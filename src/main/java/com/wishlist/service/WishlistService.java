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
}
