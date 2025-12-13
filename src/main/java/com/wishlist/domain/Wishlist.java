package com.wishlist.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Builder
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String customerId;

    // Lista mesmo? e se tiver algum atributo que faca sentido estar aqui tbm além do id?
    private Set<String> productIds;
}
