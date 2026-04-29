package com.bluthinkInc.spring_security_project.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToOne
    private Users user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<CartItem> items;
    private LocalDateTime createAt;

    public Cart(LocalDateTime createAt){
        this.createAt = createAt;
    }
}
