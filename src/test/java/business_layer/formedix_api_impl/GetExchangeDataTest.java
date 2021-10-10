package business_layer.formedix_api_impl;

import business_layer.FormedixAPIImpl;
import data.DateFormattingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.TestDataRetriever;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GetExchangeDataTest {



	private FormedixAPIImpl formedixExchangeAPI;


	@BeforeEach
	public void setup() {
		formedixExchangeAPI = new FormedixAPIImpl(TestDataRetriever.generateSampleTestDataRetriever());
	}

	/**
	 * Testing FormedixAPIImpl.getExchangeRateData
	 * Confirming that the method will return the record for a valid date.
	 */
	@Test
	public void requestDataForDateInRange() {
		assertNotNull(formedixExchangeAPI.getExchangeRateData(DateFormattingUtil.parseEurofx("2021-10-05")));
	}

	/**
	 * Testing FormedixAPIImpl.getExchangeRateData
	 * Confirming that the method will return null for a date that is out of the range in the data.
	 */
	@Test
	public void requestDataForDateOutOfRange() {
		assertNull(formedixExchangeAPI.getExchangeRateData(DateFormattingUtil.parseEurofx("2025-09-09")));
	}

	/**
	 * Testing FormedixAPIImpl.getExchangeRateData
	 * Confirming that the method will return null if null is provided as the date parameter.
	 */
	@Test
	public void requestDataForNullDate() {
		assertNull(formedixExchangeAPI.getExchangeRateData(null));
	}


}
