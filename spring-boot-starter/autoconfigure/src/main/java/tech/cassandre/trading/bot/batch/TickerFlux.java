package tech.cassandre.trading.bot.batch;

import com.google.common.collect.Iterators;
import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.service.MarketService;
import tech.cassandre.trading.bot.util.base.batch.BaseExternalFlux;
import tech.cassandre.trading.bot.util.mapper.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Ticker flux - push {@link TickerDTO}.
 */
public class TickerFlux extends BaseExternalFlux<Ticker> {

    /** Market service. */
    private final MarketService marketService;

    /** Cycle iterator over requested currency pairs. */
    private Iterator<CurrencyPair> currencyPairsIterator;

    /** Previous values. */
    private final Map<CurrencyPair, Ticker> previousValues = new LinkedHashMap<>();

    /**
     * Constructor.
     *
     * @param mapperService the mapper service
     * @param newMarketService the market service
     */
    public TickerFlux(final MapperService mapperService,
                      final MarketService newMarketService) {
        super(mapperService);
        this.marketService = newMarketService;
    }

    /**
     * Update the list of requested currency pairs.
     *
     * @param requestedCurrencyPairs list of requested currency pairs.
     */
    public void updateRequestedCurrencyPairs(final Set<CurrencyPair> requestedCurrencyPairs) {
        currencyPairsIterator = Iterators.cycle(requestedCurrencyPairs);
    }

    @Override
    protected final Set<Ticker> getNewValues() {
        logger.debug("TickerFlux - Retrieving new values");
        Set<Ticker> newValues = new LinkedHashSet<>();
        marketService.getTicker(currencyPairsIterator.next()).ifPresent(ticker -> {
            if (!ticker.equals(previousValues.get(ticker.getCurrencyPair()))) {
                logger.debug("TickerFlux - New ticker received : {}", ticker);
                previousValues.put(ticker.getCurrencyPair(), ticker);
                newValues.add(ticker);
            }
        });
        return newValues;
    }

    @Override
    protected final Optional<Ticker> saveValue(final Ticker newValue) {
        return Optional.ofNullable(newValue);
    }

}
