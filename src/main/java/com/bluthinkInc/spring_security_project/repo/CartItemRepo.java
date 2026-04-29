package com.bluthinkInc.spring_security_project.repo;

import com.bluthinkInc.spring_security_project.model.Cart;
import com.bluthinkInc.spring_security_project.model.CartItem;
import com.bluthinkInc.spring_security_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
