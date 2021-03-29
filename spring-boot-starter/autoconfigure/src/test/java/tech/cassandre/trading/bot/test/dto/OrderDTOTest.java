package tech.cassandre.trading.bot.test.dto;

import io.qase.api.annotation.CaseId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.cassandre.trading.bot.dto.trade.OrderDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyAmountDTO;
import tech.cassandre.trading.bot.dto.util.CurrencyPair;
import tech.cassandre.trading.bot.dto.util.CurrencyPairDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static tech.cassandre.trading.bot.dto.trade.OrderStatusDTO.NEW;
import static tech.cassandre.trading.bot.dto.trade.OrderStatusDTO.PENDING_NEW;
import static tech.cassandre.trading.bot.dto.trade.OrderTypeDTO.ASK;
import static tech.cassandre.trading.bot.dto.trade.OrderTypeDTO.BID;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.BTC;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.ETH;
import static tech.cassandre.trading.bot.dto.util.CurrencyDTO.USDT;

@DisplayName("DTO - OrderDTO")
public class OrderDTOTest {

	@Test
	@SuppressWarnings({ "checkstyle:MagicNumber", "checkstyle:MethodLength" })
	@CaseId(46)
	@DisplayName("Check equals()")
	public void checkEqualToForOrder() {
		// Currency pairs.
		final CurrencyPair cp1 = CurrencyPair.forValues(ETH, BTC);
		final CurrencyPair cp2 = CurrencyPair.forValues(ETH, USDT);

		// Order 1.
		OrderDTO order01 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();

		// Order 2 - same as order 1.
		OrderDTO order02 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertEquals(order01, order02);
		assertEquals(order02, order01);

		// Order 3 - order id changed.
		OrderDTO order03 = OrderDTO.builder()
				.orderId("000002")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order03);
		assertNotEquals(order03, order01);

		// Order 4 - type changed.
		OrderDTO order04 = OrderDTO.builder()
				.orderId("000001")
				.type(BID)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order04);
		assertNotEquals(order04, order01);

		// Order 5 - currency pair changed.
		OrderDTO order05 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp2)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order05);
		assertNotEquals(order03, order05);
		assertNotEquals(order05, order03);

		// Order 6 - amount changed.
		OrderDTO order06 = OrderDTO.builder()
				.orderId("000001")
				.type(BID)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("9", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order06);
		assertNotEquals(order06, order01);

		// Order 7 - average price changed.
		OrderDTO order07 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("9", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order07);
		assertNotEquals(order07, order01);

		// Order 8 - limit price changed.
		OrderDTO order08 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("9", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order08);
		assertNotEquals(order08, order01);

		// Order 9 - leverage changed.
		OrderDTO order09 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage2")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order09);
		assertNotEquals(order09, order01);

		// Order 10 - status changed.
		OrderDTO order10 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(PENDING_NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order10);
		assertNotEquals(order10, order01);

		// Order 11 - cumulative amount changed.
		OrderDTO order11 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("9", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order11);
		assertNotEquals(order11, order01);

		// Order 12 - user reference changed.
		OrderDTO order12 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage1")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_2")
				.timestamp(ZonedDateTime.of(2018, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order12);
		assertNotEquals(order12, order01);

		// Order 13 - leverage changed.
		OrderDTO order13 = OrderDTO.builder()
				.orderId("000001")
				.type(ASK)
				.currencyPair(cp1)
				.amount(new CurrencyAmountDTO("1", cp1.getBaseCurrency()))
				.averagePrice(new CurrencyAmountDTO("3", cp1.getQuoteCurrency()))
				.limitPrice(new CurrencyAmountDTO("5", cp1.getQuoteCurrency()))
				.leverage("leverage2")
				.status(NEW)
				.cumulativeAmount(new CurrencyAmountDTO("2", cp1.getBaseCurrency()))
				.userReference("MY_REF_1")
				.timestamp(ZonedDateTime.of(2018, 1, 3, 0, 0, 0, 0, ZoneId.of("UTC")))
				.build();
		assertNotEquals(order01, order13);
		assertNotEquals(order13, order01);

		// Tests for null objects.
		OrderDTO order15 = OrderDTO.builder().build();
		OrderDTO order16 = OrderDTO.builder().build();
		assertEquals(order15, order16);
		assertEquals(order16, order15);
	}

}
