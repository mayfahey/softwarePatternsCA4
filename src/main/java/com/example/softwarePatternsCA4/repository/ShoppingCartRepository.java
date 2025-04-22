package com.example.softwarePatternsCA4.repository;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByCustomer(CustomerProfile customer);
}

