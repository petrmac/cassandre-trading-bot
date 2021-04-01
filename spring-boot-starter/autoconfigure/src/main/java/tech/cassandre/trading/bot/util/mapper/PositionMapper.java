package tech.cassandre.trading.bot.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tech.cassandre.trading.bot.domain.Position;
import tech.cassandre.trading.bot.dto.position.PositionDTO;
import tech.cassandre.trading.bot.dto.position.PositionRulesDTO;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Position mapper.
 */
@Mapper(componentModel = "spring",
        injectionStrategy = CONSTRUCTOR,
        uses = {CurrencyMapper.class, CurrencyPairMapper.class, CurrencyAmountMapper.class,
                OrderMapper.class, UtilMapper.class, StrategyMapper.class},
        nullValuePropertyMappingStrategy = IGNORE)
public interface PositionMapper {

    // =================================================================================================================
    // DTO to Domain.

    @Mapping(source = "rules.stopGainPercentage", target = "stopGainPercentageRule")
    @Mapping(source = "rules.stopLossPercentage", target = "stopLossPercentageRule")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    Position mapToPosition(PositionDTO source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "positionId", ignore = true)
    @Mapping(source = "rules.stopGainPercentage", target = "stopGainPercentageRule")
    @Mapping(source = "rules.stopLossPercentage", target = "stopLossPercentageRule")
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "strategy", ignore = true)
    void updatePosition(PositionDTO source, @MappingTarget Position target);

    // =================================================================================================================
    // Domain to DTO.

    @Mapping(source = "source", target = "rules")
    PositionDTO mapToPositionDTO(Position source);

    default PositionRulesDTO mapToPositionRulesDTO(Position source) {
        PositionRulesDTO rules = PositionRulesDTO.builder().build();
        boolean stopGainRuleSet = source.getStopGainPercentageRule() != null;
        boolean stopLossRuleSet = source.getStopLossPercentageRule() != null;
        // Two rules set.
        if (stopGainRuleSet && stopLossRuleSet) {
            rules = PositionRulesDTO.builder()
                    .stopGainPercentage(source.getStopGainPercentageRule())
                    .stopLossPercentage(source.getStopLossPercentageRule())
                    .build();
        }
        // Stop gain set.
        if (stopGainRuleSet && !stopLossRuleSet) {
            rules = PositionRulesDTO.builder()
                    .stopGainPercentage(source.getStopGainPercentageRule())
                    .build();
        }
        // Stop loss set.
        if (!stopGainRuleSet && stopLossRuleSet) {
            rules = PositionRulesDTO.builder()
                    .stopLossPercentage(source.getStopLossPercentageRule())
                    .build();
        }
        return rules;
    }

}
