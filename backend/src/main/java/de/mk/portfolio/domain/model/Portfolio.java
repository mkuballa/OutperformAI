package de.mk.portfolio.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Portfolio {
    private final UUID id;
    private final BigDecimal totalValue;
    private final BigDecimal dailyChangeValue;
    private final BigDecimal dailyChangePercent;
    private final BigDecimal totalChangeValue;
    private final BigDecimal totalChangePercent;
    private final List<Holding> holdings;
    private final List<PortfolioHistory> history;

    public Portfolio(UUID id, BigDecimal totalValue, BigDecimal dailyChangeValue, BigDecimal dailyChangePercent, BigDecimal totalChangeValue, BigDecimal totalChangePercent, List<Holding> holdings, List<PortfolioHistory> history) {
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

    public BigDecimal getTotalValue() {
        return totalValue;
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

    public List<Holding> getHoldings() {
        return holdings;
    }

    public List<PortfolioHistory> getHistory() {
        return history;
    }

    public Portfolio withTotalValue(BigDecimal newTotalValue) {
        return new Portfolio(this.id, newTotalValue, this.dailyChangeValue, this.dailyChangePercent, this.totalChangeValue, this.totalChangePercent, this.holdings, this.history);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private BigDecimal totalValue;
        private BigDecimal dailyChangeValue;
        private BigDecimal dailyChangePercent;
        private BigDecimal totalChangeValue;
        private BigDecimal totalChangePercent;
        private List<Holding> holdings;
        private List<PortfolioHistory> history;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder totalValue(BigDecimal totalValue) {
            this.totalValue = totalValue;
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

        public Builder holdings(List<Holding> holdings) {
            this.holdings = holdings;
            return this;
        }

        public Builder history(List<PortfolioHistory> history) {
            this.history = history;
            return this;
        }

        public Portfolio build() {
            return new Portfolio(id, totalValue, dailyChangeValue, dailyChangePercent, totalChangeValue, totalChangePercent, holdings, history);
        }
    }
}
