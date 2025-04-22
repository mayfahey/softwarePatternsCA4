package com.example.softwarePatternsCA4.repository;

import com.example.softwarePatternsCA4.entity.CustomerProfile;
import com.example.softwarePatternsCA4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    
    Optional<CustomerProfile> findByUser(User user);
}
