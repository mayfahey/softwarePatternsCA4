package com.example.softwarePatternsCA4.repository;

import com.example.softwarePatternsCA4.entity.CartItem;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(ShoppingCart cart);
}
