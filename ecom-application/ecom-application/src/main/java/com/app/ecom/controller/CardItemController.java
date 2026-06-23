package com.app.ecom.controller;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CardItem;
import com.app.ecom.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItem")
@RequiredArgsConstructor
public class CardItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addToCard(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest) {
        if(!cartItemService.addToCard(userId,cartItemRequest)){
            return ResponseEntity.badRequest().body("Product out of stock or user not found or product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCard(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartItemService.deleteItemFromCart(userId,productId);
        return deleted?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();

    }

    @GetMapping("/items")
    public ResponseEntity<List<CardItem>> getCard(
            @RequestHeader("X-User-ID") String userId){
        return  ResponseEntity.ok(cartItemService.getCart(userId));
    }


}
