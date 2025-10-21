package de.mk.portfolio.domain.port.out;

import de.mk.portfolio.domain.model.Holding;

import java.util.List;
import java.util.UUID;

public interface LoadHoldingsPort {
    List<Holding> loadHoldings(UUID portfolioId);
}
