package de.mk.portfolio.application;

import de.mk.portfolio.domain.model.Holding;
import de.mk.portfolio.domain.model.Portfolio;
import de.mk.portfolio.domain.model.PortfolioHistory;
import de.mk.portfolio.domain.port.in.*;
import de.mk.portfolio.domain.port.out.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PortfolioService implements
        GetPortfolioUseCase,
        GetHoldingsUseCase,
        GetPortfolioHistoryUseCase,
        AddStockUseCase,
        SellStockUseCase {

    private final LoadPortfolioPort loadPortfolioPort;
    private final LoadHoldingsPort loadHoldingsPort;
    private final LoadPortfolioHistoryPort loadPortfolioHistoryPort;
    private final SavePortfolioPort savePortfolioPort;
    private final SaveHoldingPort saveHoldingPort;
    private final DeleteHoldingPort deleteHoldingPort;

    // For simplicity, we'll use a fixed portfolio ID for now
    private final UUID FIXED_PORTFOLIO_ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    public PortfolioService(LoadPortfolioPort loadPortfolioPort, LoadHoldingsPort loadHoldingsPort, LoadPortfolioHistoryPort loadPortfolioHistoryPort, SavePortfolioPort savePortfolioPort, SaveHoldingPort saveHoldingPort, DeleteHoldingPort deleteHoldingPort) {
        this.loadPortfolioPort = loadPortfolioPort;
        this.loadHoldingsPort = loadHoldingsPort;
        this.loadPortfolioHistoryPort = loadPortfolioHistoryPort;
        this.savePortfolioPort = savePortfolioPort;
        this.saveHoldingPort = saveHoldingPort;
        this.deleteHoldingPort = deleteHoldingPort;
    }

    @Override
    public Portfolio getPortfolio() {
        Optional<Portfolio> portfolio = loadPortfolioPort.loadPortfolio(FIXED_PORTFOLIO_ID);
        if (portfolio.isEmpty()) {
            // Create a dummy portfolio if none exists
            return savePortfolioPort.savePortfolio(Portfolio.builder()
                    .id(FIXED_PORTFOLIO_ID)
                    .totalValue(new BigDecimal("0.00"))
                    .dailyChangeValue(new BigDecimal("0.00"))
                    .dailyChangePercent(new BigDecimal("0.00"))
                    .totalChangeValue(new BigDecimal("0.00"))
                    .totalChangePercent(new BigDecimal("0.00"))
                    .history(List.of())
                    .build());
        }
        return portfolio.get();
    }

    @Override
    public List<Holding> getHoldings(UUID portfolioId) {
        return loadHoldingsPort.loadHoldings(portfolioId);
    }

    @Override
    public List<PortfolioHistory> getPortfolioHistory(UUID portfolioId, String range) {
        return loadPortfolioHistoryPort.loadPortfolioHistory(portfolioId, range);
    }

    @Override
    public Holding addStock(AddStockCommand command) {
        Portfolio portfolio = getPortfolio(); // This will create a dummy portfolio if none exists

        Holding newHolding = Holding.builder()
                .id(UUID.randomUUID())
                .portfolioId(portfolio.getId())
                .name(command.symbol() + " Company") // Dummy name for now
                .symbol(command.symbol())
                .quantity(command.quantity().intValue()) // Assuming quantity is int for Holding model
                .price(command.purchasePrice()) // Set current price to purchase price for simplicity
                .purchasePrice(command.purchasePrice())
                .purchaseDate(command.purchaseDate())
                .dailyChangeValue(BigDecimal.ZERO)
                .dailyChangePercent(BigDecimal.ZERO)
                .totalChangeValue(BigDecimal.ZERO)
                .totalChangePercent(BigDecimal.ZERO)
                .logoUrl("https://logo.clearbit.com/" + command.symbol().toLowerCase() + ".com")
                .build();

        Holding savedHolding = saveHoldingPort.saveHolding(newHolding);

        // Update portfolio total value (simplified for now)
        BigDecimal newTotalValue = portfolio.getTotalValue().add(command.purchasePrice().multiply(command.quantity()));
        portfolio = portfolio.withTotalValue(newTotalValue);
        savePortfolioPort.savePortfolio(portfolio);

        return savedHolding;
    }

    @Override
    public void sellStock(UUID portfolioId, String symbol, int quantity) {
        // In a real application, you would find the holding and update its quantity or delete it
        // For simplicity, we'll just delete it for now
        loadHoldingsPort.loadHoldings(portfolioId).stream()
                .filter(h -> h.getSymbol().equals(symbol))
                .findFirst()
                .ifPresent(holding -> deleteHoldingPort.deleteHolding(holding.getId()));
    }
}
