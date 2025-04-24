package com.example.softwarePatternsCA4.decorator;

import com.example.softwarePatternsCA4.entity.CustomerProfile;

import java.math.BigDecimal;

public class LoyaltyDiscountDecorator implements PriceCalculator {

    private final PriceCalculator wrappedCalculator;
    private final CustomerProfile customer;

    public LoyaltyDiscountDecorator(PriceCalculator wrappedCalculator, CustomerProfile customer) {
        this.wrappedCalculator = wrappedCalculator;
        this.customer = customer;
    }

    @Override
    public BigDecimal calculateTotal() {
        BigDecimal baseTotal = wrappedCalculator.calculateTotal();

        int loyaltyPoints = customer.getLoyaltyPoints();
        BigDecimal loyaltyDiscount = BigDecimal.valueOf(loyaltyPoints).multiply(BigDecimal.valueOf(0.01));

        BigDecimal finalTotal = baseTotal.subtract(loyaltyDiscount);
        return finalTotal.max(BigDecimal.ZERO); // no negative totals
    }
}
