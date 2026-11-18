package com.example.tepsiburada.campaign.repository;

import com.example.tepsiburada.campaign.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, String> {
    Optional<Campaign> findByProductIdAndIsActive(String productId, boolean isActive);
}
