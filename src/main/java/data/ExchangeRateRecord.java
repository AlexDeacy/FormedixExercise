package data;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Data object that holds exchange rate data.
 */
public class ExchangeRateRecord {


	private final HashMap<String, BigDecimal> exchangeRateMap;


	public ExchangeRateRecord(final HashMap<String, BigDecimal> referenceRateMap) {
		this.exchangeRateMap = referenceRateMap;
	}

	/**
	 * Returns the exchange rate for the requested currency.
	 * @param currencyId id of the desired currency exchange rate.
	 * @return the exchange rate for the specified currencyId, or
	 *         {@code null} if no value is found.
	 */
	public BigDecimal getCurrencyExchangeRate(final String currencyId) {
		if (currencyId == null || !exchangeRateMap.containsKey(currencyId.toUpperCase())) {
			return null;
		}
		return exchangeRateMap.get(currencyId.toUpperCase());
	}

}
