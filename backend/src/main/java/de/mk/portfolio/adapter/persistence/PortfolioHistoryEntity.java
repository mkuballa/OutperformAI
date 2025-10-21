package de.mk.portfolio.adapter.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "portfolio_history")
public class PortfolioHistoryEntity {
    @Id
    private UUID id;
    private LocalDate date;
    @Column(name = "value")
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private PortfolioEntity portfolio;

    public PortfolioHistoryEntity() {
    }

    public PortfolioHistoryEntity(UUID id, LocalDate date, BigDecimal value, PortfolioEntity portfolio) {
        this.id = id;
        this.date = date;
        this.value = value;
        this.portfolio = portfolio;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public PortfolioEntity getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioEntity portfolio) {
        this.portfolio = portfolio;
    }
}
