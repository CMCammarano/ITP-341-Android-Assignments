package itp341.cammarano.colin.finalproject;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import itp341.cammarano.colin.finalproject.core.Quote;
import itp341.cammarano.colin.finalproject.core.Upcoming;
import itp341.cammarano.colin.finalproject.core.entries.Entry;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;

public class EntryDetailActivity extends AppCompatActivity {

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	public static final String KEY_UPCOMING = "edu.itp341.NewDay.Upcoming";

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private TextView mLabelTitle;
	private TextView mLabelStartDate;
	private TextView mLabelStartTime;
	private TextView mLabelType;
	private TextView mLabelAlert;
	private TextView mLabelQuote;
	private TextView mLabelAuthor;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_entry_detail);

		// Prepare the text fields
		mLabelTitle = (TextView)findViewById (R.id.detail_label_title);
		mLabelStartDate = (TextView)findViewById (R.id.detail_label_date);
		mLabelStartTime = (TextView)findViewById (R.id.detail_label_time);
		mLabelType = (TextView)findViewById (R.id.detail_label_type_upcoming);
		mLabelAlert = (TextView)findViewById (R.id.detail_label_alert_upcoming);
		mLabelQuote = (TextView)findViewById (R.id.detail_label_quote);
		mLabelAuthor = (TextView)findViewById (R.id.detail_label_author);

		// Get the intent
		Intent intent = getIntent ();
		if (intent != null) {

			// Get the name
			int index = intent.getIntExtra (KEY_UPCOMING, 0);
			Upcoming upcoming = ApplicationManager.get (getApplicationContext ()).getUpcoming ().get (index);
			mLabelTitle.setText (upcoming.getName ());

			// Choose the correct color to use
			int color = ContextCompat.getColor (getApplicationContext (), ApplicationManager.get (getApplicationContext ()).getCategories ().get (upcoming.getCategory ()));
			mLabelTitle.setTextColor (color);

			// Get the type
			String type = "";
			switch (upcoming.getType ()) {
				case Upcoming.BLOCK_TYPE:
					type = getApplicationContext ().getResources ().getString (R.string.label_block_type);
					break;

				case Upcoming.EVENT_TYPE:
					type = getApplicationContext ().getResources ().getString (R.string.label_event_type);
					break;

				case Upcoming.NOTIFICATION_TYPE:
					type = getApplicationContext ().getResources ().getString (R.string.label_notification_type);
					break;
			}
			mLabelType.setText (type);

			// Set the start date and time
			mLabelStartDate.setText (upcoming.getDate ());
			mLabelStartTime.setText (String.format (getString (R.string.label_detail_time), upcoming.getTime()));

			// Set the alert label
			String[] arr = getResources ().getStringArray (R.array.array_alerts);
			mLabelAlert.setText (arr[upcoming.getAlert ()]);
		}

		// Get a random quote from the database
		Quote quote = ApplicationManager.get (getApplicationContext ()).getRandomQuote ();
		mLabelQuote.setText ("\"" + quote.getQuote () + "\"");
		mLabelAuthor.setText ("- " + quote.getAuthor ());
	}
}
