package tech.cassandre.trading.bot.util.mapper;

import org.knowm.xchange.dto.trade.LimitOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import tech.cassandre.trading.bot.dto.trade.OrderDTO;
import tech.cassandre.trading.bot.dto.util.Currency;
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;

import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * Order mapper.
 */
@Mapper(uses = {UtilMapper.class, CurrencyMapper.class, TradeMapper.class, StrategyMapper.class}, nullValuePropertyMappingStrategy = IGNORE)
public interface OrderMapper {

    // =================================================================================================================
    // XChange to DTO.

    @Mapping(source = "id", target = "orderId")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "source", target = "amount", qualifiedByName = "mapLimitOrderToOrderDTOAmount")
    @Mapping(source = "source", target = "cumulativeAmount", qualifiedByName = "mapLimitOrderToOrderDTOCumulativeAmount")
    @Mapping(source = "source", target = "averagePrice", qualifiedByName = "mapLimitOrderToOrderDTOAveragePrice")
    @Mapping(source = "source", target = "limitPrice", qualifiedByName = "mapLimitOrderToOrderDTOLimitPrice")
    @Mapping(source = "instrument", target = "currencyPair")
    @Mapping(target = "strategy", ignore = true)
    @Mapping(target = "trades", ignore = true)
    @Mapping(target = "trade", ignore = true)
    OrderDTO mapToOrderDTO(LimitOrder source);

    @Named("mapLimitOrderToOrderDTOAmount")
    default CurrencyAmountDTO mapLimitOrderToOrderDTOAmount(LimitOrder source) {
        CurrencyPair cp = CurrencyPair.forValue(source.getInstrument());
        if (source.getOriginalAmount() != null && source.getInstrument() != null) {
            return CurrencyAmountDTO.builder()
                    .value(source.getOriginalAmount())
                    .currency(cp.getBaseCurrency())
                    .build();
        } else {
            return null;
        }
    }

    @Named("mapLimitOrderToOrderDTOCumulativeAmount")
    default CurrencyAmountDTO mapLimitOrderToOrderDTOCumulativeAmount(LimitOrder source) {
        CurrencyPair cp = CurrencyPair.forValue(source.getInstrument());
        if (source.getCumulativeAmount() != null && source.getInstrument() != null) {
            return CurrencyAmountDTO.builder()
                    .value(source.getCumulativeAmount())
                    .currency(cp.getBaseCurrency())
                    .build();
        } else {
            return null;
        }
    }

    @Named("mapLimitOrderToOrderDTOAveragePrice")
    default CurrencyAmountDTO mapLimitOrderToOrderDTOAveragePrice(LimitOrder source) {
        CurrencyPair cp = CurrencyPair.forValue(source.getInstrument());
        if (source.getAveragePrice() != null && source.getInstrument() != null) {
            return CurrencyAmountDTO.builder()
                    .value(source.getAveragePrice())
                    .currency(cp.getQuoteCurrency())
                    .build();
        } else {
            return null;
        }
    }

    @Named("mapLimitOrderToOrderDTOLimitPrice")
    default CurrencyAmountDTO mapLimitOrderToOrderDTOLimitPrice(LimitOrder source) {
        CurrencyPair cp = CurrencyPair.forValue(source.getInstrument());
        if (source.getLimitPrice() != null && source.getInstrument() != null) {
            return CurrencyAmountDTO.builder()
                    .value(source.getLimitPrice())
                    .currency(cp.getQuoteCurrency())
                    .build();
        } else {
            return null;
        }
    }

    // =================================================================================================================
    // DTO to domain.

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(source = "currencyPair", target = "currencyPair")
    tech.cassandre.trading.bot.domain.Order mapToOrder(OrderDTO source);

    @Named("mapCurrencyToString")
    default String map(Currency value){
        return value.getCode();
    }

    @Named("mapXchangeCurrencyToString")
    default String map(org.knowm.xchange.currency.Currency value){
        return value.getCurrencyCode();
    }

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "trades", ignore = true)
    void updateOrder(OrderDTO source, @MappingTarget tech.cassandre.trading.bot.domain.Order target);

    // =================================================================================================================
    // Domain to DTO.

    /**
     * Map Order to OrderDTO.
     *
     * @param source order
     * @return OrderDTO
     */
    @Mapping(source = "trades", target = "trades")
    @Mapping(target = "trade", ignore = true)
    OrderDTO mapToOrderDTO(tech.cassandre.trading.bot.domain.Order source);

}
