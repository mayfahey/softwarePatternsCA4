package com.example.softwarePatternsCA4.decorator;

import java.math.BigDecimal;

public class PercentageDiscountDecorator implements PriceCalculator {

    private final PriceCalculator wrappedCalculator;
    private final BigDecimal discountPercent; // 0.10 for 10% etc

    public PercentageDiscountDecorator(PriceCalculator wrappedCalculator, BigDecimal discountPercent) {
        this.wrappedCalculator = wrappedCalculator;
        this.discountPercent = discountPercent;
    }

    @Override
    public BigDecimal calculateTotal() {
        BigDecimal baseTotal = wrappedCalculator.calculateTotal();
        BigDecimal discountAmount = baseTotal.multiply(discountPercent);
        return baseTotal.subtract(discountAmount);
    }
}
