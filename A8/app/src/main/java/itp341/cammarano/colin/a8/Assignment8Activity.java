package itp341.cammarano.colin.a8;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class Assignment8Activity extends AppCompatActivity {

	public static final int ACTIVITY_MAIN = 0;
	public static final int ACTIVITY_EDIT = 1;

	public static final String POSITION_KEY = "itp341.cammarano.colin.a8.position";
	public static final String TITLE_KEY = "itp341.cammarano.colin.a8.title";
	public static final String CONTENT_KEY = "itp341.cammarano.colin.a8.content";

	private NoteAdapter mNoteAdapter;
	private ListView mListView;
	private Button mButtonAddNote;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_assignment8);

		// Set up the list view and the adapter.
		mNoteAdapter = new NoteAdapter (this);
		mListView = (ListView)findViewById (R.id.listView_notes);
		mListView.setAdapter (mNoteAdapter);

		mListView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
				Note note = mNoteAdapter.getItem (position);
				Intent intent = new Intent (getApplicationContext (), NoteEditActivity.class);
				intent.putExtra (POSITION_KEY, position);
				intent.putExtra (TITLE_KEY, note.getTitle ());
				intent.putExtra (CONTENT_KEY, note.getContent ());
				startActivityForResult (intent, ACTIVITY_EDIT);
			}
		});

		// Set up the button
		mButtonAddNote = (Button)findViewById (R.id.button_add_note);
		mButtonAddNote.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getApplicationContext (), NoteEditActivity.class);
				startActivityForResult (intent, ACTIVITY_EDIT);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult (requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			int position = data.getIntExtra (POSITION_KEY, -1);
			String title = data.getStringExtra (TITLE_KEY);
			String content = data.getStringExtra (CONTENT_KEY);
			if (position >= 0) {
				Note note = mNoteAdapter.getItem (position);
				note.setTitle (title);
				note.setContent (content);
			}

			else {
				mNoteAdapter.add (new Note (title, content));
			}

			mNoteAdapter.notifyDataSetChanged ();
		}

		else if (resultCode == Activity.RESULT_CANCELED) {
			if (data != null) {
				int position = data.getIntExtra (POSITION_KEY, -1);
				if (position >= 0) {
					Note note = mNoteAdapter.getItem (position);
					mNoteAdapter.remove (note);
				}
			}
		}
	}
}
