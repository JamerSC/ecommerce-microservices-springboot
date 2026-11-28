package com.ecommerce.order.service;

import com.ecommerce.order.dto.CreateOrderDto;
import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.dto.UpdateOrderDto;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.exception.ResourceNotFoundException;
import com.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    // ==========================================
    // GET ORDER
    // ==========================================

    @Test
    void getUserById_ShouldThrowException_WhenOrderDoesNotExist(){

        // arrange
        when(orderRepository.findById(99L))
                .thenReturn(Optional.empty());

        // act & assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.getOrderById(99L)
        );

        verify(orderRepository).findById(99L);
    }

    // ==========================================
    // CREATE ORDER
    // ==========================================

    @Test
    void createOrder_ShouldCreateOrder_WhenDataIsValid() {

        // arrange
        CreateOrderDto dto = new CreateOrderDto();

        dto.setCustomerId(1L);
        dto.setStatus(OrderStatus.PROCESSING);
        dto.setTotalAmount(BigDecimal.valueOf(100));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        OrderDto result = orderService.createOrder(dto);

        // assert
        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals(OrderStatus.PROCESSING, result.getStatus());
        assertEquals(BigDecimal.valueOf(100), result.getTotalAmount());

        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_ShouldUpdateOrder_WhenOrderExists() {

        // arrange
        Order existingOrder = new Order();
        existingOrder.setId(99L);
        existingOrder.setCustomerId(99L);
        existingOrder.setTotalAmount(BigDecimal.valueOf(100));
        existingOrder.setStatus(OrderStatus.PROCESSING);
        existingOrder.setCreatedBy(1L);
        existingOrder.setCreatedAt(OffsetDateTime.now());
        existingOrder.setUpdatedBy(1L);
        existingOrder.setUpdatedAt(OffsetDateTime.now());


        // update order
        UpdateOrderDto dto = new UpdateOrderDto();
        dto.setCustomerId(99L);
        dto.setTotalAmount(BigDecimal.valueOf(200));
        dto.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(99L))
                .thenReturn(Optional.of(existingOrder));

        // Mock save()
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // act
        OrderDto result = orderService.updateOrder(99L, dto);

        // assert
        assertNotNull(result);
        assertEquals(99L, result.getCustomerId());
        assertEquals(BigDecimal.valueOf(200), result.getTotalAmount());
        assertEquals(OrderStatus.SHIPPED, result.getStatus());

        verify(orderRepository).findById(99L);
        verify(orderRepository).save(existingOrder);
    }

    @Test
    void updateOrder_ShouldThrowException_OrderProductDoesNotExist() {

        // Arrange
        Long orderId = 99L;

        UpdateOrderDto dto = new UpdateOrderDto();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.updateOrder(orderId, dto)
        );

        verify(orderRepository).findById(orderId);

        verify(orderRepository, never())
                .save(any(Order.class));
    }

    // ==========================================
    // DELETE ORDER
    // ==========================================

    @Test
    void deleteOrder_ShouldDeleteOrder_WhenCalled() {
        // arrange
        Long orderId = 1L;

        // act
        orderService.deleteOrder(orderId);

        // assert
        verify(orderRepository).deleteById(orderId);
    }
}
