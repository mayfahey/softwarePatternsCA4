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
        BigDecimal discountRate = BigDecimal.valueOf(Math.min(loyaltyPoints / 10, 30)).divide(BigDecimal.valueOf(100)); // Max 30%

        BigDecimal discountAmount = baseTotal.multiply(discountRate);
        return baseTotal.subtract(discountAmount);
    }
}
