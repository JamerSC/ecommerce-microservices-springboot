package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.repository.OrderSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Page<OrderDto> getAllOrders(
            String search,
            LocalDate dateFrom,
            LocalDate dateTo,
            Pageable pageable
    ) {
        log.debug("Fetching orders with search: {}, dateFrom: {}, dateTo: {}, pageable: {}", search, dateFrom, dateTo, pageable);

        Specification<Order> spec = Specification.allOf(
                OrderSpecification.search(search),
                OrderSpecification.dateBetween(dateFrom, dateTo)
        );

        return orderRepository.findAll(spec, pageable)
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getCreatedBy(),
                        order.getCreatedAt(),
                        order.getUpdatedBy(),
                        order.getUpdatedAt()
                ));
    }

    @Override
    public List<OrderDto> getAllMockOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> new OrderDto(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getStatus(),
                        order.getCreatedBy(),
                        order.getCreatedAt(),
                        order.getUpdatedBy(),
                        order.getUpdatedAt()
                ))
                .toList();
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return new OrderDto(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedBy(),
                order.getCreatedAt(),
                order.getUpdatedBy(),
                order.getUpdatedAt()
        );
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus(orderDto.getStatus());
        order.setCreatedBy(orderDto.getCreatedBy());
        order.setCreatedAt(orderDto.getCreatedAt());
        order.setUpdatedBy(orderDto.getUpdatedBy());
        order.setUpdatedAt(orderDto.getUpdatedAt());
        orderRepository.save(order);
        return new OrderDto(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedBy(),
                order.getCreatedAt(),
                order.getUpdatedBy(),
                order.getUpdatedAt()
        );
    }

    @Override
    public OrderDto updateOrder(Long orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.setCustomerId(orderDto.getCustomerId());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus(orderDto.getStatus());
        order.setUpdatedBy(orderDto.getUpdatedBy());
        order.setUpdatedAt(orderDto.getUpdatedAt());
        orderRepository.save(order);
        return new OrderDto(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedBy(),
                order.getCreatedAt(),
                order.getUpdatedBy(),
                order.getUpdatedAt()
        );
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
