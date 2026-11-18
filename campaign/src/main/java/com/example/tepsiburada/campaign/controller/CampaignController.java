package com.example.tepsiburada.campaign.controller;

import com.example.tepsiburada.campaign.dto.CampaignDto;
import com.example.tepsiburada.campaign.dto.CreateCampaignRequest;
import com.example.tepsiburada.campaign.service.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/campaign")
public class CampaignController {
    private final CampaignService service;
    Logger logger = LoggerFactory.getLogger(CampaignController.class);
    public CampaignController(CampaignService service)
    {
        this.service = service;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<CampaignDto> getCampaignByProductId(@PathVariable String productId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCampaignByProductId(productId));
    }
    @PostMapping
    public ResponseEntity<CampaignDto> createCampaign(@RequestBody CreateCampaignRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCampaign(request));
    }
}
