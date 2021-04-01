package tech.cassandre.trading.bot.service.xchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.cassandre.trading.bot.dto.user.UserDTO;
import tech.cassandre.trading.bot.service.UserService;
import tech.cassandre.trading.bot.util.base.service.BaseService;
import tech.cassandre.trading.bot.util.mapper.MapperService;

import java.io.IOException;
import java.util.Optional;

/**
 * User service - XChange implementation.
 */
public class UserServiceXChangeImplementation extends BaseService implements UserService {

    /** Logger. */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** XChange service. */
    private final org.knowm.xchange.service.account.AccountService xChangeAccountService;

    /**
     * Constructor.
     *
     * @param rate                     rate in ms
     * @param newXChangeAccountService xchange account service
     */
    public UserServiceXChangeImplementation(final long rate,
                                            final MapperService mapperService,
                                            final org.knowm.xchange.service.account.AccountService newXChangeAccountService) {
        super(mapperService, rate);
        this.xChangeAccountService = newXChangeAccountService;
    }

    @Override
    public final Optional<UserDTO> getUser() {
        try {
            // Consume a token from the token bucket.
            // If a token is not available this method will block until the refill adds one to the bucket.
            getBucket().asScheduler().consume(1);

            logger.debug("UserService - Retrieving account information");
            final UserDTO user = getMapperService().getAccountMapper().mapToUserDTO(xChangeAccountService.getAccountInfo());
            logger.debug("UserService - Account information retrieved " + user);
            return Optional.ofNullable(user);
        } catch (IOException e) {
            logger.error("UserService - Error retrieving account information : {}", e.getMessage());
            return Optional.empty();
        } catch (InterruptedException e) {
            logger.error("UserService - InterruptedException : {}", e.getMessage());
            return Optional.empty();
        }
    }

}
