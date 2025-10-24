package de.mk.portfolio.adapter.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "holding")
public class HoldingEntity {
    @Id
    private UUID id;
    private String name;
    private String symbol;
    private Integer quantity;
    private BigDecimal price;
    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
    private BigDecimal dailyChangeValue;
    private BigDecimal dailyChangePercent;
    private BigDecimal totalChangeValue;
    private BigDecimal totalChangePercent;
    private String logoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    public HoldingEntity() {
    }

    public HoldingEntity(UUID id, String name, String symbol, Integer quantity, BigDecimal price, BigDecimal purchasePrice, LocalDate purchaseDate, BigDecimal dailyChangeValue, BigDecimal dailyChangePercent, BigDecimal totalChangeValue, BigDecimal totalChangePercent, String logoUrl, PortfolioEntity portfolio) {
        this.id = id;
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
        this.portfolio = portfolio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getDailyChangeValue() {
        return dailyChangeValue;
    }

    public void setDailyChangeValue(BigDecimal dailyChangeValue) {
        this.dailyChangeValue = dailyChangeValue;
    }

    public BigDecimal getDailyChangePercent() {
        return dailyChangePercent;
    }

    public void setDailyChangePercent(BigDecimal dailyChangePercent) {
        this.dailyChangePercent = dailyChangePercent;
    }

    public BigDecimal getTotalChangeValue() {
        return totalChangeValue;
    }

    public void setTotalChangeValue(BigDecimal totalChangeValue) {
        this.totalChangeValue = totalChangeValue;
    }

    public BigDecimal getTotalChangePercent() {
        return totalChangePercent;
    }

    public void setTotalChangePercent(BigDecimal totalChangePercent) {
        this.totalChangePercent = totalChangePercent;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public PortfolioEntity getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioEntity portfolio) {
        this.portfolio = portfolio;
    }
}