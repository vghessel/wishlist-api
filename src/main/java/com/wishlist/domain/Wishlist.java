package com.wishlist.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String wishlistId;

    @Indexed(unique = true)
    private String customerId;

    private Set<String> productIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
