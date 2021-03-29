package tech.cassandre.trading.bot.dto.market;

import tech.cassandre.trading.bot.dto.util.Currency;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * An object representing a stock ticker.
 * A ticker is a report of the price of certain securities, updated continuously throughout the trading session.
 */
public interface Ticker {
    /**
     * Currency pair.
     * @return currency pair
     */
    CurrencyPair getCurrencyPair();

    /**
     * The opening price is the first trade price that was recorded during the day’s trading.
     * @return open price
     */
    BigDecimal getOpen();

    /**
     * Last trade field is the price set during the last trade.
     * @return last price
     */
    BigDecimal getLast();

    /**
     * The bid price shown represents the highest bid price.
     * @return bid price
     */
    BigDecimal getBid();

    /**
     * The ask price shown represents the lowest bid price.
     *
     * @return the ask price
     */
    BigDecimal getAsk();

    /**
     * The day’s high price.
     *
     * @return the high price
     */
    BigDecimal getHigh();

    /**
     * The day’s low price.
     *
     * @return the low price
     */
    BigDecimal getLow();

    /**
     * Volume is the number of shares or contracts traded.
     * @return volume
     */
    BigDecimal getVolume();

    /**
     * Quote volume.
     * @return quote volume
     */
    BigDecimal getQuoteVolume();

    /**
     * The bid size represents the quantity of a security that investors are willing to purchase at a specified bid price.
     * @return bid size
     */
    BigDecimal getBidSize();

    /**
     * The ask size represents the quantity of a security that investors are willing to sell at a specified selling price.
     * @return ask size
     */
    BigDecimal getAskSize();

    /**
     * Information timestamp.
     * @return timestamp
     */
    ZonedDateTime getTimestamp();

    /**
     * Returns base currency.
     *
     * @return base currency
     */
    default Optional<Currency> getBaseCurrency() {
        if (getCurrencyPair() != null) {
            return Optional.ofNullable(getCurrencyPair().getBaseCurrency());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns quote currency.
     *
     * @return quote currency
     */
    default Optional<Currency> getQuoteCurrency() {
        if (getCurrencyPair() != null) {
            return Optional.ofNullable(getCurrencyPair().getQuoteCurrency());
        } else {
            return Optional.empty();
        }
    }
}
