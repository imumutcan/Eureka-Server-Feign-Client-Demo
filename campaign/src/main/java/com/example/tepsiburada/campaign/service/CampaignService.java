package com.example.tepsiburada.campaign.service;

import com.example.tepsiburada.campaign.dto.CampaignDto;
import com.example.tepsiburada.campaign.dto.CreateCampaignRequest;
import com.example.tepsiburada.campaign.exception.CampaignNotFoundException;
import com.example.tepsiburada.campaign.model.Campaign;
import com.example.tepsiburada.campaign.repository.CampaignRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class CampaignService {
    private final CampaignRepository repository;
    private static final Logger logger =  LoggerFactory.getLogger(CampaignService.class);

    public CampaignService(CampaignRepository repository)
    {
        this.repository = repository;
    }


    public CampaignDto getCampaignByProductId(String productId)
    {
        Campaign campaign = repository.findByProductIdAndIsActive(productId, true)
                .orElseThrow(() -> new CampaignNotFoundException("Campaign not found!"));

        return CampaignDto.convert(campaign);
    }

    public CampaignDto createCampaign(CreateCampaignRequest request)
    {
        repository.findByProductIdAndIsActive(request.productId(), true)
                .ifPresent(existingCampaign -> {
                    existingCampaign.setActive(false);
                    repository.save(existingCampaign);
                });

        Campaign campaign = repository.save(
                Campaign.builder()
                        .productId(request.productId())
                        .saleRate(request.saleRate())
                        .build()
        );
        return CampaignDto.convert(campaign);
    }
}
