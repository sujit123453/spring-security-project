package com.bluthinkInc.spring_security_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    @ManyToOne
    @JsonBackReference
    private Cart cart;
    @ManyToOne
    private Product product;
    private Integer quantity;
    private LocalDateTime createdAt;

    public CartItem(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
}
