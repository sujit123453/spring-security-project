package com.bluthinkInc.spring_security_project.controller.adminController;

import com.bluthinkInc.spring_security_project.dto.customResponse.ProductResponseEntity;
import com.bluthinkInc.spring_security_project.model.Product;
import com.bluthinkInc.spring_security_project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    public AdminController(ProductService productService){
        this.productService = productService;
    }

    //add products
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/addProduct",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseEntity<?>> addProduct(@RequestPart("product") String productJson,
                                                               @RequestPart("imageFile")MultipartFile imageFile){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson,Product.class);
            System.out.println(imageFile.getOriginalFilename());
            ProductResponseEntity<?> savedProduct = productService.addProductService(product,imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        }catch(Exception e){
            ProductResponseEntity<String> errorResponse =
                    new ProductResponseEntity<>(
                            "Product not saved,something went wrong",
                            200,
                            null
                    );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    //update product by product id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/product/{product_id}")
    public ResponseEntity<ProductResponseEntity<?>> updateProductByIdController(
            @PathVariable int product_id,
            @PathVariable("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(productJson,Product.class);
        Product updateProduct = productService.updateProductByIdService(product_id,product,imageFile);
        if(updateProduct != null){
            ProductResponseEntity<?> response =
                    new ProductResponseEntity<>(
                            "update successfully!!",
                            200,
                            updateProduct
                    );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ProductResponseEntity<String> errorResponse =
                new ProductResponseEntity<>(
                        "Product not found with this productId",
                        404,
                        null
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //delete product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/product/{product_id}")
    public ResponseEntity<ProductResponseEntity<?>> deleteProductByIdController(
            @PathVariable int product_id){
        boolean isDeleted = productService.deleteProductByIdService(product_id);
        if(isDeleted){
            ProductResponseEntity<?> response =
                    new ProductResponseEntity<>(
                            "delete Successfully",
                            200,
                            true
                    );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        ProductResponseEntity<?> errorResponse =
                new ProductResponseEntity<>(
                        "product not found with this id!!",
                        404,
                        false
                );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
