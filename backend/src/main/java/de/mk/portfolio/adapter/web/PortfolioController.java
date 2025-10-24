package de.mk.portfolio.adapter.web;

import de.mk.portfolio.domain.model.Holding;
import de.mk.portfolio.domain.model.Portfolio;
import de.mk.portfolio.domain.model.PortfolioHistory;
import de.mk.portfolio.domain.port.in.AddStockUseCase;
import de.mk.portfolio.domain.port.in.GetHoldingsUseCase;
import de.mk.portfolio.domain.port.in.GetPortfolioHistoryUseCase;
import de.mk.portfolio.domain.port.in.GetPortfolioUseCase;
import de.mk.portfolio.domain.port.in.SellStockUseCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private static final Logger logger = LogManager.getLogger(PortfolioController.class);

    private final GetPortfolioUseCase getPortfolioUseCase;
    private final GetHoldingsUseCase getHoldingsUseCase;
    private final GetPortfolioHistoryUseCase getPortfolioHistoryUseCase;
    private final AddStockUseCase addStockUseCase;
    private final SellStockUseCase sellStockUseCase;

    // For simplicity, we'll use a fixed portfolio ID for now
    private final UUID FIXED_PORTFOLIO_ID = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");

    public PortfolioController(GetPortfolioUseCase getPortfolioUseCase, GetHoldingsUseCase getHoldingsUseCase, GetPortfolioHistoryUseCase getPortfolioHistoryUseCase, AddStockUseCase addStockUseCase, SellStockUseCase sellStockUseCase) {
        this.getPortfolioUseCase = getPortfolioUseCase;
        this.getHoldingsUseCase = getHoldingsUseCase;
        this.getPortfolioHistoryUseCase = getPortfolioHistoryUseCase;
        this.addStockUseCase = addStockUseCase;
        this.sellStockUseCase = sellStockUseCase;
    }

    @GetMapping
    public ResponseEntity<PortfolioResponse> getPortfolio() {
        logger.info("Getting portfolio");
        Portfolio portfolio = getPortfolioUseCase.getPortfolio();
        return ResponseEntity.ok(mapToPortfolioResponse(portfolio));
    }

    @GetMapping("/holdings")
    public ResponseEntity<List<HoldingResponse>> getHoldings() {
        logger.info("Getting holdings");
        List<Holding> holdings = getHoldingsUseCase.getHoldings(FIXED_PORTFOLIO_ID);
        return ResponseEntity.ok(holdings.stream().map(this::mapToHoldingResponse).collect(Collectors.toList()));
    }

    @GetMapping("/history")
    public ResponseEntity<List<PortfolioHistoryResponse>> getPortfolioHistory(@RequestParam(defaultValue = "1M") String range) {
        logger.info("Getting portfolio history with range: {}", range);
        List<PortfolioHistory> history = getPortfolioHistoryUseCase.getPortfolioHistory(FIXED_PORTFOLIO_ID, range);
        return ResponseEntity.ok(history.stream().map(this::mapToPortfolioHistoryResponse).collect(Collectors.toList()));
    }

    @PostMapping("/holdings")
    public ResponseEntity<HoldingResponse> addStock(@RequestBody AddStockUseCase.AddStockCommand request) {
        logger.info("Adding stock: {}", request.symbol());
        Holding holding = addStockUseCase.addStock(request);
        return ResponseEntity.ok(mapToHoldingResponse(holding));
    }

    @DeleteMapping("/holdings/{symbol}")
    public ResponseEntity<Void> sellStock(@PathVariable String symbol, @RequestParam int quantity) {
        logger.info("Selling stock: {} with quantity: {}", symbol, quantity);
        sellStockUseCase.sellStock(FIXED_PORTFOLIO_ID, symbol, quantity);
        return ResponseEntity.noContent().build();
    }

    // --- Mappers to DTOs ---
    private PortfolioResponse mapToPortfolioResponse(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getId(),
                portfolio.getTotalValue(),
                portfolio.getDailyChangeValue(),
                portfolio.getDailyChangePercent(),
                portfolio.getTotalChangeValue(),
                portfolio.getTotalChangePercent()
        );
    }

    private HoldingResponse mapToHoldingResponse(Holding holding) {
        return new HoldingResponse(
                holding.getId(),
                holding.getName(),
                holding.getSymbol(),
                holding.getQuantity(),
                holding.getPrice(),
                holding.getDailyChangeValue(),
                holding.getDailyChangePercent(),
                holding.getTotalChangeValue(),
                holding.getTotalChangePercent(),
                holding.getLogoUrl()
        );
    }

    private PortfolioHistoryResponse mapToPortfolioHistoryResponse(PortfolioHistory history) {
        return new PortfolioHistoryResponse(
                history.getDate(),
                history.getValue()
        );
    }

    // --- DTOs ---
    record PortfolioResponse(
            UUID id,
            BigDecimal totalValue,
            BigDecimal dailyChangeValue,
            BigDecimal dailyChangePercent,
            BigDecimal totalChangeValue,
            BigDecimal totalChangePercent
    ) {}

    record HoldingResponse(
            UUID id,
            String name,
            String symbol,
            Integer quantity,
            BigDecimal price,
            BigDecimal dailyChangeValue,
            BigDecimal dailyChangePercent,
            BigDecimal totalChangeValue,
            BigDecimal totalChangePercent,
            String logoUrl
    ) {}

    record PortfolioHistoryResponse(
            LocalDate date,
            BigDecimal value
    ) {}
}
