package com.wishlist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wishlist.domain.Wishlist;
import com.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WishlistController.class)
@AutoConfigureMockMvc
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService wishlistService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnWishlistByCustomerId() throws Exception {
        Wishlist wishlist = Wishlist.builder()
                .customerId("123")
                .productIds(Set.of("456", "789"))
                .build();

        when(wishlistService.getWishlist("123"))
                .thenReturn(wishlist);

        mockMvc.perform(get("/wishlists")
                        .param("customerId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("123"))
                .andExpect(jsonPath("$.productIds").isArray())
                .andExpect(jsonPath("$.productIds.length()").value(2));

        verify(wishlistService).getWishlist("123");
    }

    @Test
    void shouldReturnProductStatusInWishlist() throws Exception {
        when(wishlistService.containsProduct("123", "456"))
                .thenReturn(true);

        mockMvc.perform(get("/wishlists/products/status")
                        .param("customerId", "123")
                        .param("productId", "456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("123"))
                .andExpect(jsonPath("$.productId").value("456"))
                .andExpect(jsonPath("$.inWishlist").value(true));

        verify(wishlistService).containsProduct("123", "456");
    }

    @Test
    void shouldAddProductToWishlist() throws Exception {
        mockMvc.perform(post("/wishlists/products")
                        .param("customerId", "123")
                        .param("productId", "456"))
                .andExpect(status().isNoContent());

        verify(wishlistService).addProduct("123", "456");
    }

    @Test
    void shouldRemoveProductFromWishlist() throws Exception {
        mockMvc.perform(delete("/wishlists/products")
                        .param("customerId", "123")
                        .param("productId", "456"))
                .andExpect(status().isNoContent());

        verify(wishlistService).removeProduct("123", "456");
    }
}
