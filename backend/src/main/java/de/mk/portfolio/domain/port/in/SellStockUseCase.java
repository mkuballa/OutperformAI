package de.mk.portfolio.domain.port.in;

import java.util.UUID;

public interface SellStockUseCase {
    void sellStock(UUID portfolioId, String symbol, int quantity);
}
