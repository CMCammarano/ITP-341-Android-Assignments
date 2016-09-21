package itp341.cammarano.colin.finalproject.ui.fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import itp341.cammarano.colin.finalproject.MainActivity;
import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.core.entries.Entry;
import itp341.cammarano.colin.finalproject.core.entries.Block;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;
import itp341.cammarano.colin.finalproject.ui.CategoryAdapter;
import itp341.cammarano.colin.finalproject.ui.fragments.dialog.DatePickerDialogFragment;
import itp341.cammarano.colin.finalproject.ui.fragments.dialog.TimePickerDialogFragment;

public class AddBlockFragment extends Fragment {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private HashMap<String, Long> mStartDate;
	private HashMap<String, Long> mEndDate;
	private ArrayList<Long> mDays;
	private EditText mEditName;
	private Button mButtonStartDate;
	private Button mButtonStartTime;
	private Button mButtonEndDate;
	private Button mButtonEndTime;
	private Spinner mSpinnerCategories;
	private Spinner mSpinnerAlerts;
	private Button mButtonSubmit;

	// Toggle Buttons
	private ToggleButton mToggleSun;
	private ToggleButton mToggleMon;
	private ToggleButton mToggleTues;
	private ToggleButton mToggleWed;
	private ToggleButton mToggleThurs;
	private ToggleButton mToggleFri;
	private ToggleButton mToggleSat;

	/******************************************************/
	// FACTORY
	/******************************************************/
	public static AddBlockFragment newInstance () {
		AddBlockFragment fragment = new AddBlockFragment ();
		Bundle args = new Bundle ();
		fragment.setArguments (args);
		return fragment;
	}

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public AddBlockFragment () {}

	/******************************************************/
	// FRAGMENT FUNCTIONS
	/******************************************************/
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate (R.layout.fragment_add_block, container, false);

		// Get UI element references
		mEditName = (EditText)view.findViewById (R.id.block_edit_name);

		// Initialize the days array
		mDays = new ArrayList<> ();

		// Get the current date
		SimpleDateFormat df = new SimpleDateFormat ("h:mm a");
		final Date date = new Date ();
		final Calendar calendar = Calendar.getInstance ();
		calendar.setTime (date);

		mStartDate = new HashMap<> ();
		mStartDate.put (Entry.KEY_DAY, (long) calendar.get (Calendar.DAY_OF_MONTH));
		mStartDate.put (Entry.KEY_MONTH, (long) calendar.get (Calendar.MONTH) + 1);
		mStartDate.put (Entry.KEY_YEAR, (long) calendar.get (Calendar.YEAR));
		mStartDate.put (Entry.KEY_HOUR, (long) calendar.get (Calendar.HOUR_OF_DAY));
		mStartDate.put (Entry.KEY_MINUTE, (long) calendar.get (Calendar.MINUTE));

		// Set the end time
		calendar.add (Calendar.MINUTE, 30);

		mEndDate = new HashMap<> ();
		mEndDate.put (Entry.KEY_DAY, (long) calendar.get (Calendar.DAY_OF_MONTH));
		mEndDate.put (Entry.KEY_MONTH, (long) calendar.get (Calendar.MONTH) + 1);
		mEndDate.put (Entry.KEY_YEAR, (long) calendar.get (Calendar.YEAR));
		mEndDate.put (Entry.KEY_HOUR, (long) calendar.get (Calendar.HOUR_OF_DAY));
		mEndDate.put (Entry.KEY_MINUTE, (long) calendar.get (Calendar.MINUTE));

		// Toggle Buttons
		mToggleSun = (ToggleButton)view.findViewById (R.id.block_sun_toggle);
		mToggleMon = (ToggleButton)view.findViewById (R.id.block_mon_toggle);
		mToggleTues = (ToggleButton)view.findViewById (R.id.block_tues_toggle);
		mToggleWed = (ToggleButton)view.findViewById (R.id.block_wed_toggle);
		mToggleThurs = (ToggleButton)view.findViewById (R.id.block_thurs_toggle);
		mToggleFri = (ToggleButton)view.findViewById (R.id.block_fri_toggle);
		mToggleSat = (ToggleButton)view.findViewById (R.id.block_sat_toggle);

