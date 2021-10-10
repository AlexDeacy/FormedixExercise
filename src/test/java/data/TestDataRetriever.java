package data;

import service_layer.ExchangeRatesDataRetriever;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;


/**
 * Implementation fo the {@link ExchangeRatesDataRetriever}
 * that can be loaded with some simple test data for use in unit testing.
 */
public class TestDataRetriever implements ExchangeRatesDataRetriever {



	private final ExchangeRateData exchangeRateData;



	public TestDataRetriever() {
		exchangeRateData = new ExchangeRateData();
	}

	public void putData(final LocalDate date, final ExchangeRateRecord exchangeRateRecord) {
		exchangeRateData.put(date, exchangeRateRecord);
	}

	public void clearData() {
		exchangeRateData.clear();
	}

	@Override
	public ExchangeRateData retrieveData() {
		return exchangeRateData;
	}


	public static TestDataRetriever generateSampleTestDataRetriever() {
		final TestDataRetriever testDataRetriever = new TestDataRetriever();
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-09-01"), genSampleTestRecord(new BigDecimal(1.123), new BigDecimal(140.5), new BigDecimal(2.0)));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-01"), genSampleTestRecord(new BigDecimal(1.17), new BigDecimal(120), new BigDecimal(2.0)));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-03"), genSampleTestRecord(new BigDecimal(1.16), new BigDecimal(128.97), new BigDecimal(2.0)));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-04"), genSampleTestRecord(new BigDecimal(1.1636), new BigDecimal(129.21), null));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-05"), genSampleTestRecord(new BigDecimal(1.1602), new BigDecimal(128.99), new BigDecimal(2.0)));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-06"), genSampleTestRecord(new BigDecimal(1.1542), new BigDecimal(128.52), new BigDecimal(2.0)));
		testDataRetriever.putData(DateFormattingUtil.parseEurofx("2021-10-07"), genSampleTestRecord(new BigDecimal(1.1562), new BigDecimal(128.78), new BigDecimal(0)));
		return testDataRetriever;
	}

	public static ExchangeRateRecord genSampleTestRecord(final BigDecimal USD, final BigDecimal JPY, final BigDecimal BGN) {
		final HashMap<String, BigDecimal> exchangeMap = new HashMap<>();
		exchangeMap.put("USD", USD);
		exchangeMap.put("JPY", JPY);
		exchangeMap.put("BGN", BGN);
		exchangeMap.put("CYP", null);
		return new ExchangeRateRecord(exchangeMap);
	}


}
