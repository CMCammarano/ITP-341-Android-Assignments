package itp341.cammarano.colin.a6;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Assignment6Activity extends AppCompatActivity {

	public static final String COLOR = "edu.usc.a6.color";
	public static final String SIZE = "edu.usc.a6.size";
	public static final String NAME = "edu.usc.a6.name";
	public static final String COLOR_RESULT = "edu.usc.a6.color_result";
	public static final String SIZE_RESULT = "edu.usc.a6.color_size";
	public static final String NAME_RESULT = "edu.usc.a6.color_name";

	public static final int END_ACTIVITY = 0;
	public static final int COLOR_ACTIVITY = 1;
	public static final int SIZE_ACTIVITY = 2;
	public static final int NAME_ACTIVITY = 3;

	private Button mButtonColor;
	private Button mButtonSize;
	private Button mButtonName;
	private Button mButtonSolve;

	private TextView mTextRiddleColor;
	private TextView mTextRiddleSize;
	private TextView mTextRiddleName;

	private boolean mPuzzleColorDone;
	private boolean mPuzzleSizeDone;
	private boolean mPuzzleNameDone;

	private int[] mColor;
	private int mSize;
	private String mName;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_assignment6);

		// Setup the buttons and text views
		mButtonColor = (Button)findViewById (R.id.button_room_color);
		mButtonSize = (Button)findViewById (R.id.button_room_size);
		mButtonName = (Button)findViewById (R.id.button_room_name);
		mButtonSolve = (Button)findViewById (R.id.button_submit);

		mTextRiddleColor = (TextView)findViewById (R.id.text_riddle_color);
		mTextRiddleSize = (TextView)findViewById (R.id.text_riddle_size);
		mTextRiddleName = (TextView)findViewById (R.id.text_riddle_name);

		// Get any data from a previous intent
		Intent i = getIntent ();
		mPuzzleColorDone = i.getBooleanExtra (COLOR_RESULT, false);
		mPuzzleSizeDone = i.getBooleanExtra (SIZE_RESULT, false);
		mPuzzleNameDone = i.getBooleanExtra (NAME_RESULT, false);

		mSize = i.getIntExtra (SIZE, 0);
		mName = i.getStringExtra (NAME);
		mColor = i.getIntArrayExtra (COLOR);
		if (mColor == null) {
			mColor = new int[3];
		}

		// Set up the text areas
		mTextRiddleColor.setText (String.format (getResources ().getString (R.string.text_riddle_color), getResources ().getString(R.string.color)));
		if (mColor[0] == 0 && mColor[1] == 0 && mColor[2] == 255) {
			mPuzzleColorDone = true;
			mTextRiddleColor.setTextColor (Color.GREEN);
		}

		else {
			mPuzzleColorDone = false;
			mTextRiddleColor.setTextColor (Color.RED);
		}

		mTextRiddleSize.setText (String.format (getResources ().getString (R.string.text_riddle_size), getResources ().getString (R.string.size)));
		if (mPuzzleColorDone && mSize == 2) {
			mPuzzleSizeDone = true;
			mTextRiddleSize.setTextColor (Color.GREEN);
		}

		else {
			mPuzzleSizeDone = false;
			mTextRiddleSize.setTextColor (Color.RED);
		}

		mTextRiddleName.setText (String.format (getResources ().getString (R.string.text_riddle_name), getResources ().getString(R.string.name)));
		if (mPuzzleSizeDone && mName.equals ("Mike")) {
			mPuzzleNameDone = true;
			mTextRiddleName.setTextColor (Color.GREEN);
		}

		else {
			mPuzzleNameDone = false;
			mTextRiddleName.setTextColor (Color.RED);
		}

		// Set up listeners
		mButtonColor.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), ColorActivity.class);
				i.putExtra (COLOR, mColor);
				i.putExtra (SIZE, mSize);
				i.putExtra (NAME, mName);
				i.putExtra (COLOR_RESULT, mPuzzleColorDone);
				i.putExtra (SIZE_RESULT, mPuzzleSizeDone);
				i.putExtra (NAME_RESULT, mPuzzleNameDone);
				startActivityForResult (i, COLOR_ACTIVITY);
			}
		});

		mButtonSize.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), SizeActivity.class);
				i.putExtra (COLOR, mColor);
				i.putExtra (SIZE, mSize);
				i.putExtra (NAME, mName);
				i.putExtra (COLOR_RESULT, mPuzzleColorDone);
				i.putExtra (SIZE_RESULT, mPuzzleSizeDone);
				i.putExtra (NAME_RESULT, mPuzzleNameDone);
				startActivityForResult (i, SIZE_ACTIVITY);
			}
		});

		mButtonName.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), NameActivity.class);
				i.putExtra (COLOR, mColor);
				i.putExtra (SIZE, mSize);
				i.putExtra (NAME, mName);
				i.putExtra (COLOR_RESULT, mPuzzleColorDone);
				i.putExtra (SIZE_RESULT, mPuzzleSizeDone);
				i.putExtra (NAME_RESULT, mPuzzleNameDone);
				startActivityForResult (i, NAME_ACTIVITY);
			}
		});

		mButtonSolve.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), EndActivity.class);
				i.putExtra (COLOR, mColor);
				i.putExtra (SIZE, mSize);
				i.putExtra (NAME, mName);
				i.putExtra (COLOR_RESULT, mPuzzleColorDone);
				i.putExtra (SIZE_RESULT, mPuzzleSizeDone);
				i.putExtra (NAME_RESULT, mPuzzleNameDone);
				startActivityForResult (i, END_ACTIVITY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Get any data from a previous intent
		if (requestCode == COLOR_ACTIVITY || requestCode == NAME_ACTIVITY || requestCode == SIZE_ACTIVITY) {
			Intent i = data;
			mPuzzleColorDone = i.getBooleanExtra (COLOR_RESULT, false);
			mPuzzleSizeDone = i.getBooleanExtra (SIZE_RESULT, false);
			mPuzzleNameDone = i.getBooleanExtra (NAME_RESULT, false);

			mSize = i.getIntExtra (SIZE, 0);
			mName = i.getStringExtra (NAME);
			mColor = i.getIntArrayExtra (COLOR);
			if (mColor == null) {
				mColor = new int[3];
			}

			// Set up the text areas
			mTextRiddleColor.setText (String.format (getResources ().getString (R.string.text_riddle_color), getResources ().getString (R.string.color)));
			if (mColor[0] == 0 && mColor[1] == 0 && mColor[2] == 255) {
				mPuzzleColorDone = true;
				mTextRiddleColor.setTextColor (Color.GREEN);
			}

			else {
				mPuzzleColorDone = false;
				mTextRiddleColor.setTextColor (Color.RED);
			}

			mTextRiddleSize.setText (String.format (getResources ().getString (R.string.text_riddle_size), getResources ().getString (R.string.size)));
			if (mPuzzleColorDone && mSize == 2) {
				mPuzzleSizeDone = true;
				mTextRiddleSize.setTextColor (Color.GREEN);
			}

			else {
				mPuzzleSizeDone = false;
				mTextRiddleSize.setTextColor (Color.RED);
			}

			mTextRiddleName.setText (String.format (getResources ().getString (R.string.text_riddle_name), getResources ().getString (R.string.name)));
			if (mName != null) {
				if (mPuzzleSizeDone && !mName.isEmpty () && mName.equals ("Mike")) {
					mPuzzleNameDone = true;
					mTextRiddleName.setTextColor (Color.GREEN);
				}

				else {
					mPuzzleNameDone = false;
					mTextRiddleName.setTextColor (Color.RED);
				}
			}

			else {
				mPuzzleNameDone = false;
				mTextRiddleName.setTextColor (Color.RED);
			}
		}

		else if (requestCode == END_ACTIVITY) {
			Intent i = data;
			mPuzzleColorDone = i.getBooleanExtra (COLOR_RESULT, false);
			mPuzzleSizeDone = i.getBooleanExtra (SIZE_RESULT, false);
			mPuzzleNameDone = i.getBooleanExtra (NAME_RESULT, false);

			mSize = i.getIntExtra (SIZE, 0);
			mName = i.getStringExtra (NAME);
			mColor = i.getIntArrayExtra (COLOR);
			if (mColor == null) {
				mColor = new int[3];
			}

			// Set up the text areas
			mTextRiddleColor.setText (String.format (getResources ().getString (R.string.text_riddle_color), getResources ().getString (R.string.color)));
			mPuzzleColorDone = false;
			mTextRiddleColor.setTextColor (Color.RED);

			mTextRiddleSize.setText (String.format (getResources ().getString (R.string.text_riddle_size), getResources ().getString (R.string.size)));
			mPuzzleSizeDone = false;
			mTextRiddleSize.setTextColor (Color.RED);

			mTextRiddleName.setText (String.format (getResources ().getString (R.string.text_riddle_name), getResources ().getString (R.string.name)));
			mPuzzleNameDone = false;
			mTextRiddleName.setTextColor (Color.RED);
		}
	}
}
