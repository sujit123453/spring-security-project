package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.dto.customResponse.ProductResponseEntity;
import com.bluthinkInc.spring_security_project.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponseEntity<Product> addProductService(Product product,MultipartFile file) throws IOException;
    List<Product> getAllProductService();
    Product getProductByIdService(int productId);
    Product updateProductByIdService(int productId, Product product, MultipartFile imageFile) throws IOException;
    boolean deleteProductByIdService(int productId);
    List<Product> searchProductByKeywordService(String keyword);
}
