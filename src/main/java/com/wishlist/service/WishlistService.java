package com.wishlist.service;

import com.wishlist.domain.Wishlist;
import com.wishlist.exception.WishlistLimitExceededException;
import com.wishlist.exception.WishlistNotFoundException;
import com.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    private static final int MAX_PRODUCTS = 20;

    public Wishlist getWishlist(String customerId) {
        log.debug("Fetching wishlist for customerId = {}", customerId);
        return wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException(customerId));
    }

    public boolean containsProduct(String customerId, String productId) {
        log.debug("Checking if productId = {} exists in wishlist for customerId = {}", productId, customerId);
        Wishlist wishlist = getWishlist(customerId);
        return wishlist.getProductIds().contains(productId);
    }

    public void addProduct(String customerId, String productId) {
        log.debug("Adding productId = {} to wishlist for customerId = {}", productId, customerId);
        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElseGet(() -> Wishlist.builder()
                        .customerId(customerId)
                        .productIds(new HashSet<>())
                        .build());

        if (wishlist.getProductIds().contains(productId)) {
            log.debug("ProductId = {} already exists in wishlist for customerId = {}", productId, customerId);
            return;
        }

        if (wishlist.getProductIds().size() >= MAX_PRODUCTS) {
            throw new WishlistLimitExceededException(customerId);
        }

        wishlist.getProductIds().add(productId);
        wishlistRepository.save(wishlist);

        log.debug("ProductId = {} added to wishlist for customerId = {}", productId, customerId);
    }

    public void removeProduct(String customerId, String productId) {
        log.debug("Removing productId = {} from wishlist for customerId = {}", productId, customerId);

        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(null);

        if (wishlist == null) {
            log.debug("Wishlist not found for customerId = {}, nothing to remove", customerId);
            return;
        }

        if (wishlist.getProductIds().remove(productId)) {
            wishlistRepository.save(wishlist);
            log.debug("ProductId = {} removed from wishlist for customerId = {}", productId, customerId);
        } else {
            log.debug("ProductId = {} not found in wishlist for customerId = {}", productId, customerId);
        }
    }
}
