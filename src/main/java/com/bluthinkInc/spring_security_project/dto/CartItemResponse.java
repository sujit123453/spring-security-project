package com.bluthinkInc.spring_security_project.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private double price;
}
