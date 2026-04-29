package com.bluthinkInc.spring_security_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private Integer cartId;
    private List<CartItemResponse> cartItem;
    private double totalPrice;
}
