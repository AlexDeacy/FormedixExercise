package service_layer;

import data.DateFormattingUtil;
import data.ExchangeRateData;
import data.ExchangeRateRecord;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the {@link ExchangeRatesDataRetriever} class
 * that reads from a local CSV file to populate a {@link ExchangeRateData} object.
 */
public class FileDataRetriever implements ExchangeRatesDataRetriever {


	private static final String DATA_FILE_PATH = "data\\eurofxref-hist.csv";


	@Override
	public ExchangeRateData retrieveData() {
		return parseEurofxrefData(readCSVData());
	}


	/**
	 * Read the data from the csv file at {@link #DATA_FILE_PATH}
	 * @return return a list of rows which are each represented with a String[]
	 */
	private static List<String[]> readCSVData() {
		final List<String[]> rawData = new ArrayList<>();
		final File csvDataFile = new File(DATA_FILE_PATH);
		try {
			final BufferedReader csvReader = new BufferedReader(new FileReader(csvDataFile));
			String row;
			while ((row = csvReader.readLine()) != null) {
				final String[] dataRow = row.split(",");
				rawData.add(dataRow);
			}
		} catch (final FileNotFoundException e) {
			System.out.println("File not found at : " + DATA_FILE_PATH);
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return rawData;
	}


	/**
	 * Parse the contents of the csv file at {@link #DATA_FILE_PATH}
	 * The format should include the list of currency Ids on the first row with the word 'Date' as the first element.
	 * Then each row of data after that should have a date of the format 'yyyy-mm-dd',
	 * followed by the values for each of the exchange rates for each currency for that day that are each in the same order as currency ids.
	 * @param rawData a list of string[] that each represent a row in the CSV file.
	 * @return {@link ExchangeRateData} object that contains the parsed data
	 */
	private static ExchangeRateData parseEurofxrefData(final List<String[]> rawData) {
		if (rawData == null) {
			return null;
		}
		final ExchangeRateData data = new ExchangeRateData();
		final List<String> currencyIdList = Arrays.stream(rawData.get(0)).skip(1).collect(Collectors.toList());

		boolean firstLineSkipped = false;
		for (String[] dataRow: rawData) {
			if (!firstLineSkipped || dataRow == null) {
				firstLineSkipped = true;
				continue;
			}

			final LocalDate date = DateFormattingUtil.parseEurofx(dataRow[0]);
			final HashMap<String, BigDecimal> exchangeRateMap = new HashMap<>();
			for (int i= 1; i<dataRow.length; i++) {
				BigDecimal exchangeRateValue;
				if (dataRow[i].equals("N/A")) {
					exchangeRateValue = null;
				} else {
					exchangeRateValue = new BigDecimal(dataRow[i]);
				}
				exchangeRateMap.put(currencyIdList.get(i-1), exchangeRateValue);
			}

			final ExchangeRateRecord exchangeRateRecord = new ExchangeRateRecord(exchangeRateMap);
			data.put(date, exchangeRateRecord);
		}
		return data;
	}



}
