package itp341.cammarano.colin.finalproject.core.entries;

import java.io.Serializable;
import java.util.Map;

/**
 * An event is an entry that has a defined start and end point. Events are one time entries, and will only show up in the planner once.
 * Created by Colin Cammarano on 4/24/16.
 */
public class Event extends Entry implements Serializable {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private Map<String, Long> mEnd;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Event (String name, Map<String, Long> start, Map<String, Long> end, int category, int alert) {
		super(name, start, category, alert);

		mEnd = end;
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public Map<String, Long> getEnd () { return mEnd; }
}
