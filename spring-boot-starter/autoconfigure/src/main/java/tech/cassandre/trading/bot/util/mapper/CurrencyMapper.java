package tech.cassandre.trading.bot.util.mapper;

import org.mapstruct.Mapper;
import tech.cassandre.trading.bot.dto.util.Currency;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;


/**
 * Currency mapper.
 */
@Mapper (componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface CurrencyMapper {

    default org.knowm.xchange.currency.Currency toXchangeCurrency(Currency source) {
        return new org.knowm.xchange.currency.Currency(source.getCode());
    }

    default Currency toDomainCurrency(org.knowm.xchange.currency.Currency source) {
        return Currency.forValue(source.getCurrencyCode());
    }

    default String toStringCurrency(org.knowm.xchange.currency.Currency source){
        return source.getCurrencyCode();
    }

    default String toStringCurrency(Currency source){
        return source.getCode();
    }

    default Currency toDomainCurrency(String source) {
        return Currency.forValue(source);
    }
}
