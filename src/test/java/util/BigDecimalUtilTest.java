package util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Unit test that cover the utility methods provided by {@code BigDecimalUtil}
 */
public class BigDecimalUtilTest {



	/**
	 * Testing BigDecimalUtil.absolute
	 * Confirming that the method will convert negative values into positive ones.
	 */
	@Test
	public void absoluteNegativeTest() {
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(-1)).compareTo(new BigDecimal(1)) == 0);
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(-25)).compareTo(new BigDecimal(25)) == 0);
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(-0.056)).compareTo(new BigDecimal(0.056)) == 0);
	}

	/**
	 * Testing BigDecimalUtil.absolute
	 * Confirming that the method will not modify positive values.
	 */
	@Test
	public void absolutePositiveTest() {
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(1)).compareTo(new BigDecimal(1)) == 0);
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(25)).compareTo(new BigDecimal(25)) == 0);
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(0.056)).compareTo(new BigDecimal(0.056)) == 0);
	}

	/**
	 * Testing BigDecimalUtil.absolute
	 * Confirming that the method will not modify values of zero.
	 */
	@Test
	public void absoluteZeroTest() {
		assertTrue(BigDecimalUtil.absolute(new BigDecimal(0)).compareTo(new BigDecimal(0)) == 0);
	}

	/**
	 * Testing BigDecimalUtil.absolute
	 * Confirming that the method will return null if provided a null parameter.
	 */
	@Test
	public void absoluteNullTest() {
		assertNull(BigDecimalUtil.absolute(null));
	}



	/**
	 * Testing BigDecimalUtil.deltaWithinRange
	 * Confirming that the method returns true when the delta between the first two values is less than the delta provided
	 */
	@Test
	public void deltaLessThan() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), new BigDecimal(55), new BigDecimal(10)));
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(0.1), new BigDecimal(0.3), new BigDecimal(0.5)));
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(-50), new BigDecimal(-55), new BigDecimal(10)));
	}

	/**
	 * Testing BigDecimalUtil.deltaWithinRange
	 * Confirming that the method returns true when the delta between the first two values is equal to the delta provided
	 */
	@Test
	public void deltaEqualTo() {
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), new BigDecimal(55), new BigDecimal(5)));
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(0.1), new BigDecimal(0.3), new BigDecimal(0.2)));
		assertTrue(BigDecimalUtil.deltaWithinRange(new BigDecimal(-50), new BigDecimal(-55), new BigDecimal(5)));
	}

	/**
	 * Testing BigDecimalUtil.deltaWithinRange
	 * Confirming that the method returns false when the delta between the first two values is less than the delta provided
	 */
	@Test
	public void deltaMoreThan() {
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), new BigDecimal(55), new BigDecimal(1)));
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(0.1), new BigDecimal(0.3), new BigDecimal(0.1)));
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(-50), new BigDecimal(55), new BigDecimal(1)));
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(-50), new BigDecimal(-55), new BigDecimal(1)));
	}

	/**
	 * Testing BigDecimalUtil.deltaWithinRange
	 * Confirming that the method returns false when the delta provide is negative and thus not achievable.
	 */
	@Test
	public void deltaInvalidRange() {
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), new BigDecimal(55), new BigDecimal(-10)));
	}

	/**
	 * Testing BigDecimalUtil.deltaWithinRange
	 * Confirming that the method returns null if any or all of the parameters are null.
	 */
	@Test
	public void deltaNull() {
		assertFalse(BigDecimalUtil.deltaWithinRange(null, new BigDecimal(55), new BigDecimal(-10)));
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), null, new BigDecimal(-10)));
		assertFalse(BigDecimalUtil.deltaWithinRange(new BigDecimal(50), new BigDecimal(55), null));
		assertFalse(BigDecimalUtil.deltaWithinRange(null, null, null));
	}

}
