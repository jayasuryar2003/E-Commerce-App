package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.model.CardItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRespository;
import com.app.ecom.repository.ProductRespository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    private final ProductRespository productRespository;
    private final UserRepository userRepository;
    private final CartItemRespository cartItemRespository;

    public boolean addToCard(String userId, CartItemRequest cartItemRequest) {
      Optional<Product> productOpt = productRespository.findById(cartItemRequest.getProductId());
      if(productOpt.isEmpty()){
          return false;
      }
      Product product = productOpt.get();
      if(cartItemRequest.getQuantity()< cartItemRequest.getQuantity()){
          return false;
      }

      Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
        if(useropt.isEmpty()){
            return false;
        }
        User user = useropt.get();

        // here we are handling two scenarios if the p roduct already exist in the cart we just increase
        // the quantity and the product is not exist we need to add the quantity into 1 or requsted quantity.

        CardItem existingCartItem = cartItemRespository.findByUserAndProduct(user,product);
        if(existingCartItem!=null){
            // update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity()+cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRespository.save(existingCartItem);
        }else{
           // create a new card item
            CardItem cardItem = new CardItem();
            cardItem.setUser(user);
            cardItem.setProduct(product);
            cardItem.setQuantity(cartItemRequest.getQuantity());
            cardItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRespository.save(cardItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
//        Optional<Product> productOpt = productRespository.findById(productId);
//        if(productOpt.isEmpty()){
//            return false;
//        }
//        Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
//        if(useropt.isEmpty()){
//            return false;
//        }
//        useropt.flatMap(user ->
//                productOpt.map(product ->{
//                    cartItemRespository.deleteByUserAndProduct(user,product);
//                    return true;
//                }));
        Optional<Product> productOpt = productRespository.findById(productId);
        Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
        if(productOpt.isPresent() && useropt.isPresent()){
            cartItemRespository.deleteByUserAndProduct(useropt.get(),productOpt.get());
            return true;
        }
        return false;
    }

    public List<CardItem> getCart(String userId) {
//        Optional<User> useropt = userRepository.findById(Long.valueOf(userId));
//        if( useropt.isPresent()){
//            cartItemRespository.findByUser(useropt.get());
//            return true;
//        }
//        return false;
        return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRespository::findByUser)
                .orElseGet(List::of);
    }
}

