package tech.cassandre.trading.bot.dto.util;

import java.math.BigDecimal;

public interface CurrencyAmount {

    BigDecimal getValue();

    Currency getCurrency();

    static CurrencyAmount forValues(BigDecimal amount, Currency currency) {
        return new CurrencyAmountDTO(amount, currency);
    }

    static CurrencyAmount forValues(String amount, Currency currency) {
        return new CurrencyAmountDTO(amount, currency);
    }

    static CurrencyAmount forValues(BigDecimal amount, CurrencyPair currencyPair) {
        return forValues(amount, currencyPair.getBaseCurrency());
    }

    static CurrencyAmount forValues(String amount, CurrencyPair currencyPair) {
        return forValues(amount, currencyPair.getBaseCurrency());
    }

}
