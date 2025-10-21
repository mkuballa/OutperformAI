package de.mk.portfolio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PortfolioHistory {
    private final UUID id;
    private final UUID portfolioId;
    private final LocalDate date;
    private final BigDecimal value;

    public PortfolioHistory(UUID id, UUID portfolioId, LocalDate date, BigDecimal value) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.date = date;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private UUID portfolioId;
        private LocalDate date;
        private BigDecimal value;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder portfolioId(UUID portfolioId) {
            this.portfolioId = portfolioId;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public PortfolioHistory build() {
            return new PortfolioHistory(id, portfolioId, date, value);
        }
    }
}
