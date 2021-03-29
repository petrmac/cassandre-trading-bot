package tech.cassandre.trading.bot.util.mapper;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.cassandre.trading.bot.dto.user.AccountDTO;
import tech.cassandre.trading.bot.dto.user.BalanceDTO;
import tech.cassandre.trading.bot.dto.user.UserDTO;

import java.util.Map;

/**
 * Account mapper.
 */
@Mapper(uses = CurrencyMapper.class)
public interface AccountMapper {

    // =================================================================================================================
    // XChange to DTO.

    @Mapping(source = "username", target = "id")
    @Mapping(source = "wallets", target = "accounts")
    UserDTO mapToUserDTO(AccountInfo source);

    @Mapping(source = "id", target = "accountId")
    @Mapping(target = "feature", ignore = true)
    AccountDTO mapToWalletDTO(Wallet source);

    Map<Currency, BalanceDTO> mapToCurrencyDTOAndBalanceDTO(Map<Currency, Balance> source);

    BalanceDTO mapToBalanceDTO(Balance source);

}
