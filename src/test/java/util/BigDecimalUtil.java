package util;

import java.math.BigDecimal;

public class BigDecimalUtil {


	public static final BigDecimal CURRENCY_MAX_DELTA = new BigDecimal(0.00005);//Maintain accuracy to 4 decimal places.


	/**
	 *
	 * Compares two values and determines if the difference between them is less than the provided delta.
	 *
	 * @param firstValue  the first value to be compared
	 * @param secondValue the second value to be compared
	 * @param maxDelta the maximum delta allowed between the two values. Must be positive otherwise false will be returned.
	 * @return null if any parameters provided are null,
	 * 			true if the difference between {@code firstValue} and {@code secondValue} is less than or equal to {@code maxDelta}.
	 * 			false if the difference is more than {@code maxDelta}.
	 */
	public static boolean deltaWithinRange(BigDecimal firstValue, BigDecimal secondValue, BigDecimal maxDelta) {
		if (firstValue == null || secondValue == null || maxDelta == null) {
			return false;
		}
		return absolute(firstValue.subtract(secondValue)).compareTo(maxDelta) <= 0;
	}


	/**
	 * Returns the absolute value of an {@code BigDecimal} value.
	 * If the argument is not negative, the argument is returned.
	 * If the argument is negative, the negation of the argument is returned.
	 *
	 * @param   bigDecimal   the argument whose absolute value is to be determined
	 * @return  the absolute value of the argument. Null if {@code bigDecimal} is null.
	 */
	public static BigDecimal absolute(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return null;
		}
		if (bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
			return bigDecimal.multiply(new BigDecimal(-1));
		}
		return bigDecimal;
	}


}
