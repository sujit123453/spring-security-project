package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.dto.CartResponse;
import com.bluthinkInc.spring_security_project.model.Cart;

public interface CartService {
     CartResponse addToCart(String name,Integer productId,Integer qty);
     Cart getCart(String name);
     CartResponse convertToCartResponse(Cart cart);
}
