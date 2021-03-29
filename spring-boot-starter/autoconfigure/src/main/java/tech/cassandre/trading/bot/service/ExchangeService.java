package tech.cassandre.trading.bot.service;

import tech.cassandre.trading.bot.dto.util.CurrencyPair;

import java.util.Set;

/**
 * Service getting information from the exchange.
 */
public interface ExchangeService {

    /**
     * Get the list of available currency pairs for trading.
     *
     * @return list of currency pairs
     */
    Set<CurrencyPair> getAvailableCurrencyPairs();

}
