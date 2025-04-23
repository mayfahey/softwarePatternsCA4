package com.example.softwarePatternsCA4.service;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.User;
import com.example.softwarePatternsCA4.repository.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository) {
        this.customerProfileRepository = customerProfileRepository;
    }

    // Create or update profile
    public CustomerProfile saveProfile(CustomerProfile profile) {
        return customerProfileRepository.save(profile);
    }

    // Get profile by ID
    public Optional<CustomerProfile> getProfileById(Long id) {
        return customerProfileRepository.findById(id);
    }

    // Get profile by User
    public Optional<CustomerProfile> getProfileByUser(User user) {
        return customerProfileRepository.findByUser(user);
    }

    // Get all profiles
    public List<CustomerProfile> getAllProfiles() {
        return customerProfileRepository.findAll();
    }

    // Delete profile
    public void deleteProfile(Long id) {
        customerProfileRepository.deleteById(id);
    }
}
