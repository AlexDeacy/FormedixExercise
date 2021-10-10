package business_layer.formedix_api_impl;

import business_layer.FormedixAPIImpl;
import data.DateFormattingUtil;
import data.ExchangeRateRecord;
import data.TestDataRetriever;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static util.BigDecimalUtil.CURRENCY_MAX_DELTA;

public class GetAverageExchangeRateTest {


	private FormedixAPIImpl formedixExchangeAPI;
	private TestDataRetriever testDataRetriever;


	@BeforeEach
	public void setup() {
		testDataRetriever = TestDataRetriever.generateSampleTestDataRetriever();
		formedixExchangeAPI = new FormedixAPIImpl(testDataRetriever);
	}


	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return the highest when only a single date is in the range. Highest being the exact value as there is only one.
	 */
	@Test
	public void singleDate() {
		assertTrue(new BigDecimal(1.1542).compareTo(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-06"), DateFormattingUtil.parseEurofx("2021-10-06"), "USD")) == 0);
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return the highest when the date range is over multiple record.
	 */
	@Test
	public void averageSimple() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(1.1588399999999999145927631616359576582908630371093750), formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-03"), DateFormattingUtil.parseEurofx("2021-10-07"), "USD"), CURRENCY_MAX_DELTA));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will work with currency ids of lower or mixed case.
	 */
	@Test
	public void lowerCase() {
		assertNotNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-03"), DateFormattingUtil.parseEurofx("2021-10-07"), "usd"));
		assertNotNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-03"), DateFormattingUtil.parseEurofx("2021-10-07"), "Usd"));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return null if the start and end dates are the wrong way round.
	 */
	@Test
	public void wrongDateOrder() {
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-07"), DateFormattingUtil.parseEurofx("2021-10-03"), "USD"));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return null if any or all of the parameters provided are null.
	 */
	@Test
	public void nullDateAndCurrencyId() {
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(null, DateFormattingUtil.parseEurofx("2021-10-06"), "USD"));
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-06"), null, "USD"));
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-06"), DateFormattingUtil.parseEurofx("2021-10-06"), null));
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(null, null, null));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return if the range of dates requested are outside of the range of the data.
	 */
	@Test
	public void dateOutOfDataRange() {
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("3333-10-06"), DateFormattingUtil.parseEurofx("3333-10-06"), "USD"));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return the highest even if the range of date provided exceeds the range of dates in the data.
	 */
	@Test
	public void dateRangeLargerThanData() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(1.1553142857142856428761693418242170342377253941127232), formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2020-09-01"), DateFormattingUtil.parseEurofx("2022-10-07"), "USD"), CURRENCY_MAX_DELTA));
	}


	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return the highest when some values are null, and return null if all value are null, for a given currency.
	 */
	@Test
	public void dateRangeWithNullData() {
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2020-01-01"), TestDataRetriever.genSampleTestRecord(new BigDecimal(1.1636), new BigDecimal(129.21), null));
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2020-01-01"), DateFormattingUtil.parseEurofx("2020-01-01"), "BGN"));

		final HashMap<String, BigDecimal> exchangeMap = new HashMap();
		exchangeMap.put("USD", new BigDecimal(1.1636));
		exchangeMap.put("JPY", new BigDecimal(129.21));
		exchangeMap.put("BGN", new BigDecimal(2.5));
		exchangeMap.put("CYP", null);
		exchangeMap.put("CZK", new BigDecimal(25.415));
		final ExchangeRateRecord exchangeRaterecord = new ExchangeRateRecord(exchangeMap);
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-08"), exchangeRaterecord);
		System.out.println(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2020-01-01"), DateFormattingUtil.parseEurofx("2021-10-08"), "BGN"));
		System.out.println(new BigDecimal(1.8).compareTo(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2020-01-01"), DateFormattingUtil.parseEurofx("2021-10-08"), "BGN")) == 0);
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(1.8), formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2020-01-01"), DateFormattingUtil.parseEurofx("2021-10-08"), "BGN"), CURRENCY_MAX_DELTA));
	}


	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return null when providing null or invalid currency ids
	 */
	@Test
	public void invalidCurrencyId() {
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-04"), DateFormattingUtil.parseEurofx("2021-10-04"), "ABC"));
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-04"), DateFormattingUtil.parseEurofx("2021-10-04"), null));
	}

	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return null if no data has been loaded.
	 */
	@Test
	public void noData() {
		testDataRetriever.clearData();
		assertNull(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-10-06"), DateFormattingUtil.parseEurofx("2021-10-06"), "USD"));
	}


	/**
	 * Testing FormedixAPIImpl.getAverageExchangeRateDuringPeriod
	 * Confirming that the method will return the highest when some records don't contain any data for a given currency.
	 * Confirming that the method will return the highest even if the data has gaps in the range of dates stored in the data.
	 * Here only one date ExchangeRateRecord has data for CZK but we can still check a range of dates.
	 */
	@Test
	public void partialData() {
		final HashMap<String, BigDecimal> exchangeMap = new HashMap();
		exchangeMap.put("USD", new BigDecimal(1.1636));
		exchangeMap.put("JPY", new BigDecimal(129.21));
		exchangeMap.put("BGN", new BigDecimal(1.9558));
		exchangeMap.put("CYP", null);
		exchangeMap.put("CZK", new BigDecimal(25.415));
		final ExchangeRateRecord exchangeRaterecord = new ExchangeRateRecord(exchangeMap);
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-09-02"), exchangeRaterecord);
		assertTrue(new BigDecimal(25.415).compareTo(formedixExchangeAPI.getAverageExchangeRateDuringPeriod(DateFormattingUtil.parseEurofx("2021-09-01"), DateFormattingUtil.parseEurofx("2021-10-06"), "CZK")) == 0);
	}

}
