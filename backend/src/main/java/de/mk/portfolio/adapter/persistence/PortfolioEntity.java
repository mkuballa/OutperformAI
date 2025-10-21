package de.mk.portfolio.adapter.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "portfolio")
public class PortfolioEntity {
    @Id
    private UUID id;
    private BigDecimal totalValue;
    private BigDecimal dailyChangeValue;
    private BigDecimal dailyChangePercent;
    private BigDecimal totalChangeValue;
    private BigDecimal totalChangePercent;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoldingEntity> holdings;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioHistoryEntity> history;

    public PortfolioEntity() {
    }

    public PortfolioEntity(UUID id, BigDecimal totalValue, BigDecimal dailyChangeValue, BigDecimal dailyChangePercent, BigDecimal totalChangeValue, BigDecimal totalChangePercent, List<HoldingEntity> holdings, List<PortfolioHistoryEntity> history) {
        this.id = id;
        this.totalValue = totalValue;
        this.dailyChangeValue = dailyChangeValue;
        this.dailyChangePercent = dailyChangePercent;
        this.totalChangeValue = totalChangeValue;
        this.totalChangePercent = totalChangePercent;
        this.holdings = holdings;
        this.history = history;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
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

    public List<HoldingEntity> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<HoldingEntity> holdings) {
        this.holdings = holdings;
    }

    public List<PortfolioHistoryEntity> getHistory() {
        return history;
    }

    public void setHistory(List<PortfolioHistoryEntity> history) {
        this.history = history;
    }
}
