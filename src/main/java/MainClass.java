import business_layer.FormedixAPI;
import business_layer.FormedixAPIImpl;
import data.DateFormattingUtil;
import service_layer.FileDataRetriever;

import java.math.BigDecimal;


/**
 * Simple main class to demonstrate the usage of the FormedixAPI with some basic examples.
 */
public class MainClass {



	public static void main(final String args[]) {

		final FormedixAPI formedixAPIImpl = new FormedixAPIImpl(new FileDataRetriever());


		formedixAPIImpl.getExchangeRateData(DateFormattingUtil.parseEurofx("2021-10-04")).getCurrencyExchangeRate("ABC");


		BigDecimal convertTest1 = formedixAPIImpl.convertCurrencyAmount(
				DateFormattingUtil.parseEurofx("2021-10-04"), "USD", "JPY", new BigDecimal(50));
		System.out.println("Test: [CONVERT 1] = " + convertTest1);

		final BigDecimal convertTest2 = formedixAPIImpl.convertCurrencyAmount(
				DateFormattingUtil.parseEurofx("2021-10-04"), "USD", "USD", new BigDecimal(50));
		System.out.println("Test: [CONVERT 2] = " + convertTest2);


		final BigDecimal largeTest = formedixAPIImpl.getHighestExchangeRateDuringPeriod(
				DateFormattingUtil.parseEurofx("2021-10-04"), DateFormattingUtil.parseEurofx("2021-10-07"), "USD");
		System.out.println("Test: [LARGE] = " + largeTest);

		final BigDecimal averageTest = formedixAPIImpl.getAverageExchangeRateDuringPeriod(
				DateFormattingUtil.parseEurofx("2021-10-05"), DateFormattingUtil.parseEurofx("2021-10-07"), "USD");
		System.out.println("Test: [AVERAGE] = " + averageTest);

	}



}
