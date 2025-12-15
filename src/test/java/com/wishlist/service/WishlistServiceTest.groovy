package com.wishlist.service

import com.wishlist.domain.Wishlist
import com.wishlist.exception.WishlistLimitExceededException
import com.wishlist.exception.WishlistNotFoundException
import com.wishlist.repository.WishlistRepository
import spock.lang.Specification

class WishlistServiceTest extends Specification {

    WishlistRepository wishlistRepository = Mock()
    WishlistService wishlistService = new WishlistService(wishlistRepository)

    def "should return wishlist when it exists"() {
        given: "an existing wishlist for the customer"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["456", "789"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "fetching the wishlist"
            def result = wishlistService.getWishlist("123")

        then: "the wishlist is returned"
            result.customerId == "123"
            result.productIds.size() == 2
            result.productIds.contains("456")
    }

    def "should throw exception when wishlist does not exist"() {
        given: "no wishlist for the customer"
            wishlistRepository.findByCustomerId("123") >> Optional.empty()

        when: "fetching the wishlist"
            wishlistService.getWishlist("123")

        then: "an exception is thrown"
            thrown(WishlistNotFoundException)
    }

    def "should return true when product exists in wishlist"() {
        given: "a wishlist containing the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["456"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "checking if product exists"
            def result = wishlistService.containsProduct("123", "456")

        then: "product is found"
            result == true
    }

    def "should return false when product does not exist in wishlist"() {
        given: "a wishlist without the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["789"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "checking if product exists"
            def result = wishlistService.containsProduct("123", "456")

        then: "product is not found"
            result == false
    }

    def "should add product to existing wishlist"() {
        given: "an existing wishlist without the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds([] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "adding a product"
            wishlistService.addProduct("123", "456")

        then: "product is added and wishlist is saved"
            wishlist.productIds.contains("456")
            1 * wishlistRepository.save(wishlist)
    }

    def "should create wishlist when adding product to a new customer"() {
        given: "no existing wishlist"
            wishlistRepository.findByCustomerId("123") >> Optional.empty()

        when: "adding a product"
            wishlistService.addProduct("123", "456")

        then: "a new wishlist is created and saved"
            1 * wishlistRepository.save({
                it.customerId == "123" &&
                        it.productIds.contains("456")
            })
    }

    def "should not add product if it already exists in wishlist"() {
        given: "a wishlist that already contains the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["456"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "adding the same product again"
            wishlistService.addProduct("123", "456")

        then: "wishlist is not saved again"
            0 * wishlistRepository.save(_)
            wishlist.productIds.size() == 1
    }

    def "should throw exception when wishlist exceeds product limit"() {
        given: "a wishlist already at maximum capacity"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds((1..20).collect { it.toString() } as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "adding another product"
            wishlistService.addProduct("123", "999")

        then: "an exception is thrown"
            thrown(WishlistLimitExceededException)
            0 * wishlistRepository.save(_)
    }

    def "should remove product from wishlist when it exists"() {
        given: "a wishlist containing the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["456", "789"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "removing the product"
            wishlistService.removeProduct("123", "456")

        then: "product is removed and wishlist is saved"
            !wishlist.productIds.contains("456")
            1 * wishlistRepository.save(wishlist)
    }

    def "should do nothing when removing product from non-existing wishlist"() {
        given: "no wishlist for the customer"
            wishlistRepository.findByCustomerId("123") >> Optional.empty()

        when: "removing a product"
            wishlistService.removeProduct("123", "456")

        then: "nothing happens"
            0 * wishlistRepository.save(_)
    }

    def "should not save wishlist when removing non-existing product"() {
        given: "a wishlist without the product"
            def wishlist = Wishlist.builder()
                    .customerId("123")
                    .productIds(["789"] as Set)
                    .build()

            wishlistRepository.findByCustomerId("123") >> Optional.of(wishlist)

        when: "removing a product that does not exist"
            wishlistService.removeProduct("123", "456")

        then: "wishlist is not saved"
            0 * wishlistRepository.save(_)
            wishlist.productIds.size() == 1
    }
}
