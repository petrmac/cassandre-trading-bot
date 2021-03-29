package tech.cassandre.trading.bot.service;

import tech.cassandre.trading.bot.dto.market.Ticker;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;

import java.util.Optional;

/**
 * Service getting information about market price.
 */
public interface MarketService {

    /**
     * Returns a ticker for a currency pair.
     *
     * @param currencyPair currency pair
     * @return ticker
     */
    Optional<Ticker> getTicker(CurrencyPair currencyPair);

}
