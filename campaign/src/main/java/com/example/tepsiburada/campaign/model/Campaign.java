package com.example.tepsiburada.campaign.model;

import jakarta.persistence.*;

@Entity
@Table(name = "campaign")

public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String productId;
    private double saleRate;
    @Column(name = "is_active")
    private boolean isActive;

    protected Campaign()
    {

    }

    private Campaign(Builder builder)
    {
        this.productId = builder.productId;
        this.saleRate = builder.saleRate;
        this.isActive = builder.isActive;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public double getSaleRate() {
        return saleRate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    public static class Builder{
        private String productId;
        private double saleRate;
        private boolean isActive = true;

        public Builder productId(String productId)
        {
            this.productId = productId;
            return this;
        }

        public Builder saleRate(double saleRate)
        {
            this.saleRate = saleRate;
            return this;
        }

        public Builder isActive(boolean isActive)
        {
            this.isActive = isActive;
            return this;
        }

        public Campaign build()
        {
            return new Campaign(this);
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }

}
