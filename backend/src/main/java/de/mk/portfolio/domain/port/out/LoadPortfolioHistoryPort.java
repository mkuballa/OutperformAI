package de.mk.portfolio.domain.port.out;

import de.mk.portfolio.domain.model.PortfolioHistory;

import java.util.List;
import java.util.UUID;

public interface LoadPortfolioHistoryPort {
    List<PortfolioHistory> loadPortfolioHistory(UUID portfolioId, String range);
}
