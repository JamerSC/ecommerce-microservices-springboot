package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderDto {

    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private Long createdBy;

    private OffsetDateTime createdAt;

    private Long updatedBy;

    private OffsetDateTime updatedAt;
}
