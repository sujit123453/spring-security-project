package com.bluthinkInc.spring_security_project.service;

import com.bluthinkInc.spring_security_project.customExceptions.UserNotFoundException;
import com.bluthinkInc.spring_security_project.dto.CartItemResponse;
import com.bluthinkInc.spring_security_project.dto.CartResponse;
import com.bluthinkInc.spring_security_project.model.Cart;
import com.bluthinkInc.spring_security_project.model.CartItem;
import com.bluthinkInc.spring_security_project.model.Product;
import com.bluthinkInc.spring_security_project.model.Users;
import com.bluthinkInc.spring_security_project.repo.CartItemRepo;
import com.bluthinkInc.spring_security_project.repo.CartRepo;
import com.bluthinkInc.spring_security_project.repo.ProductRepo;
import com.bluthinkInc.spring_security_project.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl {
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    public CartServiceImpl(CartRepo cartRepo, CartItemRepo cartItemRepo,
                           ProductRepo productRepo, UserRepo userRepo){
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo =productRepo;
        this.userRepo = userRepo;
    }

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    public CartResponse addToCart(String name, Integer productId, Integer qty) {
        logger.info("Add to cart request received for user:{},productId:{},qty:{}",name,productId,qty);
        Users user = userRepo.findByName(name);
        if(user == null){
            logger.warn("User not found with name:{}",name);
            throw new UserNotFoundException("User not found");
        }
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(()->{
                    logger.info("No existing cart found.Creating new cart for user:{}",name);
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCreateAt(LocalDateTime.now());
                    return cartRepo.save(newCart);
                });
        //Fetch product
        logger.debug("Fetching product with Id:{}",productId);
        Product product = productRepo.findById(productId)
                .orElseThrow(()->new RuntimeException("product not found with this id"));

        //check the stock present or not
        if(product.getStock() < qty){
            logger.warn("Insufficient stock for productId:{},Available:{},Requested:{}",
                    productId,product.getStock(),qty);
            throw new RuntimeException("Product is out of stock");
        }
        //duplicates product handling
        CartItem item = cartItemRepo.findByCartAndProduct(cart,product)
                .orElse(null);
        if(item!=null){
            logger.info("Product already exist in cart.Updating quantity.");
            item.setQuantity(item.getQuantity() + qty);
        }else{
            logger.info("Adding new product to cart.");
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(qty);
        }
        item.setCreatedAt(LocalDateTime.now());
         cartItemRepo.save(item);
         logger.info("Product added to cart successfully for user:{}",name);
         return convertToCartResponse(cart);
    }


    public Cart getCart(String name) {
        Users user = userRepo.findByName(name);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        Cart cart = cartRepo.findByUser(user)
                .orElseGet(()->{
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepo.save(newCart);
                });
        return cart;
    }


    private CartResponse convertToCartResponse(Cart cart) {

        List<CartItemResponse> itemResponses = new ArrayList<>();
        double total = 0;
        List<CartItem> items = new ArrayList<>();
        for (CartItem item : cart.getItems()) {

            CartItemResponse response = new CartItemResponse();
            response.setProductId(item.getProduct().getProduct_id());
            response.setProductName(item.getProduct().getProduct_name());
            response.setQuantity(item.getQuantity());
            response.setPrice(item.getProduct().getProduct_price());

            total += item.getProduct().getProduct_price() * item.getQuantity();

            itemResponses.add(response);
        }

        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getCartId());
        cartResponse.setCartItem(itemResponses);
        cartResponse.setTotalPrice(total);
        return cartResponse;
    }
}
