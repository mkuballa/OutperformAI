package de.mk.portfolio.domain.port.out;

import de.mk.portfolio.domain.model.Holding;

public interface SaveHoldingPort {
    Holding saveHolding(Holding holding);
}
