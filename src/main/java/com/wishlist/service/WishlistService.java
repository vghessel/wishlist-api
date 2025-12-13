package com.wishlist.service;

import com.wishlist.domain.Wishlist;
import com.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    private static final int MAX_PRODUCTS = 20;

    public Wishlist getWishlist(String customerId) {
        return wishlistRepository.findById(customerId)
                .orElseThrow(() ->
                        new WishlistNotFoundException(customerId)
                );
    }

    public boolean containsProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findById(customerId)
                .orElseThrow(() ->
                        new WishlistNotFoundException(customerId)
                );

        return wishlist.getProductIds().contains(productId);
    }


    public void addProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository
                .findById(customerId)
                .orElseGet(() -> Wishlist.builder()
                        .customerId(customerId)
                        .productIds(new HashSet<>())
                        .build());

        if (wishlist.getProductIds().contains(productId)) {
            return;
        }

        if (wishlist.getProductIds().size() >= MAX_PRODUCTS) {
            throw new WishlistLimitExceededException(customerId);
        }

        wishlist.getProductIds().add(productId);
        wishlistRepository.save(wishlist);
    }

    public void removeProduct(String customerId, String productId) {

        Wishlist wishlist = wishlistRepository
                .findById(customerId)
                .orElse(null);

        if (wishlist == null) {
            return;
        }

        if (!wishlist.getProductIds().contains(productId)) {
            return;
        }

        wishlist.getProductIds().remove(productId);
        wishlistRepository.save(wishlist);
    }
}
