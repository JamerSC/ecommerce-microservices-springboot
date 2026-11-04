package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {


    Page<OrderDto> getAllOrders(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    );

    List<OrderDto> getAllMockOrders();

    OrderDto getOrderById(Long orderId);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto updateOrder(Long orderId, OrderDto orderDto);

    void deleteOrder(Long orderId);
}
