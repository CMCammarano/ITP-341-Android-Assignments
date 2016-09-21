package itp341.cammarano.colin.finalproject.singleton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import itp341.cammarano.colin.finalproject.LoginActivity;
import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.RegisterActivity;
import itp341.cammarano.colin.finalproject.core.Quote;
import itp341.cammarano.colin.finalproject.core.Upcoming;
import itp341.cammarano.colin.finalproject.core.alerts.AlertReceiver;
import itp341.cammarano.colin.finalproject.core.entries.Block;
import itp341.cammarano.colin.finalproject.core.entries.Entry;
import itp341.cammarano.colin.finalproject.core.entries.Event;
import itp341.cammarano.colin.finalproject.core.entries.Notification;
import itp341.cammarano.colin.finalproject.core.login.User;

/**
 * Created by Colin Cammarano on 4/25/16.
 * I had discovered that the "pre-Google-acquisition" version of Firebase for Android is nigh-on impossible to unit test without cluttering up everything with hacks or making some obnoxiously complex mock object.
 * Given the time constraints I had with this project, I had to make do with manual UI tests for this -- the new version of Firebase supposedly is far more testable.
 * My future release has since shifted to the new version of Firebase managed by Google.
 */
public class ApplicationManager {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private User mUser;
	private ArrayList<Upcoming> mUpcoming;
	private ArrayList<Event> mEvents;
	private ArrayList<Block> mBlocks;
	private ArrayList<Notification> mNotifications;
	private ArrayList<Integer> mCategories;
	private ArrayList<Quote> mQuotes;
	private Firebase mDatabase;
	private Context mContext;

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	private static final String DATABASE = "https://newday-demo.firebaseio.com/";
	public static final String PREFERENCES = "edu.itp341.newday";

	private static final String KEY_QUOTES = "quotes";
	private static final String KEY_EVENTS = "events";
	private static final String KEY_BLOCKS = "blocks";
	private static final String KEY_NOTIFICATIONS = "notifications";

	public static final String KEY_USERS = "users";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_USERNAME = "username";

	private static final String KEY_TITLE = "title";
	private static final String KEY_START = "start";
	private static final String KEY_END = "end";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_ALERT = "alert";
	private static final String KEY_DAYS = "days";

	public static final String KEY_QUOTE = "quote";
	public static final String KEY_AUTHOR = "author";

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	private ApplicationManager () {
		mUser = null;
		mUpcoming = new ArrayList<> ();
		mEvents = new ArrayList<> ();
		mBlocks = new ArrayList<> ();
		mNotifications = new ArrayList<> ();
		mCategories = new ArrayList<> ();
		mQuotes = new ArrayList<> ();
	}

	/******************************************************/
	// PRIVATE MEMBER METHODS
	/******************************************************/
	private void initialize () {

		// Initialize Firebase (without offline storage for now)
		//Firebase.getDefaultConfig().setPersistenceEnabled(true);
		mDatabase = new Firebase(DATABASE);

		// TODO: Load user data from persistent storage, login, and synchronize data -- done in splash screen

		// Set up colors
		mCategories.add (R.color.category_default);
		mCategories.add (R.color.category_light_blue);
		mCategories.add (R.color.category_med_blue);
		mCategories.add (R.color.category_dark_blue);
		/*
		mCategories.add (R.color.category_light_teal);
		mCategories.add (R.color.category_med_teal);
		mCategories.add (R.color.category_dark_teal);
		mCategories.add (R.color.category_light_green);
		mCategories.add (R.color.category_med_green);
		mCategories.add (R.color.category_dark_blue);
		*/

		// Load up quotes
		Firebase quoteRef = mDatabase.child (KEY_QUOTES);
		quoteRef.addListenerForSingleValueEvent (new ValueEventListener () {
			@Override
			public void onDataChange (DataSnapshot dataSnapshot) {

				for (DataSnapshot q : dataSnapshot.getChildren ()) {
					String quote = (String)q.child (KEY_QUOTE).getValue ();
					String author = (String)q.child (KEY_AUTHOR).getValue ();
					mQuotes.add (new Quote (quote, author));
				}
			}

			@Override
			public void onCancelled (FirebaseError firebaseError) {}
		});
	}

