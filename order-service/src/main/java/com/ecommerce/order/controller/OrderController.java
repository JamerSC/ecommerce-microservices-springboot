package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderDto;
import com.ecommerce.order.service.OrderService;
import com.ecommerce.order.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
