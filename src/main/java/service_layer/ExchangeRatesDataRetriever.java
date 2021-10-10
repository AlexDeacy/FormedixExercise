package service_layer;

import data.ExchangeRateData;

/**
 * Factory interface that is responsible for populating an providing a {@link ExchangeRateData} object
 */
public interface ExchangeRatesDataRetriever {


	/**
	 * Returns a {@code ExchangeRateData} that has been populated with records.
	 * @return object containing exchange rate data.
	 */
	ExchangeRateData retrieveData();

}
