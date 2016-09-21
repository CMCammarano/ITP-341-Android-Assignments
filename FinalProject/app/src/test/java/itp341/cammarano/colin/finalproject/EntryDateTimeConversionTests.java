package itp341.cammarano.colin.finalproject;

import com.firebase.client.snapshot.EmptyNode;

import org.junit.Test;

import java.util.HashMap;

import itp341.cammarano.colin.finalproject.core.entries.Entry;

import static org.junit.Assert.*;

/**
 * Test the Entry class's date and time conversion methods to ensure stability with incorrect and correct inputs.
 * Since inputs are based on those retrieved from the Date and Calendar classes, these incorrect input cases should not arise unless the code is changed.
 * The only five values used are the values tested -- seconds, millis, et al. are not used.
 */
public class EntryDateTimeConversionTests {

	@Test
	public void testTimeConversion () throws Exception {
		// Test standard time case
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getTime (start), "12:00 AM");
	}

	@Test
	public void testTimeConversionMinRollback () throws Exception {
		// Test incorrect minutes -- rollback
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (-10));

		assertEquals (Entry.getTime (start), "11:50 PM");
	}

	@Test
	public void testTimeConversionMinRollover () throws Exception {
		// Test incorrect minutes -- rollover
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (61));

		assertEquals (Entry.getTime (start), "1:01 AM");
	}

	@Test
	public void testTimeConversionHourRollback () throws Exception {
		// Test incorrect hours -- rollback
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (-1));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getTime (start), "11:00 PM");
	}

	@Test
	public void testTimeConversionHourRollover () throws Exception {
		// Test incorrect hours -- rollover
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (25));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getTime (start), "1:00 AM");
	}

	@Test
	public void testDateConversion () throws Exception {
		// Test standard date case
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Jan 1, 2016");
	}

	@Test
	public void testDateConversionMonthRollover () throws Exception {
		// Test invalid month -- rollover to next year
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (13));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Jan 1, 2017");
	}

	@Test
	public void testDateConversionMonthRollback () throws Exception {
		// Test invalid month -- rollback to previous year
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (0));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Dec 1, 2015");
	}

	@Test
	public void testDateConversionInvalidYear () throws Exception {
		// Test invalid year -- negatives should be corrected to 1 + abs(year) [follows standard in Calendar class -- this whole system can be modified for actual release once I figure out a better way to account for the fact that Android and iOS handle the month offset differently. :( ]
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (-1));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Jan 1, 0002");
	}

	@Test
	public void testDateConversionDayRollback () throws Exception {
		// Test invalid day -- rollover to previous month
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (0));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Dec 31, 2015");
	}

	@Test
	public void testDateConversionDayRollover () throws Exception {
		// Test invalid day -- rollover to next month
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (32));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Feb 1, 2016");
	}

	@Test
	public void testDateConversionHourRollback () throws Exception {
		// Test incorrect hours -- rollback
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (-1));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Dec 31, 2015");
	}

	@Test
	public void testDateConversionHourRollover () throws Exception {
		// Test incorrect hours -- rollover
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (25));
		start.put (Entry.KEY_MINUTE, Long.valueOf (0));

		assertEquals (Entry.getDate (start), "Jan 2, 2016");
	}

	@Test
	public void testDateConversionMinRollback () throws Exception {
		// Test incorrect minutes -- rollback
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (0));
		start.put (Entry.KEY_MINUTE, Long.valueOf (-1));

		assertEquals (Entry.getDate (start), "Dec 31, 2015");
	}

	@Test
	public void testDateConversionMinRollover () throws Exception {
		// Test incorrect minutes -- rollover
		HashMap<String, Long> start = new HashMap<> ();
		start.put (Entry.KEY_YEAR, Long.valueOf (2016));
		start.put (Entry.KEY_MONTH, Long.valueOf (1));
		start.put (Entry.KEY_DAY, Long.valueOf (1));
		start.put (Entry.KEY_HOUR, Long.valueOf (23));
		start.put (Entry.KEY_MINUTE, Long.valueOf (61));

		assertEquals (Entry.getDate (start), "Jan 2, 2016");
	}
}