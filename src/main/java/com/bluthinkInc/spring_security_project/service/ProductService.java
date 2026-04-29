package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.dto.customResponse.ProductResponseEntity;
import com.bluthinkInc.spring_security_project.model.Product;
import com.bluthinkInc.spring_security_project.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public ProductResponseEntity<Product> addProductService(Product product, MultipartFile file) throws IOException {
        product.setImageName(file.getOriginalFilename());
        product.setImageType(file.getContentType());
        product.setImageData(file.getBytes());

        return new ProductResponseEntity<>(
                "Product Added successfully!!",
                201,
                repo.save(product)
        );
    }

    public List<Product> getAllProductService() {
        return repo.findAll();
    }

    public Product getProductByIdService(int productId) {
        return repo.findById(productId).orElse(null);
    }

    public Product updateProductByIdService(int productId, Product product, MultipartFile imageFile) throws IOException {

        Product existingProduct = repo.findById(productId).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        // update existing entity (NOT the new product object)
        existingProduct.setProduct_name(product.getProduct_name());
        existingProduct.setProduct_description(product.getProduct_description());
        existingProduct.setProduct_price(product.getProduct_price());
        existingProduct.setStock(product.getStock());
        existingProduct.setIssueDate(product.getIssueDate());

        existingProduct.setImageData(imageFile.getBytes());
        existingProduct.setImageName(imageFile.getOriginalFilename());
        existingProduct.setImageType(imageFile.getContentType());

        return repo.save(existingProduct);
    }


    public boolean deleteProductByIdService(int productId) {
        if (repo.existsById(productId)) {
            repo.deleteById(productId);
            return true;
        }
        return false;
    }

    public List<Product> searchProductByKeywordService(String keyword) {
        List<Product> products = repo.searchProductByKeyword(keyword);
        return products;
    }
}

