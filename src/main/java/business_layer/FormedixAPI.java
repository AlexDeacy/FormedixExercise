package business_layer;

import data.ExchangeRateRecord;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FormedixAPI {


	/**
	 * Obtain the exchange rate record for a given date.
	 * @param date the date associated with the desired {@code ExchangeRateRecord}
	 * @return the {@code ExchangeRateRecord} for the given date,
	 * 	or null if the date provided is null or doesn't match any records in the data.
	 */
	ExchangeRateRecord getExchangeRateData(LocalDate date);


	/**
	 * Converts the provided amount from one currency to the other.
	 *
	 * @param date The date of the exchange reference rate data to use when converting.
	 * @param sourceCurrencyId The source currency id that the amount is currently in.
	 * @param targetCurrencyId The target currency id to exchange to.
	 * @param amount the value to be converted between currencies.
	 * @return value of the converted amount,
	 * or null if any parameters or the date or currency ids dont match any values in the data.
	 */
	BigDecimal convertCurrencyAmount(LocalDate date, String sourceCurrencyId, String targetCurrencyId, BigDecimal amount);


	/**
	 * Finds the highest currency reference rate between the two dates provided.
	 *
	 * @param startDate the start date of the range. This date is included in the range.
	 * @param endDate the end date of the range. This date is included in the range. Must be a later date than {@code startDate}
	 * @param currencyId the id of the currency to be searched.
	 * @return the highest exchange rate,
	 * or null zero if non are found or if any parameters are null or the dates or currency ids dont match the data.
	 */
	BigDecimal getHighestExchangeRateDuringPeriod(LocalDate startDate, LocalDate endDate, String currencyId);


	/**
	 * Calculates the average currency reference rate between the two dates provided.
	 *
	 * @param startDate the start date of the range. This date is included in the range.
	 * @param endDate the end date of the range. This date is included in the range.
	 * @param currencyId the id of the currency to be averaged.
	 * @return the average of the values in the range,
	 * or null zero if non are found or if any parameters are null or the dates or currency ids dont match the data.
	 */
	BigDecimal getAverageExchangeRateDuringPeriod(LocalDate startDate, LocalDate endDate, String currencyId);



}
