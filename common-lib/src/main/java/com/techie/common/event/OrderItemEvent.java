package com.techie.common.event;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEvent {

    private String skuCode;

    private String productName;

    private Integer quantity;

}
