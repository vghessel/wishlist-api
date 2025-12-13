package com.wishlist.dto;

import java.util.Set;

public record WishlistResponseDTO(String customerId, Set<String> productIds) {}
