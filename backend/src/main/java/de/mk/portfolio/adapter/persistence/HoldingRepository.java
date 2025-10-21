package de.mk.portfolio.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HoldingRepository extends JpaRepository<HoldingEntity, UUID> {
    List<HoldingEntity> findByPortfolioId(UUID portfolioId);
    void deleteByPortfolioIdAndSymbol(UUID portfolioId, String symbol);
}
