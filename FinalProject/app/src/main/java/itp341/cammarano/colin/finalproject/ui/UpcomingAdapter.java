package itp341.cammarano.colin.finalproject.ui;

import android.content.Context;
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
public class UpcomingAdapter extends ArrayAdapter<Upcoming> {

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public UpcomingAdapter (Context context) {
		super (context, 0, ApplicationManager.get (context).getUpcoming ());
	}

	/******************************************************/
	// ADAPTER METHODS
	/******************************************************/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from (getContext ()).inflate (R.layout.list_upcoming, parent, false);
		}

		// Update the text for each list view entry
		Upcoming upcoming = ApplicationManager.get (row.getContext ()).getUpcoming ().get (position);
		if (upcoming != null) {

			TextView textName = (TextView)row.findViewById (R.id.upcoming_label_name);
			TextView textDate = (TextView)row.findViewById (R.id.upcoming_label_date);
			TextView textType = (TextView)row.findViewById (R.id.upcoming_label_type);

			textName.setText (upcoming.getName ());
			textDate.setText (upcoming.getDate ());

			String type = "";
			switch (upcoming.getType ()) {
				case Upcoming.BLOCK_TYPE:
					type = getContext ().getResources ().getString (R.string.label_block_type);
					break;

				case Upcoming.EVENT_TYPE:
					type = getContext ().getResources ().getString (R.string.label_event_type);
					break;

				case Upcoming.NOTIFICATION_TYPE:
					type = getContext ().getResources ().getString (R.string.label_notification_type);
					break;
			}
			textType.setText (type);

			// Choose the correct color to use
			int color = ContextCompat.getColor (getContext (), ApplicationManager.get (getContext ()).getCategories ().get (upcoming.getCategory ()));
			View bullet = row.findViewById (R.id.upcoming_bullet_category);
			bullet.setBackgroundColor (color);
		}
		return row;
	}
}
