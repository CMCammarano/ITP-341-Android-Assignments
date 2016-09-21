package itp341.cammarano.colin.a8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {

	private EditText mEditTitle;
	private EditText mEditContent;
	private Button mButtonSave;
	private Button mButtonDelete;

	private int mPosition;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_note_edit);

		// Prepare the edit texts
		mEditTitle = (EditText)findViewById (R.id.edit_title);
		mEditContent = (EditText)findViewById (R.id.edit_content);

		// Get previous intent
		Intent i = getIntent ();
		mPosition = i.getIntExtra (Assignment8Activity.POSITION_KEY, -1);
		String title = i.getStringExtra (Assignment8Activity.TITLE_KEY);
		String content = i.getStringExtra (Assignment8Activity.CONTENT_KEY);

		mEditTitle.setText (title);
		mEditContent.setText (content);

		// Prepare the buttons
		mButtonSave = (Button)findViewById (R.id.button_save);
		mButtonDelete = (Button)findViewById (R.id.button_delete);

		mButtonSave.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), Assignment8Activity.class);
				i.putExtra (Assignment8Activity.POSITION_KEY, mPosition);
				i.putExtra (Assignment8Activity.TITLE_KEY, mEditTitle.getText ().toString ());
				i.putExtra (Assignment8Activity.CONTENT_KEY, mEditContent.getText ().toString ());
				setResult (AppCompatActivity.RESULT_OK, i);
				finish ();
			}
		});

		mButtonDelete.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), Assignment8Activity.class);
				i.putExtra (Assignment8Activity.POSITION_KEY, mPosition);
				setResult (AppCompatActivity.RESULT_CANCELED, i);
				finish ();
			}
		});
	}
}
