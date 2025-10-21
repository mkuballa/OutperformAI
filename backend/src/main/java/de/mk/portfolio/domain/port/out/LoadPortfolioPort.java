package de.mk.portfolio.domain.port.out;

import de.mk.portfolio.domain.model.Portfolio;

import java.util.Optional;
import java.util.UUID;

public interface LoadPortfolioPort {
    Optional<Portfolio> loadPortfolio(UUID portfolioId);
}
