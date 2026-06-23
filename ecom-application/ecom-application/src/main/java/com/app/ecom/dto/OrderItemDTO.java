package com.app.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal; // subtotal is one thing is not getting from DB this is we are adding
    // this is the benefity  of DTO we are deacting from the actuall model
}