	private void createUpcoming (Entry entry) {
		// Create our upcoming event
		Upcoming upcoming = new Upcoming (entry);
		mUpcoming.add (upcoming);
	}

	// Storing user data
	private void storeUserData (String email, String password) {

		// TODO: Hash password for actual project release
		SharedPreferences.Editor editor = mContext.getSharedPreferences (PREFERENCES, Context.MODE_PRIVATE).edit ();
		editor.putString (KEY_EMAIL, email);
		editor.putString (KEY_PASSWORD, password);
		editor.commit ();
	}

	private void addAlert (Entry entry) {

		// Create the notification here
		android.app.Notification.Builder builder = new android.app.Notification.Builder(mContext);
		builder.setContentTitle (entry.getName ());
		builder.setContentText (String.format (mContext.getString (R.string.label_detail_time), Entry.getTime (entry.getStart ())) + " on " + Entry.getDate (entry.getStart ()));
		builder.setSmallIcon (R.drawable.ic_action_calendar_day);
		android.app.Notification notification = builder.build ();

		// Ready the notification intent
		Intent notificationIntent = new Intent (mContext, AlertReceiver.class);
		notificationIntent.putExtra (AlertReceiver.NOTIFICATION_ID, 1);
		notificationIntent.putExtra (AlertReceiver.NOTIFICATION, notification);
		PendingIntent pendingIntent = PendingIntent.getBroadcast (mContext, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the event start time
		Calendar calendar = Calendar.getInstance ();
		calendar.set (entry.getStart ().get (Entry.KEY_YEAR).intValue (), entry.getStart ().get (Entry.KEY_MONTH).intValue () - 1, entry.getStart ().get (Entry.KEY_DAY).intValue (), entry.getStart ().get (Entry.KEY_HOUR).intValue (), entry.getStart ().get (Entry.KEY_MINUTE).intValue ());

		// Get the alert delay -- calculate the difference between the start time and current time, and set the interval that way.
		long delay = SystemClock.elapsedRealtime () + (calendar.getTimeInMillis () - System.currentTimeMillis ());
		switch (entry.getAlert ()) {
			case 0:
				delay += 1000;				// For demonstration purposes
				break;
			case 1:
				delay += 0;
				break;
			case 2:
				delay -= 1000 * 60 * 5;
				break;
			case 3:
				delay -= 1000 * 60 * 15;
				break;
			case 4:
				delay -= 1000 * 60 * 30;
				break;
			case 5:
				delay -= 1000 * 60 * 60;
				break;
		}

		// Schedule the alarm here
		AlarmManager alarmManager = (AlarmManager)mContext.getSystemService (Context.ALARM_SERVICE);
		alarmManager.set (AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
	}

	/******************************************************/
	// PUBLIC MEMBER METHODS
	/******************************************************/
	public void addEvent (Event event) {

		// Add the event to the local list of events
		mEvents.add (event);

		// If we are logged in, add an event to the database -- events are stored by ID
		if (isLoggedIn ()) {
			Firebase events = mDatabase.child (KEY_USERS).child (mUser.getUserID ()).child (KEY_EVENTS).push ();
			events.child (KEY_TITLE).setValue (event.getName ());
			events.child (KEY_START).setValue (event.getStart ());
			events.child (KEY_END).setValue (event.getEnd ());
			events.child (KEY_CATEGORY).setValue (event.getCategory ());
			events.child (KEY_ALERT).setValue (event.getAlert ());
		}

		// Create our upcoming event
		createUpcoming (event);

		// Add an alert!
		addAlert (event);
	}

	// TODO: Support blocks as repeating events for actual project release
	public void addBlock (Block block) {

		// Add the event to the local list of blocks
		mBlocks.add (block);

		// If we are logged in, add a block to the database -- blocks are stored by ID
		if (isLoggedIn ()) {
			Firebase blocks = mDatabase.child (KEY_USERS).child (mUser.getUserID ()).child (KEY_BLOCKS).push ();
			blocks.child (KEY_TITLE).setValue (block.getName ());
			blocks.child (KEY_START).setValue (block.getStart ());
			blocks.child (KEY_END).setValue (block.getEnd ());
			blocks.child (KEY_CATEGORY).setValue (block.getCategory ());
			blocks.child (KEY_ALERT).setValue (block.getAlert ());
			blocks.child (KEY_DAYS).setValue (block.getDays ());
		}

		// Create our upcoming event
		createUpcoming (block);
	}

	public void addNotification (Notification notification) {

		// Add the event to the local list of notifications
		mNotifications.add (notification);

		// If we are logged in, add a notification to the database -- notifications are stored by ID
		if (isLoggedIn ()) {
			Firebase notifications = mDatabase.child (KEY_USERS).child (mUser.getUserID ()).child (KEY_NOTIFICATIONS).push ();
			notifications.child (KEY_TITLE).setValue (notification.getName ());
			notifications.child (KEY_START).setValue (notification.getStart ());
			notifications.child (KEY_CATEGORY).setValue (notification.getCategory ());
			notifications.child (KEY_ALERT).setValue (notification.getAlert ());
		}

		// Create our upcoming event
		createUpcoming (notification);

		// Add an alert!
		addAlert (notification);
	}

	public void logoff () {
		mUser = null;
		mBlocks = new ArrayList<> ();
		mEvents = new ArrayList<> ();
		mNotifications = new ArrayList<> ();
		mUpcoming = new ArrayList<> ();

		// Remove user data from the device!
		SharedPreferences.Editor editor = mContext.getSharedPreferences (PREFERENCES, Context.MODE_PRIVATE).edit ();
		editor.clear ();
		editor.commit ();

		// Unauthorize the database
		mDatabase.unauth ();
	}

	public void login (String email, String password, final LoginActivity.OnLoginListener listener) {

		final String e = email;
		final String p = password;
		mDatabase.authWithPassword (email, password, new Firebase.AuthResultHandler () {
			@Override
			public void onAuthenticated (AuthData authData) {

				// Set the current user and flag them as logged in
				final String userID = authData.getUid ();

				Firebase user = mDatabase.child (KEY_USERS).child (userID);
				user.addListenerForSingleValueEvent (new ValueEventListener () {
					@Override
					public void onDataChange (DataSnapshot dataSnapshot) {

						// Get the user data
						Map<String, String> data = (Map<String, String>) dataSnapshot.getValue ();
						String name = data.get (KEY_NAME);
						String username = data.get (KEY_USERNAME);

						// Set the user
						mInstance.mUser = new User (userID, name, username, e, p);

						// Save the user data to the device!
						storeUserData (e, p);

						// Fetch entries from the database! Start with events!
						DataSnapshot eventRef = dataSnapshot.child (KEY_EVENTS);

						for (DataSnapshot e : eventRef.getChildren ()) {

							String title = (String) e.child (KEY_TITLE).getValue ();
							long category = (Long) e.child (KEY_CATEGORY).getValue ();
							long alert = (Long) e.child (KEY_ALERT).getValue ();
							Map<String, Long> start = (Map<String, Long>) e.child (KEY_START).getValue ();
							Map<String, Long> end = (Map<String, Long>) e.child (KEY_END).getValue ();

							Event event = new Event (title, start, end, (int) category, (int) alert);
							mEvents.add (event);
							createUpcoming (event);
						}

						// Now, get notifications!
						DataSnapshot notificationRef = dataSnapshot.child (KEY_NOTIFICATIONS);
						for (DataSnapshot n : notificationRef.getChildren ()) {

							String title = (String) n.child (KEY_TITLE).getValue ();
							long category = (Long) n.child (KEY_CATEGORY).getValue ();
							long alert = (Long) n.child (KEY_ALERT).getValue ();
							Map<String, Long> start = (Map<String, Long>) n.child (KEY_START).getValue ();

							Notification notification = new Notification (title, start, (int) category, (int) alert);
							mNotifications.add (notification);
							createUpcoming (notification);
						}

						// Now, get blocks!
						DataSnapshot blockRef = dataSnapshot.child (KEY_BLOCKS);
						for (DataSnapshot b : blockRef.getChildren ()) {

							String title = (String) b.child (KEY_TITLE).getValue ();
							long category = (Long) b.child (KEY_CATEGORY).getValue ();
							long alert = (Long) b.child (KEY_ALERT).getValue ();
							Map<String, Long> start = (Map<String, Long>) b.child (KEY_START).getValue ();
							Map<String, Long> end = (Map<String, Long>) b.child (KEY_END).getValue ();
							List<Long> days = (List<Long>) b.child (KEY_DAYS).getValue ();

							Block block = new Block (title, start, end, days, (int) category, (int) alert);
							mBlocks.add (block);
							createUpcoming (block);
						}

						// Login successful!
						if (listener != null) {
							listener.onSuccess ();
						}
					}

					@Override
					public void onCancelled (FirebaseError firebaseError) {
					}
				});
			}

			@Override
			public void onAuthenticationError (FirebaseError firebaseError) {
				// Login failure!
				if (listener != null) {
					listener.onFailure ();
				}
			}
		});
	}

	public void register (String name, String username, String email, String password, final RegisterActivity.OnRegisterListener listener) {

		final String n = name;
		final String u = username;
		final String e = email;
		final String p = password;
		mDatabase.createUser (email, password, new Firebase.ValueResultHandler<Map<String, Object>> () {
			@Override
			public void onSuccess (Map<String, Object> stringObjectMap) {
				String userID = (String) stringObjectMap.get ("uid");
				Map<String, String> user = new HashMap<> ();
				user.put (KEY_NAME, n);
				user.put (KEY_USERNAME, u);
				user.put (KEY_EMAIL, e);
				mDatabase.child (KEY_USERS).child (userID).setValue (user);

				// Set the current user and flag them as logged in
				mInstance.mUser = new User (userID, n, u, e, p);

				// Save the user data to the device!
				storeUserData (e, p);

				// Fire the listener
				listener.onSuccess ();
			}

			@Override
			public void onError (FirebaseError firebaseError) {
				listener.onFailure ();
			}
		});
	}

	// Adding quotes
	public void addQuote (Quote quote) {

		// If we are logged in, add an event to the database -- events are stored by ID
		if (isLoggedIn ()) {
			Firebase quotes = mDatabase.child (KEY_QUOTES).push ();
			quotes.child (KEY_QUOTE).setValue (quote.getQuote ());
			quotes.child (KEY_AUTHOR).setValue (quote.getAuthor ());
		}

		// Add the quote locally
		mQuotes.add (quote);
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public ArrayList<Upcoming> getUpcoming () { return mUpcoming; }
	public ArrayList<Event> getEvents () { return mEvents; }
	public ArrayList<Block> getBlocks () { return mBlocks; }
	public ArrayList<Notification> getNotifications () { return mNotifications; }
	public ArrayList<Integer> getCategories () { return mCategories; }
	public ArrayList<Quote> getQuotes () { return mQuotes; }
	public User getActiveUser () { return mUser; }
	public boolean isLoggedIn () { return (mUser != null); }

	public Quote getRandomQuote () {
		if (mQuotes.size () > 0) {
			Random random = new Random ();
			int index = random.nextInt (mQuotes.size ());

			return mQuotes.get (index);
		}

		else {
			return new Quote (mContext.getResources ().getString (R.string.quote_default), mContext.getResources ().getString (R.string.author_default));
		}
	}

	/******************************************************/
	// SINGLETON
	/******************************************************/
	private static ApplicationManager mInstance;
	public static ApplicationManager get (Context context) {

		// Always set the Firebase context when accessing the singleton
		Firebase.setAndroidContext(context);

		// Lazy load if necessary.
		if (mInstance == null) {
			mInstance = new ApplicationManager ();

			// Initialize the data in the newly created singleton instance -- get data for the current user from the database.
			mInstance.initialize ();
		}

		// Set the instance context
		mInstance.mContext = context;

		// Return the instance
		return mInstance;
	}
}
