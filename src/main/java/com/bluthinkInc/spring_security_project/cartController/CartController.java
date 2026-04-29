package com.bluthinkInc.spring_security_project.cartController;

import com.bluthinkInc.spring_security_project.dto.CartResponse;
import com.bluthinkInc.spring_security_project.model.Cart;
import com.bluthinkInc.spring_security_project.service.CartServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CartController {
    private final CartServiceImpl cartService;
    public CartController(CartServiceImpl cartService){
        this.cartService = cartService;
    }
    @PostMapping("/cart/add")
    public ResponseEntity<CartResponse>addToCart(@RequestParam Integer product_id,
                                                 @RequestParam("quantity") Integer qty){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Fetch the authenticated username:{}",username);
        CartResponse response = cartService.addToCart(username,product_id,qty);
        log.info("Successfully added to cart");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartService.getCart(username);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
}
