package data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Utility class for parsing string dates into LocalDates.
 *
 */
public class DateFormattingUtil {

	private static final String datePattern = "yyyy-MM-dd";
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);


	/**
	 * Parses string dates using the Eurofx date format {@link #datePattern}
	 * into {@link LocalDate] objects
	 * @param dateString to be parsed
	 * @return the resulting LocalDate object.
	 */
	public static LocalDate parseEurofx(final String dateString) {
		if (dateString == null) {
			return null;
		}
		return LocalDate.parse(dateString, formatter);
	}


}
