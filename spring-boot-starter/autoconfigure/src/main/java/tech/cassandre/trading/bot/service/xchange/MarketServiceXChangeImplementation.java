package tech.cassandre.trading.bot.service.xchange;

import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.util.base.service.BaseService;
import tech.cassandre.trading.bot.util.mapper.CurrencyPairMapper;
import tech.cassandre.trading.bot.util.mapper.MapperService;
import tech.cassandre.trading.bot.util.mapper.TickerMapper;

import java.io.IOException;
import java.util.Optional;

/**
 * Market service - XChange implementation.
 */
public class MarketServiceXChangeImplementation extends BaseService implements MarketService {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** XChange service. */
    private final MarketDataService marketDataService;

    /**
     * Constructor.
     *
     * @param rate                 rate in ms
     * @param newMarketDataService market data service
     */
    public MarketServiceXChangeImplementation(final long rate,
                                              final MarketDataService newMarketDataService,
                                              final MapperService mapperService) {
        super(mapperService, rate);
        this.marketDataService = newMarketDataService;
    }

    @Override
    public final Optional<Ticker> getTicker(final CurrencyPair currencyPair) {
        final TickerMapper tickerMapper = getMapperService().getTickerMapper();
        final CurrencyPairMapper currencyPairMapper = getMapperService().getCurrencyPairMapper();
        try {
            // Consume a token from the token bucket.
            // If a token is not available this method will block until the refill adds one to the bucket.
            getBucket().asScheduler().consume(1);

            logger.debug("MarketService - Getting ticker for {}", currencyPair);
            Ticker t = tickerMapper
                    .mapToTickerDTO(marketDataService.getTicker(currencyPairMapper.toXchangeCurrencyPair(currencyPair)));
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
