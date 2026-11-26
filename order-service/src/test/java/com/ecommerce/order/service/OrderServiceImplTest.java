package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    //

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        Order currentOrder = new Order();
        currentOrder.setId(1L);
        currentOrder.setCustomerId(1L);
        currentOrder.setTotalAmount(BigDecimal.valueOf(100));
        currentOrder.setStatus(OrderStatus.PROCESSING);
        currentOrder.setCreatedBy(1L);
        currentOrder.setCreatedAt(OffsetDateTime.now());
        currentOrder.setUpdatedBy(1L);
        currentOrder.setUpdatedAt(OffsetDateTime.now());
    }

    @Test
    void getOrderById_ShouldReturnProduct_WhenProductExist() {

        // arrange
        Order order = new Order();
        order.setId(2L);
        order.setCustomerId(2L);
        order.setTotalAmount(BigDecimal.valueOf(200));
        order.setStatus(OrderStatus.SHIPPED);
        order.setCreatedBy(1L);
        order.setCreatedAt(OffsetDateTime.now());
        order.setUpdatedBy(1L);
        order.setUpdatedAt(OffsetDateTime.now());

        when(orderRepository.findById(2L))
                .thenReturn(Optional.of(order));

        // act
        OrderDto result = orderService.getOrderById(2L);

        // assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(2L, result.getCustomerId());
        assertEquals(BigDecimal.valueOf(200), result.getTotalAmount());

        verify(orderRepository).findById(2L);
    }
}
