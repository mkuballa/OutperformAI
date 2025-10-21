package de.mk.portfolio.domain.port.in;

import de.mk.portfolio.domain.model.Holding;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AddStockUseCase {

    Holding addStock(AddStockCommand command);

    record AddStockCommand(
            String symbol,
            BigDecimal quantity,
            BigDecimal purchasePrice,
            LocalDate purchaseDate
    ) {
    }
}