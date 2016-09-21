package itp341.cammarano.colin.finalproject.core.entries;

import java.util.List;
import java.util.Map;

/**
 * A block is an entry that shows up multiple times in the calendar.
 * Created by Colin Cammarano on 4/24/16.
 */
public class Block extends Entry {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private Map<String, Long> mEnd;
	private List<Long> mDays;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Block (String name, Map<String, Long> startDate, Map<String, Long> end, List<Long> days, int category, int alert) {
		super(name, startDate, category, alert);

		mEnd = end;
		mDays = days;
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public Map<String, Long> getEnd () { return mEnd; }
	public List<Long> getDays () { return mDays; }
}
