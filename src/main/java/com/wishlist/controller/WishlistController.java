package com.wishlist.controller;

import com.wishlist.domain.Wishlist;
import com.wishlist.dto.WishlistProductRequest;
import com.wishlist.dto.WishlistResponse;
import com.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping()
    public WishlistResponse getWishlist(@RequestParam("customerId") String customerId) {
        log.info("GET /wishlists with customerId = {}", customerId);
        Wishlist wishlist = wishlistService.getWishlist(customerId);
        return new WishlistResponse(wishlist.getCustomerId(), wishlist.getProductIds());
    }

    @GetMapping("/products")
    public boolean containsProduct(WishlistProductRequest request) {
        log.info("GET /wishlists/products with customerId = {} and productId = {}", request.customerId(), request.productId());
        return wishlistService.containsProduct(request.customerId(), request.productId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/products")
    public void addProduct(WishlistProductRequest request) {
        log.info("POST /wishlists/products with customerId = {} and productId = {}", request.customerId(), request.productId());
        wishlistService.addProduct(request.customerId(), request.productId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/products")
    public void removeProduct(WishlistProductRequest request) {
        log.info("DELETE /wishlists/products with customerId = {} and productId = {}", request.customerId(), request.productId());
        wishlistService.removeProduct(request.customerId(), request.productId());
    }
}
