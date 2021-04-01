package tech.cassandre.trading.bot.util.mapper;

import org.knowm.xchange.dto.marketdata.Ticker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.cassandre.trading.bot.dto.market.TickerDTO;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

/**
 * Ticker mapper.
 */
@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, uses = CurrencyPairMapper.class)
public interface TickerMapper {

    // =================================================================================================================
    // XChange to DTO.

    @Mapping(source = "instrument", target = "currencyPair")
    TickerDTO mapToTickerDTO(Ticker source);

}
