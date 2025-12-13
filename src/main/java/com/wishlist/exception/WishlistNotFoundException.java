package com.wishlist.exception;

public class WishlistNotFoundException extends RuntimeException {

    private final String customerId;

    public WishlistNotFoundException(String customerId) {
        super("Wishlist not found for customerId=" + customerId);
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }
}
