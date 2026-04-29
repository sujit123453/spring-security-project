package com.bluthinkInc.spring_security_project.repo;

import com.bluthinkInc.spring_security_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p " +
            "WHERE LOWER(p.product_name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.product_description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProductByKeyword(String keyword);
}
