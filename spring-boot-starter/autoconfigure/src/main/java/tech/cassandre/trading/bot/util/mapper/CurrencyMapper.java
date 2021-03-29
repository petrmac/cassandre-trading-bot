package tech.cassandre.trading.bot.util.mapper;

import org.knowm.xchange.instrument.Instrument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.cassandre.trading.bot.dto.util.Currency;
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO;
import tech.cassandre.trading.bot.util.jpa.CurrencyAmount;

/**
 * Currency mapper.
 */
@Mapper
public interface CurrencyMapper {

    // =================================================================================================================
    // XChange to DTO.

    default String mapToCurrencyString(org.knowm.xchange.currency.Currency source) {
        if (source != null) {
            return source.toString();
        } else {
            return null;
        }
    }

    default Currency mapToCurrencyDTO(String value) {
        return Currency.forString(value);
    }

    @Mapping(source = "currencyCode", target = "code")
    CurrencyDTO mapToCurrencyDTO(org.knowm.xchange.currency.Currency source);

    default String mapToCurrencyPairString(CurrencyPair source) {
        return source.toString();
    }

    default CurrencyPair mapToCurrencyPairDTO(Instrument source) {
        final org.knowm.xchange.currency.CurrencyPair cp = (org.knowm.xchange.currency.CurrencyPair) source;
        return CurrencyPair.forValues(cp.base.getCurrencyCode(), cp.counter.getCurrencyCode());
    }

    default CurrencyPair mapToCurrencyPairDTO(String source) {
        return CurrencyPair.forValue(source);
    }

    @Mapping(source = "base", target = "baseCurrency")
    @Mapping(source = "counter", target = "quoteCurrency")
    CurrencyPairDTO mapToCurrencyPairDTO(org.knowm.xchange.currency.CurrencyPair source);

    @Mapping(source = "value", target = "value")
    @Mapping(source = "currency", target = "currency")
    CurrencyAmountDTO mapToCurrencyAmountDTO(CurrencyAmount source);

    // =================================================================================================================
    // XChange to DTO.

    default org.knowm.xchange.currency.CurrencyPair mapToCurrencyPair(CurrencyPair source) {
        return new org.knowm.xchange.currency.CurrencyPair(source.getBaseCurrency().getCode(), source.getQuoteCurrency().getCode());
    }

}
