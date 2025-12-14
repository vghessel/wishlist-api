package com.wishlist.exception;

import lombok.Getter;

@Getter
public class WishlistNotFoundException extends RuntimeException {

    private final String customerId;

    public WishlistNotFoundException(String customerId) {
        super("Wishlist not found for customerId=" + customerId);
        this.customerId = customerId;
    }
}
