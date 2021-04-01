package tech.cassandre.trading.bot.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.cassandre.trading.bot.domain.Strategy;
import tech.cassandre.trading.bot.dto.strategy.StrategyDTO;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

/**
 * Strategy mapper.
 */
@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR)
public interface StrategyMapper {

    // =================================================================================================================
    // DTO to Domain.

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "exchangeAccount", ignore = true)
    Strategy mapToStrategy(StrategyDTO source);

    // =================================================================================================================
    // Domain to DTO.

    StrategyDTO mapToStrategyDTO(Strategy source);

}
