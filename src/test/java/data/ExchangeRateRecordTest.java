package data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateRecordTest {

	private static BigDecimal usd = new BigDecimal(1.1562);

	private ExchangeRateRecord record;

	@BeforeEach
	void setUp() {
		record = TestDataRetriever.genSampleTestRecord(usd, new BigDecimal(128.78), new BigDecimal(1.9558));
	}



	/**
	 * Testing ExchangeRateRecord.getCurrencyExchangeRate
	 * Confirming that the method will return a value for a valid currency id
	 */
	@Test
	public void requestPresentCurrency() {
		assertTrue(usd.equals(record.getCurrencyExchangeRate("USD")));
	}

	/**
	 * Testing ExchangeRateRecord.getCurrencyExchangeRate
	 * Confirming that the method will return a null value id the provided currency id is invalid.
	 */
	@Test
	public void requestMissingCurrency() {
		assertNull(record.getCurrencyExchangeRate("ABC"));
	}

	/**
	 * Testing ExchangeRateRecord.getCurrencyExchangeRate
	 * Confirming that the method will work for lower case or mixed case currency ids if they are valid.
	 */
	@Test
	public void requestLowercaseCurrencyId() {
		assertNotNull(record.getCurrencyExchangeRate("usd"));
		assertNotNull(record.getCurrencyExchangeRate("uSd"));
	}

	/**
	 * Testing ExchangeRateRecord.getCurrencyExchangeRate
	 * Confirming that the method will return null if the valid currency id refers to a N/A value in the data.
	 */
	@Test
	public void requestPresentNullCurrency() {
		assertNull(record.getCurrencyExchangeRate("CYP"));
	}

	/**
	 * Testing ExchangeRateRecord.getCurrencyExchangeRate
	 * Confirming that the method will return null if the currency id provided is null.
	 */
	@Test
	public void requestPresentNullCurrencyId() {
		assertNull(record.getCurrencyExchangeRate(null));
	}


}
