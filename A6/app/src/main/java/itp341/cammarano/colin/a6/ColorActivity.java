package itp341.cammarano.colin.a6;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ColorActivity extends AppCompatActivity {

	private SeekBar mSeekBarRed;
	private SeekBar mSeekBarGreen;
	private SeekBar mSeekBarBlue;

	private TextView mTextRed;
	private TextView mTextGreen;
	private TextView mTextBlue;
	private TextView mColorView;

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
		setContentView (R.layout.activity_color);

		// Set up seek bars and views
		mSeekBarRed = (SeekBar)findViewById (R.id.seek_r);
		mSeekBarGreen = (SeekBar)findViewById (R.id.seek_g);
		mSeekBarBlue = (SeekBar)findViewById (R.id.seek_b);

		mTextRed = (TextView)findViewById (R.id.text_r);
		mTextGreen = (TextView)findViewById (R.id.text_g);
		mTextBlue = (TextView)findViewById (R.id.text_b);

		mColorView = (TextView)findViewById (R.id.view_color);
		mButtonConfirm = (Button)findViewById (R.id.button_color_done);

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

		// Set UI to default
		mSeekBarRed.setProgress (mColor[0]);
		mSeekBarGreen.setProgress (mColor[1]);
		mSeekBarBlue.setProgress (mColor[2]);
		mColorView.setBackgroundColor (Color.rgb (mColor[0], mColor[1], mColor[2]));

		mTextRed.setText (String.format (getResources ().getString (R.string.text_r), mColor[0]));
		mTextGreen.setText (String.format (getResources ().getString (R.string.text_g), mColor[1]));
		mTextBlue.setText (String.format (getResources ().getString (R.string.text_b), mColor[2]));

		// Set listeners
		mSeekBarRed.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
			@Override
			public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
				mTextRed.setText (String.format (getResources ().getString (R.string.text_r), progress));
				mColor[0] = progress;
				mColorView.setBackgroundColor (Color.rgb (mColor[0], mColor[1], mColor[2]));
			}

			@Override
			public void onStartTrackingTouch (SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch (SeekBar seekBar) {

			}
		});

		mSeekBarGreen.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
			@Override
			public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
				mTextGreen.setText (String.format (getResources ().getString (R.string.text_g), progress));
				mColor[1] = progress;
				mColorView.setBackgroundColor (Color.rgb (mColor[0], mColor[1], mColor[2]));
			}

			@Override
			public void onStartTrackingTouch (SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch (SeekBar seekBar) {

			}
		});

		mSeekBarBlue.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
			@Override
			public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
				mTextBlue.setText (String.format (getResources ().getString (R.string.text_b), progress));
				mColor[2] = progress;
				mColorView.setBackgroundColor (Color.rgb (mColor[0], mColor[1], mColor[2]));
			}

			@Override
			public void onStartTrackingTouch (SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch (SeekBar seekBar) {

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
