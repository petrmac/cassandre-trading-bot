package tech.cassandre.trading.bot.dto.util;

import org.knowm.xchange.instrument.Instrument;

public interface CurrencyPair {
    Currency getBaseCurrency();
    Currency getQuoteCurrency();

    static CurrencyPair forValue(final String value) {
        return new CurrencyPairDTO(value);
    }

    static CurrencyPair forValue(final org.knowm.xchange.currency.CurrencyPair value) {
        return new CurrencyPairDTO(value);
    }

    static CurrencyPair forValue(final Instrument value) {
        return new CurrencyPairDTO(value);
    }

    static CurrencyPair forValues(final String baseCurrency, final String quoteCurrency) {
        return new CurrencyPairDTO(baseCurrency, quoteCurrency);
    }

    static CurrencyPair forValues(final Currency baseCurrency, final Currency quoteCurrency) {
        return new CurrencyPairDTO((CurrencyDTO) baseCurrency, (CurrencyDTO) quoteCurrency);
    }
}
