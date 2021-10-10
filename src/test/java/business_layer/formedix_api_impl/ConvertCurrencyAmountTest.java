package business_layer.formedix_api_impl;

import business_layer.FormedixAPIImpl;
import data.DateFormattingUtil;
import data.ExchangeRateRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.TestDataRetriever;
import util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static util.BigDecimalUtil.CURRENCY_MAX_DELTA;

public class ConvertCurrencyAmountTest {


	private FormedixAPIImpl formedixExchangeAPI;
	private TestDataRetriever testDataRetriever;

	@BeforeEach
	public void setup() {
		testDataRetriever = TestDataRetriever.generateSampleTestDataRetriever();
		formedixExchangeAPI = new FormedixAPIImpl(TestDataRetriever.generateSampleTestDataRetriever());
	}


	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will correctly convert an amount between the provided currencies for the exchange rates on a given date.
	 */
	@Test
	public void simpleExchange() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(5567.4926), formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "USD", "JPY", new BigDecimal(50)), CURRENCY_MAX_DELTA));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will return null if the source currency exchange rate is zero. as this would result in a divide by zero.
	 */
	@Test
	public void divideByZeroExchange() {
		final HashMap<String, BigDecimal> exchangeMap = new HashMap();
		exchangeMap.put("BGN", BigDecimal.ZERO);
		final ExchangeRateRecord exchangeRaterecord = new ExchangeRateRecord(exchangeMap);
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-12-01"), exchangeRaterecord);
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-12-01"), "BGN", "BGN", new BigDecimal(50)));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will still work for currency ids that are of lower or mixed case.
	 */
	@Test
	public void lowercaseExchange() {
		assertNotNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "uSd", "jpy", new BigDecimal(50)));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will return the same value for a currency that exchange into itself.
	 */
	@Test
	public void usdToUsdExchange() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "USD", "USD", new BigDecimal(50)), CURRENCY_MAX_DELTA));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will return null if the currency ids are invalid.
	 */
	@Test
	public void unknownCurrencyExchange() {
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "abc", "USD", new BigDecimal(50)));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will return null if the date requested is out of the available range of data.
	 */
	@Test
	public void unknownDateExchange() {
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("3456-10-06"), "USD", "JPY", new BigDecimal(50)));
	}

	/**
	 * Testing FormedixAPIImpl.convertCurrencyAmount
	 * Confirming that the method will return null if any or all parameters are null.
	 */
	@Test
	public void nullParamsExchange() {
		assertNull(formedixExchangeAPI.convertCurrencyAmount(null, "USD", "USD", new BigDecimal(50)));
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), null, "USD", new BigDecimal(50)));
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "USD", null, new BigDecimal(50)));
		assertNull(formedixExchangeAPI.convertCurrencyAmount(DateFormattingUtil.parseEurofx("2021-10-06"), "USD", "USD", null));
		assertNull(formedixExchangeAPI.convertCurrencyAmount(null, null, null, null));
	}



}