		// Setup buttons
		mButtonStartDate = (Button)view.findViewById (R.id.block_button_start_date);
		String startDate = Entry.getDate (mStartDate);
		mButtonStartDate.setText (startDate);
		mButtonStartDate.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				DatePickerDialogFragment dialog = new DatePickerDialogFragment ();
				dialog.setDate (mStartDate);
				dialog.setListener (new DatePickerDialog.OnDateSetListener () {
					@Override
					public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth) {

						Calendar calendar = Calendar.getInstance ();
						calendar.set (year, monthOfYear, dayOfMonth);

						mStartDate.put (Entry.KEY_DAY, (long) calendar.get (Calendar.DAY_OF_MONTH));
						mStartDate.put (Entry.KEY_MONTH, (long) calendar.get (Calendar.MONTH) + 1);
						mStartDate.put (Entry.KEY_YEAR, (long) calendar.get (Calendar.YEAR));

						String startDate = Entry.getDate (mStartDate);
						mButtonStartDate.setText (startDate);
					}
				});
				FragmentManager fm = getActivity ().getSupportFragmentManager ();
				dialog.show (fm, "datePicker");
			}
		});

		mButtonStartTime = (Button)view.findViewById (R.id.block_button_start_time);
		String startTime = df.format (date);
		mButtonStartTime.setText (startTime);
		mButtonStartTime.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				TimePickerDialogFragment dialog = new TimePickerDialogFragment ();
				dialog.setTime (mStartDate);
				dialog.setListener (new TimePickerDialog.OnTimeSetListener () {
					@Override
					public void onTimeSet (TimePicker view, int hourOfDay, int minute) {

						Calendar calendar = Calendar.getInstance ();
						calendar.set (Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set (Calendar.MINUTE, minute);

						mStartDate.put (Entry.KEY_HOUR, (long) calendar.get (Calendar.HOUR_OF_DAY));
						mStartDate.put (Entry.KEY_MINUTE, (long) calendar.get (Calendar.MINUTE));

						String startTime = Entry.getTime (mStartDate);
						mButtonStartTime.setText (startTime);
					}
				});
				FragmentManager fm = getActivity ().getSupportFragmentManager ();
				dialog.show (fm, "timePicker");
			}
		});

		mButtonEndDate = (Button)view.findViewById (R.id.block_button_end_date);
		String endDate = Entry.getDate (mEndDate);
		mButtonEndDate.setText (endDate);
		mButtonEndDate.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				DatePickerDialogFragment dialog = new DatePickerDialogFragment ();
				dialog.setDate (mEndDate);
				dialog.setListener (new DatePickerDialog.OnDateSetListener () {
					@Override
					public void onDateSet (DatePicker view, int year, int monthOfYear, int dayOfMonth) {

						Calendar calendar = Calendar.getInstance ();
						calendar.set (year, monthOfYear, dayOfMonth);

						mEndDate.put (Entry.KEY_DAY, (long) calendar.get (Calendar.DAY_OF_MONTH));
						mEndDate.put (Entry.KEY_MONTH, (long) calendar.get (Calendar.MONTH) + 1);
						mEndDate.put (Entry.KEY_YEAR, (long) calendar.get (Calendar.YEAR));

						String startDate = Entry.getDate (mEndDate);
						mButtonEndDate.setText (startDate);
					}
				});
				FragmentManager fm = getActivity ().getSupportFragmentManager ();
				dialog.show (fm, "datePicker");
			}
		});

		mButtonEndTime = (Button)view.findViewById (R.id.block_button_end_time);
		String endTime = df.format (calendar.getTime ());
		mButtonEndTime.setText (endTime);
		mButtonEndTime.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				TimePickerDialogFragment dialog = new TimePickerDialogFragment ();
				dialog.setTime (mEndDate);
				dialog.setListener (new TimePickerDialog.OnTimeSetListener () {
					@Override
					public void onTimeSet (TimePicker view, int hourOfDay, int minute) {

						Calendar calendar = Calendar.getInstance ();
						calendar.set (Calendar.HOUR_OF_DAY, hourOfDay);
						calendar.set (Calendar.MINUTE, minute);

						mEndDate.put (Entry.KEY_HOUR, (long) calendar.get (Calendar.HOUR_OF_DAY));
						mEndDate.put (Entry.KEY_MINUTE, (long) calendar.get (Calendar.MINUTE));

						String endTime = Entry.getTime (mEndDate);
						mButtonEndTime.setText (endTime);
					}
				});
				FragmentManager fm = getActivity ().getSupportFragmentManager ();
				dialog.show (fm, "timePicker");
			}
		});

		// Set up color picker
		CategoryAdapter adapter = new CategoryAdapter (getActivity ().getApplicationContext ());
		mSpinnerCategories = (Spinner)view.findViewById (R.id.block_spinner_category);
		mSpinnerCategories.setAdapter (adapter);

		// Set up the alert spinner
		mSpinnerAlerts = (Spinner)view.findViewById (R.id.block_spinner_alert);

		// Setup submission button
		mButtonSubmit = (Button)view.findViewById (R.id.block_button_submit);
		mButtonSubmit.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {

				// Compare the start and end dates/times to ensure that they are valid
				Calendar calendar = Calendar.getInstance ();
				calendar.set (mStartDate.get (Entry.KEY_YEAR).intValue (), mStartDate.get (Entry.KEY_MONTH).intValue () - 1, mStartDate.get (Entry.KEY_DAY).intValue (), mStartDate.get (Entry.KEY_HOUR).intValue (), mStartDate.get (Entry.KEY_MINUTE).intValue ());
				Date start = calendar.getTime ();

				calendar.set (mEndDate.get (Entry.KEY_YEAR).intValue (), mEndDate.get (Entry.KEY_MONTH).intValue () - 1, mEndDate.get (Entry.KEY_DAY).intValue (), mEndDate.get (Entry.KEY_HOUR).intValue (), mEndDate.get (Entry.KEY_MINUTE).intValue ());
				Date end = calendar.getTime ();
				if (start.getTime () > end.getTime ()) {
					String error = getString (R.string.error_add_time);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// Check the name
				String name = mEditName.getText ().toString ();
				if (name == null || name.isEmpty ()) {
					String error = getString (R.string.error_add_title);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// Check each button and push a value back into the days array
				if (mToggleSun.isChecked ()) {
					mDays.add ((long)1);
				}

				if (mToggleMon.isChecked ()) {
					mDays.add ((long)2);
				}

				if (mToggleTues.isChecked ()) {
					mDays.add ((long)3);
				}

				if (mToggleWed.isChecked ()) {
					mDays.add ((long)4);
				}

				if (mToggleThurs.isChecked ()) {
					mDays.add ((long)5);
				}

				if (mToggleFri.isChecked ()) {
					mDays.add ((long)6);
				}

				if (mToggleSat.isChecked ()) {
					mDays.add ((long)7);
				}

				// If no days are selected, don't progress
				if (mDays.size () <= 0) {
					String error = getString (R.string.error_add_days);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// Create an block object and pass it to the database
				int category = mSpinnerCategories.getSelectedItemPosition ();
				int alert = mSpinnerAlerts.getSelectedItemPosition ();
				Block block = new Block (name, mStartDate, mEndDate, mDays, category, alert);
				ApplicationManager.get (getActivity ().getApplicationContext ()).addBlock (block);

				Intent intent = new Intent (getActivity (), MainActivity.class);
				getActivity ().setResult (AppCompatActivity.RESULT_OK, intent);
				getActivity ().finish ();
			}
		});

		return view;
	}
}
