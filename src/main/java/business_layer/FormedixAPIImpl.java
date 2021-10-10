package business_layer;

import data.ExchangeRateData;
import data.ExchangeRateRecord;
import service_layer.ExchangeRatesDataRetriever;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Stream;


/**
 * {@link FormedixAPI} implementation
 */
public class FormedixAPIImpl implements FormedixAPI {


	private final ExchangeRateData exchangeRateData;

	public FormedixAPIImpl(final ExchangeRatesDataRetriever dataRetriever) {
		this.exchangeRateData = dataRetriever.retrieveData();
	}


	@Override
	public ExchangeRateRecord getExchangeRateData(final LocalDate date) {
		return exchangeRateData.get(date);
	}


	@Override
	public BigDecimal convertCurrencyAmount(final LocalDate date, final String sourceCurrency, final String targetCurrency, final BigDecimal amount) {
		if (date == null) {
			return null;
		}
		final ExchangeRateRecord exchangeRecord = exchangeRateData.get(date);
		if (exchangeRecord == null) {
			return null;
		}
		final BigDecimal sourceExchangeRate = exchangeRecord.getCurrencyExchangeRate(sourceCurrency);
		final BigDecimal targetExchangeRate = exchangeRecord.getCurrencyExchangeRate(targetCurrency);
		if (sourceExchangeRate == null || targetExchangeRate == null || amount == null || targetExchangeRate.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return amount.multiply(targetExchangeRate).divide(sourceExchangeRate, RoundingMode.HALF_UP);
	}


	@Override
	public BigDecimal getHighestExchangeRateDuringPeriod(final LocalDate startDate, final LocalDate endDate, final String currencyId) {
		if (startDate == null || endDate == null || currencyId == null) {
			return null;
		}
		return getReferenceRatesInRange(startDate, endDate, currencyId).max(Comparator.naturalOrder()).orElseGet(() -> null);
	}


	@Override
	public BigDecimal getAverageExchangeRateDuringPeriod(final LocalDate startDate, final LocalDate endDate, final String currencyId) {
		if (startDate == null || endDate == null || currencyId == null) {
			return null;
		}
		final BigDecimal[] totalWithCount = getReferenceRatesInRange(startDate, endDate, currencyId).
				map(rate -> new BigDecimal[]{rate, BigDecimal.ONE})
				.reduce((a, b) -> new BigDecimal[]{a[0].add(b[0]), a[1].add(BigDecimal.ONE)}).orElseGet(() -> null);
		if (totalWithCount == null) {
			return null;
		}
		final BigDecimal count = totalWithCount[1];
		if (count.compareTo(BigDecimal.ZERO) <= 0) {
			return new BigDecimal(0);
		}
		return totalWithCount[0].divide(count, RoundingMode.HALF_UP);
	}


	/**
	 * Returns a stream containing a all the Reference Rates for a given currency that lie between two dates.
	 *
	 * @param startDate the start date of the range. This date is included in the range.
	 * @param endDate the end date of the range. This date is included in the range.
	 * @param currencyId the id of the currency reference rates to be returned
	 * @return filtered stream of reference rate values that match the filter criteria.
	 */
	private Stream<BigDecimal> getReferenceRatesInRange(final LocalDate startDate, final LocalDate endDate, final String currencyId) {
		return exchangeRateData.entrySet().stream()
				.filter(dataEntry -> !dataEntry.getKey().isAfter(endDate) && !dataEntry.getKey().isBefore(startDate)
						&& dataEntry.getValue().getCurrencyExchangeRate(currencyId) != null)
				.map(dataEntry -> dataEntry.getValue().getCurrencyExchangeRate(currencyId));
	}

}
