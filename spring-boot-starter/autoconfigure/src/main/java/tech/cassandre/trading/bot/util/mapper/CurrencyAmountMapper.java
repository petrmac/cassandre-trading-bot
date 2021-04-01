package tech.cassandre.trading.bot.util.mapper;

import org.mapstruct.Mapper;
import tech.cassandre.trading.bot.dto.util.Currency;
import tech.cassandre.trading.bot.dto.util.CurrencyAmount;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

/**
 * CurrencyAmount mapper.
 */
@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface CurrencyAmountMapper {

    default tech.cassandre.trading.bot.util.jpa.CurrencyAmount toJpaCurrencyAmount(CurrencyAmount source) {
        if (source == null) {
            return null;
        }
        var result = new tech.cassandre.trading.bot.util.jpa.CurrencyAmount();
        result.setCurrency(source.getCurrency().getCode());
        result.setValue(source.getValue());
        return result;
    }

    default CurrencyAmount toDomainCurrencyAmount(tech.cassandre.trading.bot.util.jpa.CurrencyAmount source) {
        if (source == null) {
            return null;
        }
        return CurrencyAmount.forValues(source.getValue(), Currency.forValue(source.getCurrency()));
    }
}
