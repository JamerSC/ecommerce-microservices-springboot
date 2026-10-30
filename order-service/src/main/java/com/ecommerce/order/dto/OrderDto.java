package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDto {

    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;
}
