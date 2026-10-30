package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public List<OrderDto> getAllOrders() {
        return List.of();
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return null;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
