package de.mk.portfolio.adapter.persistence;

import de.mk.portfolio.domain.model.Holding;
import de.mk.portfolio.domain.model.Portfolio;
import de.mk.portfolio.domain.model.PortfolioHistory;
import de.mk.portfolio.domain.port.out.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PortfolioPersistenceAdapter implements
        LoadPortfolioPort,
        LoadHoldingsPort,
        LoadPortfolioHistoryPort,
        SavePortfolioPort,
        SaveHoldingPort,
        DeleteHoldingPort {

    private final PortfolioRepository portfolioRepository;
    private final HoldingRepository holdingRepository;
    private final PortfolioHistoryRepository portfolioHistoryRepository;

    public PortfolioPersistenceAdapter(PortfolioRepository portfolioRepository, HoldingRepository holdingRepository, PortfolioHistoryRepository portfolioHistoryRepository) {
        this.portfolioRepository = portfolioRepository;
        this.holdingRepository = holdingRepository;
        this.portfolioHistoryRepository = portfolioHistoryRepository;
    }

    @Override
    public Optional<Portfolio> loadPortfolio(UUID portfolioId) {
        return portfolioRepository.findById(portfolioId).map(this::mapToDomain);
    }

    @Override
    public List<Holding> loadHoldings(UUID portfolioId) {
        return holdingRepository.findByPortfolioId(portfolioId).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PortfolioHistory> loadPortfolioHistory(UUID portfolioId, String range) {
        // For simplicity, range is ignored for now, returning all history
        return portfolioHistoryRepository.findByPortfolioIdOrderByDateAsc(portfolioId).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Portfolio savePortfolio(Portfolio portfolio) {
        PortfolioEntity entity = mapToEntity(portfolio);
        return mapToDomain(portfolioRepository.save(entity));
    }

    @Override
    public Holding saveHolding(Holding holding) {
        HoldingEntity entity = mapToEntity(holding);
        return mapToDomain(holdingRepository.save(entity));
    }

    @Override
    public void deleteHolding(UUID holdingId) {
        holdingRepository.deleteById(holdingId);
    }

    // --- Mappers ---
    private Portfolio mapToDomain(PortfolioEntity entity) {
        return Portfolio.builder()
                .id(entity.getId())
                .totalValue(entity.getTotalValue() != null ? entity.getTotalValue() : BigDecimal.ZERO)
                .dailyChangeValue(entity.getDailyChangeValue() != null ? entity.getDailyChangeValue() : BigDecimal.ZERO)
                .dailyChangePercent(entity.getDailyChangePercent() != null ? entity.getDailyChangePercent() : BigDecimal.ZERO)
                .totalChangeValue(entity.getTotalChangeValue() != null ? entity.getTotalChangeValue() : BigDecimal.ZERO)
                .totalChangePercent(entity.getTotalChangePercent() != null ? entity.getTotalChangePercent() : BigDecimal.ZERO)
                .holdings(entity.getHoldings().stream().map(h -> mapToDomain(h)).collect(Collectors.toList()))
                .build();
    }

    private PortfolioEntity mapToEntity(Portfolio portfolio) {
        PortfolioEntity entity = portfolioRepository.findById(portfolio.getId()).orElse(new PortfolioEntity());
        entity.setId(portfolio.getId());
        entity.setTotalValue(portfolio.getTotalValue());
        entity.setDailyChangeValue(portfolio.getDailyChangeValue());
        entity.setDailyChangePercent(portfolio.getDailyChangePercent());
        entity.setTotalChangeValue(portfolio.getTotalChangeValue());
        entity.setTotalChangePercent(portfolio.getTotalChangePercent());
        // The history collection is managed by Hibernate, so we don't explicitly set it here
        // when mapping from domain to entity for saving. Hibernate will handle updates
        // based on changes within the managed entity's collection.
        return entity;
    }

    private Holding mapToDomain(HoldingEntity entity) {
        return Holding.builder()
                .id(entity.getId())
                .name(entity.getName())
                .symbol(entity.getSymbol())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .purchasePrice(entity.getPurchasePrice())
                .purchaseDate(entity.getPurchaseDate())
                .dailyChangeValue(entity.getDailyChangeValue())
                .dailyChangePercent(entity.getDailyChangePercent())
                .totalChangeValue(entity.getTotalChangeValue())
                .totalChangePercent(entity.getTotalChangePercent())
                .logoUrl(entity.getLogoUrl())
                .build();
    }

    private HoldingEntity mapToEntity(Holding holding) {
        HoldingEntity entity = new HoldingEntity();
        entity.setId(holding.getId());
        entity.setName(holding.getName());
        entity.setSymbol(holding.getSymbol());
        entity.setQuantity(holding.getQuantity());
        entity.setPrice(holding.getPrice());
        entity.setPurchasePrice(holding.getPurchasePrice());
        entity.setPurchaseDate(holding.getPurchaseDate());
        entity.setDailyChangeValue(holding.getDailyChangeValue());
        entity.setDailyChangePercent(holding.getDailyChangePercent());
        entity.setTotalChangeValue(holding.getTotalChangeValue());
        entity.setTotalChangePercent(holding.getTotalChangePercent());
        entity.setLogoUrl(holding.getLogoUrl());
        // Set the portfolio relationship
        PortfolioEntity portfolioEntity = new PortfolioEntity();
        portfolioEntity.setId(holding.getPortfolioId());
        entity.setPortfolio(portfolioEntity);
        return entity;
    }

    private PortfolioHistory mapToDomain(PortfolioHistoryEntity entity) {
        return PortfolioHistory.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .value(entity.getValue())
                .build();
    }

    private PortfolioHistoryEntity mapToEntity(PortfolioHistory history) {
        PortfolioHistoryEntity entity = new PortfolioHistoryEntity();
        entity.setId(history.getId());
        entity.setDate(history.getDate());
        entity.setValue(history.getValue());
        // Portfolio relationship needs to be set if available
        return entity;
    }
}
