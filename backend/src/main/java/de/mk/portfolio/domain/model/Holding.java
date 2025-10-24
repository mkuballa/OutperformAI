package de.mk.portfolio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Holding {
    private final UUID id;
    private final UUID portfolioId;
    private final String name;
    private final String symbol;
    private final Integer quantity;
    private final BigDecimal price;
    private final BigDecimal purchasePrice;
    private final LocalDate purchaseDate;
    private final BigDecimal dailyChangeValue;
    private final BigDecimal dailyChangePercent;
    private final BigDecimal totalChangeValue;
    private final BigDecimal totalChangePercent;
    private final String logoUrl;

    public Holding(UUID id, UUID portfolioId, String name, String symbol, Integer quantity, BigDecimal price, BigDecimal purchasePrice, LocalDate purchaseDate, BigDecimal dailyChangeValue, BigDecimal dailyChangePercent, BigDecimal totalChangeValue, BigDecimal totalChangePercent, String logoUrl) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.name = name;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.dailyChangeValue = dailyChangeValue;
        this.dailyChangePercent = dailyChangePercent;
        this.totalChangeValue = totalChangeValue;
        this.totalChangePercent = totalChangePercent;
        this.logoUrl = logoUrl;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public BigDecimal getDailyChangeValue() {
        return dailyChangeValue;
    }

    public BigDecimal getDailyChangePercent() {
        return dailyChangePercent;
    }

    public BigDecimal getTotalChangeValue() {
        return totalChangeValue;
    }

    public BigDecimal getTotalChangePercent() {
        return totalChangePercent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID portfolioId;
        private String name;
        private String symbol;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal purchasePrice;
        private LocalDate purchaseDate;
        private BigDecimal dailyChangeValue;
        private BigDecimal dailyChangePercent;
        private BigDecimal totalChangeValue;
        private BigDecimal totalChangePercent;
        private String logoUrl;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder portfolioId(UUID portfolioId) {
            this.portfolioId = portfolioId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder purchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
            return this;
        }

        public Builder purchaseDate(LocalDate purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public Builder dailyChangeValue(BigDecimal dailyChangeValue) {
            this.dailyChangeValue = dailyChangeValue;
            return this;
        }

        public Builder dailyChangePercent(BigDecimal dailyChangePercent) {
            this.dailyChangePercent = dailyChangePercent;
            return this;
        }

        public Builder totalChangeValue(BigDecimal totalChangeValue) {
            this.totalChangeValue = totalChangeValue;
            return this;
        }

        public Builder totalChangePercent(BigDecimal totalChangePercent) {
            this.totalChangePercent = totalChangePercent;
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Holding build() {
            return new Holding(id, portfolioId, name, symbol, quantity, price, purchasePrice, purchaseDate, dailyChangeValue, dailyChangePercent, totalChangeValue, totalChangePercent, logoUrl);
        }
    }
}