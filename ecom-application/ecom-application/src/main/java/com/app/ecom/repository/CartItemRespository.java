package com.app.ecom.repository;

import com.app.ecom.model.CardItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRespository extends JpaRepository<CardItem,Long> {

    CardItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CardItem> findByUser(User user);
}
