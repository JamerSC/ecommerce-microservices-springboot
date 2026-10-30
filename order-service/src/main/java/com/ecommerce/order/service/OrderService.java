package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long orderId);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    void deleteOrder(Long orderId);
}
