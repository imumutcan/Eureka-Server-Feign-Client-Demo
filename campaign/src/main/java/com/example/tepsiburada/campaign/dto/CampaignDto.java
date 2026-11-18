package com.example.tepsiburada.campaign.dto;

import com.example.tepsiburada.campaign.model.Campaign;

public record CampaignDto(String productId, double saleRate) {
   public static CampaignDto convert(Campaign from)
    {
        return new CampaignDto(from.getProductId(), from.getSaleRate());
    }
}
