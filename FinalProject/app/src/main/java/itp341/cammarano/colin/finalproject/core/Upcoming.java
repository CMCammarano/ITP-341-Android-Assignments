package itp341.cammarano.colin.finalproject.core;

import java.util.Map;

import itp341.cammarano.colin.finalproject.core.entries.Block;
import itp341.cammarano.colin.finalproject.core.entries.Entry;
import itp341.cammarano.colin.finalproject.core.entries.Event;

/**
 * Created by Colin Cammarano on 4/24/16.
 */
public class Upcoming {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private int mAlert;
	private int mCategory;
	private int mType;
	private String mName;
	Map<String, Long> mDate;
	Entry mEntry;

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	public static final int BLOCK_TYPE = 0;
	public static final int EVENT_TYPE = 1;
	public static final int NOTIFICATION_TYPE = 2;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Upcoming (Entry entry) {
		mAlert = entry.getAlert ();
		mCategory = entry.getCategory ();
		mName = entry.getName ();
		mDate = entry.getStart ();

		// Determine type of upcoming event
		if (entry instanceof Block) {
			mType = BLOCK_TYPE;
		}

		else if (entry instanceof Event) {
			mType = EVENT_TYPE;
		}

		else {
			mType = NOTIFICATION_TYPE;
		}

		// Set the entry
		mEntry = entry;
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public int getAlert () { return mAlert; }
	public int getCategory () { return mCategory; }
	public int getType () { return mType; }
	public String getName () { return mName; }
	public String getDate () { return Entry.getDate (mDate); }
	public String getTime () { return Entry.getTime (mDate); }
}
