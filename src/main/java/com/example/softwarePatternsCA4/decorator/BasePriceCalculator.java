package com.example.softwarePatternsCA4.decorator;

import com.example.softwarePatternsCA4.entity.ShoppingCart;

import java.math.BigDecimal;

public class BasePriceCalculator implements PriceCalculator {

    private final ShoppingCart cart;

    public BasePriceCalculator(ShoppingCart cart) {
        this.cart = cart;
    }

    @Override
    public BigDecimal calculateTotal() {
        return cart.getTotal(); 
    }
}
