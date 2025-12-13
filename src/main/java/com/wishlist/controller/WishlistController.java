package com.wishlist.controller;

import com.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{customerId}/products/{productId}")
    public void addProduct(@PathVariable String customerId, @PathVariable String productId) {
        wishlistService.addProduct(customerId, productId);
    }
}
