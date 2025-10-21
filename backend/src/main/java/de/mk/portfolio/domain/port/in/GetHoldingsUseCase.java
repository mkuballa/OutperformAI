package de.mk.portfolio.domain.port.in;

import de.mk.portfolio.domain.model.Holding;

import java.util.List;
import java.util.UUID;

public interface GetHoldingsUseCase {
    List<Holding> getHoldings(UUID portfolioId);
}
