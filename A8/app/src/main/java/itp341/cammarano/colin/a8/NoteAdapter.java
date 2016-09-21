package itp341.cammarano.colin.a8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cmcammarano on 4/3/16.
 */
public class NoteAdapter extends ArrayAdapter<Note> {

	public NoteAdapter (Context context) {
		super(context, 0, NoteManager.get ().getNotes ());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			row = LayoutInflater.from (getContext ()).inflate (R.layout.list_note, parent, false);
		}

		Note note = NoteManager.get ().getNotes ().get (position);
		if (note != null) {
			TextView textTitle = (TextView)row.findViewById (R.id.label_note_title);
			TextView textDate = (TextView)row.findViewById (R.id.label_note_date);

			textTitle.setText (note.getTitle());
			textDate.setText (note.getDate ());
		}

		return row;
	}
}
