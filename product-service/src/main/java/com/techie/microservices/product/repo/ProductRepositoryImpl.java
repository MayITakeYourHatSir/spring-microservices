package com.techie.microservices.product.repo;

import com.techie.microservices.product.model.PageResponse;
import com.techie.microservices.product.model.ProductListResponse;
import com.techie.microservices.product.model.ProductSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public PageResponse<ProductListResponse> findProductList(
            ProductSearchCondition condition,
            int page,
            int size
    ) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (condition.getId() != null) {
            criteriaList.add(Criteria.where("_id").is(condition.getId()));
        }

        if (condition.getName() != null) {
            criteriaList.add(Criteria.where("name")
                    .regex(condition.getName(), "i"));
        }

        if (condition.getIsActive() != null) {
            criteriaList.add(Criteria.where("isActive")
                    .is(condition.getIsActive()));
        }

        if (condition.getCreatedAt() != null) {
            criteriaList.add(Criteria.where("createdAt")
                    .is(condition.getCreatedAt()));
        }

        Criteria criteria = criteriaList.isEmpty()
                ? new Criteria()
                : new Criteria().andOperator(criteriaList);

        // ① 查詢條件
        MatchOperation match = Aggregation.match(criteria);

        // ② 欄位投影（DTO）
        ProjectionOperation project = Aggregation.project("name", "isActive", "createdAt")
                .and("_id").as("id");

        // ③ 排序
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "createdAt");

        // ④ 分頁
        SkipOperation skip = Aggregation.skip((long) page * size);
        LimitOperation limit = Aggregation.limit(size);

        // ⑤ 查資料用 pipeline
        Aggregation dataAgg = Aggregation.newAggregation(
                match,
                project,
                sort,
                skip,
                limit
        );

        List<ProductListResponse> content = mongoTemplate.aggregate(
                dataAgg,
                "product",
                ProductListResponse.class
        ).getMappedResults();

        // ⑥ 查總筆數（count pipeline）
        Aggregation countAgg = Aggregation.newAggregation(
                match,
                Aggregation.count().as("total")
        );

        long total = mongoTemplate.aggregate(
                countAgg,
                "product",
                CountResult.class
        ).getUniqueMappedResult() != null
                ? Objects.requireNonNull(mongoTemplate.aggregate(
                        countAgg,
                        "product",
                        CountResult.class
                )
                .getUniqueMappedResult()).total
                : 0;

        return new PageResponse<>(content, total, page, size);
    }

    private static class CountResult {
        long total;
    }
}

