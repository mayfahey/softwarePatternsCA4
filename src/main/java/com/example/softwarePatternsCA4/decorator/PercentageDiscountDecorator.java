package com.example.softwarePatternsCA4.decorator;

import java.math.BigDecimal;

public class PercentageDiscountDecorator implements PriceCalculator {

    private final PriceCalculator wrappedCalculator;

    public PercentageDiscountDecorator(PriceCalculator wrappedCalculator) {
        this.wrappedCalculator = wrappedCalculator;
    }

    @Override
    public BigDecimal calculateTotal() {
        BigDecimal baseTotal = wrappedCalculator.calculateTotal();

        BigDecimal discountPercent = BigDecimal.ZERO;
        if (baseTotal.compareTo(BigDecimal.valueOf(150)) >= 0) {
            discountPercent = BigDecimal.valueOf(0.20); // 20% off
        } else if (baseTotal.compareTo(BigDecimal.valueOf(75)) >= 0) {
            discountPercent = BigDecimal.valueOf(0.10); // 10% off
        }

        BigDecimal discountAmount = baseTotal.multiply(discountPercent);
        return baseTotal.subtract(discountAmount);
    }
}
