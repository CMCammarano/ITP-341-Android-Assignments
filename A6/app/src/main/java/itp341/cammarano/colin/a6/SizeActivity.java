package itp341.cammarano.colin.a6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

public class SizeActivity extends AppCompatActivity {

	private Spinner mSpinnerSize;
	private Button mButtonConfirm;

	private boolean mPuzzleColorDone;
	private boolean mPuzzleSizeDone;
	private boolean mPuzzleNameDone;

	private int[] mColor;
	private int mSize;
	private String mName;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_size);

		// Set up UI
		mSpinnerSize = (Spinner)findViewById (R.id.spinner_size);
		mButtonConfirm = (Button)findViewById (R.id.button_size_done);

		// Get any data from a previous intent
		Intent i = getIntent ();
		mPuzzleColorDone = i.getBooleanExtra (Assignment6Activity.COLOR_RESULT, false);
		mPuzzleSizeDone = i.getBooleanExtra (Assignment6Activity.SIZE_RESULT, false);
		mPuzzleNameDone = i.getBooleanExtra (Assignment6Activity.NAME_RESULT, false);

		mSize = i.getIntExtra (Assignment6Activity.SIZE, 0);
		mName = i.getStringExtra (Assignment6Activity.NAME);
		mColor = i.getIntArrayExtra (Assignment6Activity.COLOR);
		if (mColor == null) {
			mColor = new int[3];
		}

		mSpinnerSize.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				mSize = position;
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {

			}
		});

		mButtonConfirm.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), Assignment6Activity.class);
				i.putExtra (Assignment6Activity.COLOR, mColor);
				i.putExtra (Assignment6Activity.SIZE, mSize);
				i.putExtra (Assignment6Activity.NAME, mName);
				i.putExtra (Assignment6Activity.COLOR_RESULT, mPuzzleColorDone);
				i.putExtra (Assignment6Activity.SIZE_RESULT, mPuzzleSizeDone);
				i.putExtra (Assignment6Activity.NAME_RESULT, mPuzzleNameDone);

				setResult (AppCompatActivity.RESULT_OK, i);
				finish ();
				//startActivity (i);
			}
		});
	}
}
