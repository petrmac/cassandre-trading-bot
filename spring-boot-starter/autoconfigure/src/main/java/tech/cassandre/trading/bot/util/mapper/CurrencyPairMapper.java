package tech.cassandre.trading.bot.util.mapper;

import org.mapstruct.Mapper;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface CurrencyPairMapper {

    default org.knowm.xchange.currency.CurrencyPair toXchangeCurrencyPair(CurrencyPair source) {
        return new org.knowm.xchange.currency.CurrencyPair(
                source.getBaseCurrency().getSymbol(),
                source.getQuoteCurrency().getSymbol());
    }

    default org.knowm.xchange.currency.CurrencyPair toXchangeCurrencyPair(String source) {
        return new org.knowm.xchange.currency.CurrencyPair(source);
    }

    default CurrencyPair toDomainCurrencyPair(org.knowm.xchange.currency.CurrencyPair source) {
        return CurrencyPair.forValues(source.base.getCurrencyCode(), source.counter.getCurrencyCode());
    }

    default CurrencyPair toDomainCurrencyPair(String source) {
        return CurrencyPair.forValue(source);
    }

    default CurrencyPair toDomainCurrencyPair(org.knowm.xchange.instrument.Instrument source) {
        if (source instanceof org.knowm.xchange.currency.CurrencyPair) {
            return toDomainCurrencyPair((org.knowm.xchange.currency.CurrencyPair) source);
        }
        throw new IllegalArgumentException("Cannot map instrument to CurrencyPair");
    }

    default String toString(CurrencyPair source) {
        return source.toString();
    }

}
