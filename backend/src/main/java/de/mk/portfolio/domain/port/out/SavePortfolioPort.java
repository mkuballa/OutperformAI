package de.mk.portfolio.domain.port.out;

import de.mk.portfolio.domain.model.Portfolio;

public interface SavePortfolioPort {
    Portfolio savePortfolio(Portfolio portfolio);
}
