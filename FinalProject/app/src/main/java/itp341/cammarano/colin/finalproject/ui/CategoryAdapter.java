package itp341.cammarano.colin.finalproject.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.core.Upcoming;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;

/**
 * Created by Colin Cammarano on 4/26/16.
 */
public class CategoryAdapter extends ArrayAdapter<Integer> {

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public CategoryAdapter (Context context) {
		super (context, 0, ApplicationManager.get (context).getCategories ());
	}

	/******************************************************/
	// ADAPTER METHODS
	/******************************************************/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from (getContext ()).inflate (R.layout.list_category, parent, false);
		}

		// Choose the correct color to use
		int color = ContextCompat.getColor (getContext (), ApplicationManager.get (getContext ()).getCategories ().get (position));
		View bullet = row.findViewById (R.id.bullet_add_category);
		bullet.setBackgroundColor (color);

		return row;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from (getContext ()).inflate (R.layout.list_category, parent, false);
		}

		// Choose the correct color to use
		int color = ContextCompat.getColor (getContext (), ApplicationManager.get (getContext ()).getCategories ().get (position));
		View bullet = row.findViewById (R.id.bullet_add_category);
		bullet.setBackgroundColor (color);

		return row;
	}
}
