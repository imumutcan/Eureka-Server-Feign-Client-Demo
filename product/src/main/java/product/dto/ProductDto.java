package product.dto;
public record ProductDto(
        String productId,
        String productName,
        double price,
        double salePrice,
        double saleRate
) {

}
