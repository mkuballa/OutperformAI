package de.mk.portfolio.domain.port.out;

import java.util.UUID;

public interface DeleteHoldingPort {
    void deleteHolding(UUID holdingId);
}
