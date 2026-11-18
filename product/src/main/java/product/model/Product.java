package product.model;

import jakarta.persistence.*;

@Entity
@Table(name = "product")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productName;
    private double price;

    protected Product()
    {

    }

    private Product(Builder builder)
    {
        this.productName = builder.productName;
        this.price = builder.price;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }


    public static class Builder{
        private String productName;
        private double price;

        public Builder productName(String productName)
        {
            this.productName = productName;
            return this;
        }

        public Builder price(double price)
        {
            this.price = price;
            return this;
        }

        public Product build()
        {
            return new Product(this);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                '}';
    }
}
