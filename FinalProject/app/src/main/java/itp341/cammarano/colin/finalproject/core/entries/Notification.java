package itp341.cammarano.colin.finalproject.core.entries;

import java.util.Map;

/**
 * A notification is an entry that only has a start point. Notifications are one time entries that show up in the planner only once.
 * Created by Colin Cammarano on 4/24/16.
 */
public class Notification extends Entry {

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Notification (String name, Map<String, Long> date, int category, int alert) {
		super(name, date, category, alert);
	}
}
