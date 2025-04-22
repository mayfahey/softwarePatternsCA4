package com.example.softwarePatternsCA4.controller;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.User;
import com.example.softwarePatternsCA4.service.CustomerProfileService;
import com.example.softwarePatternsCA4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer-profiles")
public class CustomerProfileController {

    private final CustomerProfileService profileService;
    private final UserService userService;

    @Autowired
    public CustomerProfileController(CustomerProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    // Create or update customer profile
    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestParam Long userId, @RequestBody CustomerProfile profileDetails) {
        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        CustomerProfile profile = new CustomerProfile();
        profile.setShippingAddress(profileDetails.getShippingAddress());
        profile.setPaymentMethod(profileDetails.getPaymentMethod());
        profile.setLoyaltyPoints(profileDetails.getLoyaltyPoints());
        profile.setUser(userOpt.get());

        CustomerProfile savedProfile = profileService.saveProfile(profile);
        return ResponseEntity.ok(savedProfile);
    }

    // Get customer profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerProfile> getProfileById(@PathVariable Long id) {
        Optional<CustomerProfile> profile = profileService.getProfileById(id);
        return profile.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get profile by User ID
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getProfileByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }

        Optional<CustomerProfile> profile = profileService.getProfileByUser(user.get());
        return profile.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }
}

