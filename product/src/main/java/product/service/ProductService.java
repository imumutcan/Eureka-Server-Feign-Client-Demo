package product.service;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.client.CampaignClient;
import product.dto.CampaignDto;
import product.dto.ProductDto;
import product.exception.ProductNotFoundException;
import product.model.Product;
import product.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CampaignClient campaignClient;

    public ProductService(ProductRepository repository, CampaignClient campaignClient)
    {
        this.repository = repository;
        this.campaignClient = campaignClient;
    }

    public List<ProductDto> getAllProducts()
    {
        return repository.findAll()
                .stream()
                .map(product -> findProductById(product.getId()))
                .toList();
    }

    public ProductDto findProductById(String productId)
    {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product is not found"));

        CampaignDto campaign = null;
        try {
            ResponseEntity<CampaignDto> response = campaignClient.getCampaignByProductId(productId);
            if (response != null) {
                campaign = response.getBody();
            }
        } catch (FeignException.NotFound ignored) {
            // Ürün için aktif kampanya yoksa indirimsiz fiyatla devam et
        }

        double saleRate = 0;
        if (campaign != null)
        {
            saleRate = campaign.saleRate();
        }
        return new ProductDto(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                (product.getPrice() * (1 - (saleRate / 100))),
                saleRate
        );
    }

}
