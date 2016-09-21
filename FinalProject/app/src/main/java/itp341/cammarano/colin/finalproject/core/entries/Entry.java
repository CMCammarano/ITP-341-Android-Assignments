package itp341.cammarano.colin.finalproject.core.entries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Colin Cammarano on 4/24/16.
 */
public class Entry {

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	public static final String KEY_DAY = "day";
	public static final String KEY_MONTH = "month";
	public static final String KEY_YEAR = "year";
	public static final String KEY_MINUTE = "minute";
	public static final String KEY_HOUR = "hour";

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private String mName;
	private Map<String, Long> mStart;
	private int mCategory;
	private int mAlert;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Entry (String name, Map<String, Long> start, int category, int alert) {
		mName = name;
		mStart = start;
		mCategory = category;
		mAlert = alert;
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public String getName () { return mName; }
	public void setName (String name) { mName = name; }

	public Map<String, Long> getStart () { return mStart; }

	public int getCategory () { return mCategory; }
	public void setCategory (int category) { mCategory = category; }

	public int getAlert () { return mAlert; }
	public void setAlert (int alert) { mAlert = alert; }

	/******************************************************/
	// PUBLIC STATIC FUNCTIONS
	/******************************************************/
	public static String getDate (Map<String, Long> date) {
		Calendar calendar = Calendar.getInstance ();
		calendar.set (date.get (KEY_YEAR).intValue (), date.get (KEY_MONTH).intValue () - 1, date.get (KEY_DAY).intValue (), date.get (KEY_HOUR).intValue (), date.get (KEY_MINUTE).intValue ());

		DateFormat format = DateFormat.getDateInstance ();
		return format.format (calendar.getTime ());
	}

	public static String getTime (Map<String, Long> time) {
		Calendar calendar = Calendar.getInstance ();
		calendar.set (time.get (KEY_YEAR).intValue (), time.get (KEY_MONTH).intValue () - 1, time.get (KEY_DAY).intValue (), time.get (KEY_HOUR).intValue (), time.get (KEY_MINUTE).intValue ());

		// Use date format to get the time
		SimpleDateFormat format = new SimpleDateFormat ("h:mm a");
		return format.format (calendar.getTime ());
	}
}
