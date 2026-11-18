package product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import product.dto.CampaignDto;

@FeignClient(name = "campaign-service", path = "api/v1/campaign", dismiss404 = true)
public interface CampaignClient {

    @GetMapping("/{productId}")
    ResponseEntity<CampaignDto> getCampaignByProductId(@PathVariable String productId);
}
