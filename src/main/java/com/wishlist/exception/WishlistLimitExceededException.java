package com.wishlist.exception;

import lombok.Getter;

@Getter
public class WishlistLimitExceededException extends RuntimeException {

    private final String customerId;

    public WishlistLimitExceededException(String customerId) {
        super("Wishlist limit exceeded for customerId=" + customerId);
        this.customerId = customerId;
    }
}
