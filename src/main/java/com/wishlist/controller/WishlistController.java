package com.wishlist.controller;

import com.wishlist.domain.Wishlist;
import com.wishlist.dto.WishlistResponseDTO;
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

    @GetMapping("/{customerId}")
    public WishlistResponseDTO getWishlist(@PathVariable String customerId) {
        log.info("GET /wishlists/{}", customerId);
        Wishlist wishlist = wishlistService.getWishlist(customerId);
        return new WishlistResponseDTO(wishlist.getCustomerId(), wishlist.getProductIds());
    }

    @GetMapping("/{customerId}/products/{productId}")
    public boolean containsProduct(@PathVariable String customerId, @PathVariable String productId) {
        log.info("GET /wishlists/{}/products/{}", customerId, productId);
        return wishlistService.containsProduct(customerId, productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{customerId}/products/{productId}")
    public void addProduct(@PathVariable String customerId, @PathVariable String productId) {
        log.info("POST /wishlists/{}/products/{}", customerId, productId);
        wishlistService.addProduct(customerId, productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}/products/{productId}")
    public void removeProduct(@PathVariable String customerId, @PathVariable String productId) {
        log.info("DELETE /wishlists/{}/products/{}", customerId, productId);
        wishlistService.removeProduct(customerId, productId);
    }
}
