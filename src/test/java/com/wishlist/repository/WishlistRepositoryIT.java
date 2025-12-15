package com.wishlist.repository;

import com.wishlist.domain.Wishlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
class WishlistRepositoryIT {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "wishlist_test");
    }

    @Autowired
    private WishlistRepository wishlistRepository;

    @BeforeEach
    void cleanDatabase() {
        wishlistRepository.deleteAll();
    }

    @Test
    void shouldFindWishlistByCustomerId() {
        // given
        Wishlist wishlist = Wishlist.builder()
                .customerId("123")
                .productIds(Set.of("456", "789"))
                .build();

        wishlistRepository.save(wishlist);

        // when
        var result = wishlistRepository.findByCustomerId("123");

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getProductIds()).hasSize(2);
    }

    @Test
    void shouldReturnEmptyWhenCustomerDoesNotExist() {
        // when
        var result = wishlistRepository.findByCustomerId("999");

        // then
        assertThat(result).isEmpty();
    }
}
