package tech.cassandre.trading.bot.service.xchange;

import org.knowm.xchange.service.marketdata.MarketDataService;
import tech.cassandre.trading.bot.dto.market.TickerDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.util.base.service.BaseService;

import java.io.IOException;
import java.util.Optional;

/**
 * Market service - XChange implementation.
 */
public class MarketServiceXChangeImplementation extends BaseService implements MarketService {

    /** XChange service. */
    private final MarketDataService marketDataService;

    /**
     * Constructor.
     *
     * @param rate                 rate in ms
     * @param newMarketDataService market data service
     */
    public MarketServiceXChangeImplementation(final long rate, final MarketDataService newMarketDataService) {
        super(rate);
        this.marketDataService = newMarketDataService;
    }

    @Override
    public final Optional<TickerDTO> getTicker(final CurrencyPair currencyPair) {
        try {
            // Consume a token from the token bucket.
            // If a token is not available this method will block until the refill adds one to the bucket.
            getBucket().asScheduler().consume(1);

            logger.debug("MarketService - Getting ticker for {}", currencyPair);
            TickerDTO t = tickerMapper.mapToTickerDTO(marketDataService.getTicker(currencyMapper.mapToCurrencyPair(currencyPair)));
            logger.debug("MarketService - Retrieved value is : {}", t);
            return Optional.ofNullable(t);
        } catch (IOException e) {
            logger.error("MarketService - Error retrieving ticker about {} : {}", currencyPair, e.getMessage());
            return Optional.empty();
        } catch (InterruptedException e) {
            logger.error("MarketService - InterruptedException {} : {}", currencyPair, e.getMessage());
            return Optional.empty();
        }
    }

}
