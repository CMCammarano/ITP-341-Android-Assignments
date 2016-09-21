package itp341.cammarano.colin.finalproject.ui.fragments.dialog;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.HashMap;

import itp341.cammarano.colin.finalproject.core.entries.Entry;

public class TimePickerDialogFragment extends DialogFragment  {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private HashMap<String, Long> mTime;
	private TimePickerDialog.OnTimeSetListener mListener;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public TimePickerDialogFragment () {}

	/******************************************************/
	// DIALOG METHODS
	/******************************************************/
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		return new TimePickerDialog (getActivity(), mListener, mTime.get (Entry.KEY_HOUR).intValue (), mTime.get (Entry.KEY_MINUTE).intValue (), true);
	}

	/******************************************************/
	// MUTATORS
	/******************************************************/
	public void setTime (HashMap<String, Long> time) { mTime = time; }
	public void setListener (TimePickerDialog.OnTimeSetListener listener) { mListener = listener; }
}
