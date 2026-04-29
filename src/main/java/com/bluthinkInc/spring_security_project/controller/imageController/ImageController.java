package com.bluthinkInc.spring_security_project.controller.imageController;

import com.bluthinkInc.spring_security_project.model.Product;
import com.bluthinkInc.spring_security_project.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    //constructor injection
    private final ProductService productService;
    public ImageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Product product = productService.getProductByIdService(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(product.getImageData());
    }
}
