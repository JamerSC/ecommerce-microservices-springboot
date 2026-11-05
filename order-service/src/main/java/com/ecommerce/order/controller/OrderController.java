package com.ecommerce.order.controller;

import com.ecommerce.order.dto.CreateOrderDto;
import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.dto.UpdateOrderDto;
import com.ecommerce.order.service.OrderService;
import com.ecommerce.order.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/ecommerce/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<Page<OrderDto>>> getAllOrders(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        Page<OrderDto> orders = orderService.getAllOrders(search, dateFrom, dateTo, pageable);
        return ResponseEntity.ok(ApiResponse.<Page<OrderDto>>builder()
                .success(true)
                .message("Orders retrieved successfully")
                .data(orders)
                .status(200)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Order retrieved successfully")
                .data(order)
                .status(200)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @Valid @RequestBody CreateOrderDto orderDto
    ) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Product created successfully")
                .data(createdOrder)
                .status(201)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderDto orderDto
    ) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(ApiResponse.<OrderDto>builder()
                .success(true)
                .message("Order updated successfully")
                .data(updatedOrder)
                .status(200)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Order deleted successfully")
                .status(200)
                .timestamp(java.time.LocalDateTime.now())
                .build()
        );
    }

    /**
     * This endpoint is for demonstration purposes only.
     * It returns a list of mock orders.
     */
    @GetMapping("/mock")
    public List<OrderDto> getMockOrders() {
        List<OrderDto> orders = new ArrayList<>();
        orders.add(new OrderDto(1L, 1L, BigDecimal.valueOf(100.00), null, 1L, null, 1L, null));
        orders.add(new OrderDto(2L, 2L, BigDecimal.valueOf(200.00), null, 1L, null, 1L, null));
        return orders;
    }
}