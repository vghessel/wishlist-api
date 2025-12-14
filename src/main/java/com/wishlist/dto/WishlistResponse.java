package com.wishlist.dto;

import java.util.Set;

public record WishlistResponse(
        String customerId,
        Set<String> productIds
) {}
