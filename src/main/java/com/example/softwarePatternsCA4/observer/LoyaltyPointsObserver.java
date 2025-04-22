package com.example.softwarePatternsCA4.observer;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.Order;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoyaltyPointsObserver implements OrderObserver {

    private final CustomerProfileService customerProfileService;

    @Autowired
    public LoyaltyPointsObserver(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @Override
    public void onOrderPlaced(Order order) {
        CustomerProfile customer = order.getCustomer();

        // 1 point per 10 spent
        int pointsToAdd = order.getTotalAmount().intValue() / 10;

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + pointsToAdd);
        customerProfileService.saveProfile(customer);

        System.out.println("Loyalty points added: " + pointsToAdd);
    }
}
