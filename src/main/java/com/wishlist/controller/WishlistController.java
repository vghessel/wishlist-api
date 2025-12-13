package com.wishlist.controller;

import com.wishlist.domain.Wishlist;
import com.wishlist.dto.WishlistResponseDTO;
import com.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/{customerId}")
    public WishlistResponseDTO getWishlist(@PathVariable String customerId) {
        Wishlist wishlist = wishlistService.getWishlist(customerId);
        return new WishlistResponseDTO(wishlist.getCustomerId(), wishlist.getProductIds());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{customerId}/products/{productId}")
    public void addProduct(@PathVariable String customerId, @PathVariable String productId) {
        wishlistService.addProduct(customerId, productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{customerId}/products/{productId}")
    public void removeProduct(@PathVariable String customerId, @PathVariable String productId) {
        wishlistService.removeProduct(customerId, productId);
    }
}
