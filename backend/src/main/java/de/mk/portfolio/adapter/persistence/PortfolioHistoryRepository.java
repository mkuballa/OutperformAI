package de.mk.portfolio.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortfolioHistoryRepository extends JpaRepository<PortfolioHistoryEntity, UUID> {
    List<PortfolioHistoryEntity> findByPortfolioIdOrderByDateAsc(UUID portfolioId);
}
