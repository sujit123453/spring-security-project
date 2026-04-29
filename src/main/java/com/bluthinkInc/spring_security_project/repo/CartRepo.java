package com.bluthinkInc.spring_security_project.repo;

import com.bluthinkInc.spring_security_project.model.Cart;
import com.bluthinkInc.spring_security_project.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<Cart,Integer> {
    Optional<Cart> findByUser(Users user);
}
