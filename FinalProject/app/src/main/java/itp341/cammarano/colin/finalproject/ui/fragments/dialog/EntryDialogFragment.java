package itp341.cammarano.colin.finalproject.ui.fragments.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itp341.cammarano.colin.finalproject.AddActivity;
import itp341.cammarano.colin.finalproject.MainActivity;
import itp341.cammarano.colin.finalproject.R;

public class EntryDialogFragment extends DialogFragment {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private AlertDialog mDialog;

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	public static final String EXTRA_SELECTION = "itp341.cammarano.colin.selection";

	/******************************************************/
	// DIALOG METHODS
	/******************************************************/
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {

		// Create the dialog and the initial view
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity ());

		// Setup view
		LayoutInflater inflater = getActivity ().getLayoutInflater ();
		View view = inflater.inflate (R.layout.dialog_entry, null);
		builder.setView (view);

		// Set up buttons
		Button createBlock = (Button)view.findViewById (R.id.dialog_entry_button_create_block);
		createBlock.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getActivity ().getApplicationContext (), AddActivity.class);
				intent.putExtra (EXTRA_SELECTION, AddActivity.OPTION_CREATE_BLOCK);
				getActivity ().startActivityForResult (intent, MainActivity.ACTIVITY_ADD);

				if (mDialog != null) {
					mDialog.cancel ();
				}
			}
		});

		Button createEvent = (Button)view.findViewById (R.id.dialog_entry_button_create_event);
		createEvent.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getActivity ().getApplicationContext (), AddActivity.class);
				intent.putExtra (EXTRA_SELECTION, AddActivity.OPTION_CREATE_EVENT);
				getActivity ().startActivityForResult (intent, MainActivity.ACTIVITY_ADD);

				if (mDialog != null) {
					mDialog.cancel ();
				}
			}
		});

		Button createNotification = (Button)view.findViewById (R.id.dialog_entry_button_create_notification);
		createNotification.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getActivity ().getApplicationContext (), AddActivity.class);
				intent.putExtra (EXTRA_SELECTION, AddActivity.OPTION_CREATE_NOTIFICATION);
				getActivity ().startActivityForResult (intent, MainActivity.ACTIVITY_ADD);

				if (mDialog != null) {
					mDialog.cancel ();
				}
			}
		});

		mDialog = builder.create ();
		return mDialog;
	}
}