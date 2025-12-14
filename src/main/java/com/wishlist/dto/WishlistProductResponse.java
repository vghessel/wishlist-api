package com.wishlist.dto;

public record WishlistProductResponse(
        String customerId,
        String productId,
        boolean inWishlist
) {}
