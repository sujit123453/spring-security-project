package com.bluthinkInc.spring_security_project.controller.consumerController;

import com.bluthinkInc.spring_security_project.dto.customResponse.ProductResponseEntity;
import com.bluthinkInc.spring_security_project.model.Product;
import com.bluthinkInc.spring_security_project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class ConsumerController {
    private final ProductService productService;
    public ConsumerController(ProductService productService){
        this.productService = productService;
    }
    //get all products
    @GetMapping("/products")
    public ResponseEntity<ProductResponseEntity<List<Product>>> getAllProductController(){
        List<Product> product = productService.getAllProductService();
        if(!product.isEmpty()){
            ProductResponseEntity<List<Product>> response =
                    new ProductResponseEntity<>(
                            "All products are:",
                            200,
                            product
                    );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ProductResponseEntity<List<Product>> errorResponse =
                new ProductResponseEntity<>(
                        "There is no available products!",
                        404,
                        null
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //get products by id
    @GetMapping("/product/{product_id}")
    public ResponseEntity<ProductResponseEntity<?>> getProductByIdController(@PathVariable int product_id){
        Product product = productService.getProductByIdService(product_id);
        if(product != null){
            ProductResponseEntity<Product> response=
                    new ProductResponseEntity<>(
                            "Product found",
                            200,
                            product
                    );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ProductResponseEntity<String> errorResponse =
                new ProductResponseEntity<>(
                        "Product not found with this id:",
                        404,
                        null
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<ProductResponseEntity<List<Product>>> getProductByKeywordController(@PathVariable String keyword){
        List<Product> products = productService.searchProductByKeywordService(keyword);
        if(products != null){
            ProductResponseEntity<List<Product>> response =
                    new ProductResponseEntity<>(
                            "Products are:",
                            200,
                            products
                    );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ProductResponseEntity<List<Product>> errorResponse =
                new ProductResponseEntity<>(
                        "No products found for this keyword",
                        404,
                        null
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
