package de.mk.portfolio.domain.port.in;

import de.mk.portfolio.domain.model.PortfolioHistory;

import java.util.List;
import java.util.UUID;

public interface GetPortfolioHistoryUseCase {
    List<PortfolioHistory> getPortfolioHistory(UUID portfolioId, String range);
}
