package com.ecommerce.order.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private Long id;

    private Long customerId;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private String createdBy;

    private Date createdAt;

    private String updatedBy;

    private Date updatedAt;
}
