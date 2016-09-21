package itp341.cammarano.colin.finalproject.ui.fragments.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.HashMap;

import itp341.cammarano.colin.finalproject.AddActivity;
import itp341.cammarano.colin.finalproject.MainActivity;
import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.core.entries.Entry;

public class DatePickerDialogFragment extends DialogFragment  {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private HashMap<String, Long> mDate;
	private DatePickerDialog.OnDateSetListener mListener;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public DatePickerDialogFragment () {}

	/******************************************************/
	// DIALOG METHODS
	/******************************************************/
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		return new DatePickerDialog(getActivity(), mListener, mDate.get (Entry.KEY_YEAR).intValue (), mDate.get (Entry.KEY_MONTH).intValue () - 1, mDate.get (Entry.KEY_DAY).intValue ());
	}

	/******************************************************/
	// MUTATORS
	/******************************************************/
	public void setDate (HashMap<String, Long> date) { mDate = date; }
	public void setListener (DatePickerDialog.OnDateSetListener listener) { mListener = listener; }
}
