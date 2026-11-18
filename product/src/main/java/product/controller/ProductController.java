package product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.dto.ProductDto;
import product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts()
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String productId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProductById(productId));
    }
}
