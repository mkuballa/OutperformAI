package de.mk.portfolio.application;

import de.mk.portfolio.domain.model.Holding;
import de.mk.portfolio.domain.model.Portfolio;
import de.mk.portfolio.domain.model.PortfolioHistory;
import de.mk.portfolio.domain.port.in.*;
import de.mk.portfolio.domain.port.out.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.math.RoundingMode;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Random;

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
        List<Holding> holdings = loadHoldingsPort.loadHoldings(portfolioId);
        return holdings.stream().map(holding -> {
            BigDecimal totalChangeValue = (holding.getPrice().subtract(holding.getPurchasePrice())).multiply(new BigDecimal(holding.getQuantity()));
            BigDecimal totalChangePercent = BigDecimal.ZERO;
            if (holding.getPurchasePrice().multiply(new BigDecimal(holding.getQuantity())).compareTo(BigDecimal.ZERO) != 0) {
                totalChangePercent = totalChangeValue.divide(holding.getPurchasePrice().multiply(new BigDecimal(holding.getQuantity())), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            }
            return Holding.builder()
                    .id(holding.getId())
                    .portfolioId(holding.getPortfolioId())
                    .name(holding.getName())
                    .symbol(holding.getSymbol())
                    .quantity(holding.getQuantity())
                    .price(holding.getPrice())
                    .purchasePrice(holding.getPurchasePrice())
                    .purchaseDate(holding.getPurchaseDate())
                    .dailyChangeValue(holding.getDailyChangeValue())
                    .dailyChangePercent(holding.getDailyChangePercent())
                    .totalChangeValue(totalChangeValue)
                    .totalChangePercent(totalChangePercent)
                    .logoUrl(holding.getLogoUrl())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<PortfolioHistory> getPortfolioHistory(UUID portfolioId, String range) {
        return loadPortfolioHistoryPort.loadPortfolioHistory(portfolioId, range);
    }

    @Override
    public Holding addStock(AddStockCommand command) {
        Portfolio portfolio = getPortfolio(); // This will create a dummy portfolio if none exists

        // Simulate a random price change
        BigDecimal purchasePrice = command.purchasePrice();
        double percentageChange = (new Random().nextDouble() * 0.2) - 0.1; // -0.1 to 0.1
        BigDecimal price = purchasePrice.multiply(BigDecimal.ONE.add(new BigDecimal(percentageChange)));

        Holding newHolding = Holding.builder()
                .id(UUID.randomUUID())
                .portfolioId(portfolio.getId())
                .name(command.symbol() + " Company") // Dummy name for now
                .symbol(command.symbol())
                .quantity(command.quantity().intValue()) // Assuming quantity is int for Holding model
                .price(price) // Set current price with random change
                .purchasePrice(purchasePrice)
                .purchaseDate(command.purchaseDate())
                .dailyChangeValue(BigDecimal.ZERO)
                .dailyChangePercent(BigDecimal.ZERO)
                .totalChangeValue(BigDecimal.ZERO)
                .totalChangePercent(BigDecimal.ZERO)
                .logoUrl("https://logo.clearbit.com/" + command.symbol().toLowerCase() + ".com")
                .build();

        Holding savedHolding = saveHoldingPort.saveHolding(newHolding);

        // Update portfolio total value (simplified for now)
        BigDecimal newTotalValue = portfolio.getTotalValue().add(price.multiply(command.quantity()));
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
