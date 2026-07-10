package com.techie.common.event;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private String orderNo;

    private String email;

    private BigDecimal totalAmount;

    private List<OrderItemEvent> items;

    private LocalDateTime createdAt;

}
