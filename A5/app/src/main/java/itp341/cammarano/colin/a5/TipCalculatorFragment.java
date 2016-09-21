package itp341.cammarano.colin.a5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by cmcammarano on 2/28/16.
 */
public class TipCalculatorFragment extends Fragment {

	private EditText mTextBill;
	private SeekBar mSeekBarTip;
	private Spinner mSpinnerSplit;
	private TextView mLabelTip;
	private TextView mLabelPercent;
	private TextView mLabelTotal;
	private TextView mLabelPerPerson;
	private TextView mLabelTotalPerPerson;

	private float mBill = 0;
	private float mTip = 0;
	private float mTotal = 0;
	private SplitTypeEnum mSplit = SplitTypeEnum.None;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate (R.layout.frag_tip_calc_layout, container, false);

		mTextBill = (EditText)view.findViewById (R.id.text_bill);
		mSeekBarTip = (SeekBar)view.findViewById (R.id.seekBar_percentage);
		mSpinnerSplit = (Spinner)view.findViewById (R.id.spinner_split);
		mLabelTip = (TextView)view.findViewById (R.id.label_tip_amount);
		mLabelPercent = (TextView)view.findViewById (R.id.label_percentage);
		mLabelTotal = (TextView)view.findViewById (R.id.label_total_amount);
		mLabelPerPerson = (TextView)view.findViewById (R.id.label_split);
		mLabelTotalPerPerson = (TextView)view.findViewById (R.id.label_perperson);

		// Set our initial values
		mLabelTip.setText ("$" + mTip);
		mLabelTotal.setText ("$" + mTotal);
		mLabelTotalPerPerson.setText ("$" + mTotal);
		mLabelPercent.setText ("" + mSeekBarTip.getProgress () + "%");

		// Hide the per person stuff until it's changed
		mLabelPerPerson.setVisibility (View.GONE);
		mLabelTotalPerPerson.setVisibility (View.GONE);

		// Set up our listeners
		mSeekBarTip.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
			@Override
			public void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser) {
				displayOutput ();
			}

			@Override
			public void onStartTrackingTouch (SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch (SeekBar seekBar) {

			}
		});

		mSpinnerSplit.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				int val = (int) parent.getItemIdAtPosition (position);
				switch (val) {
					case 0:
						mSplit = SplitTypeEnum.None;
						break;

					case 1:
						mSplit = SplitTypeEnum.TwoWays;
						break;

					case 2:
						mSplit = SplitTypeEnum.ThreeWays;
						break;

					case 3:
						mSplit = SplitTypeEnum.FourWays;
						break;
				}

				displayOutput ();
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {

			}
		});

		mTextBill.setOnEditorActionListener (new TextView.OnEditorActionListener () {
			@Override
			public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {
				displayOutput ();
				return false;
			}
		});

		return view;
	}

	public void displayOutput () {

		// Get the bill value
		if (!mTextBill.getText ().toString ().isEmpty ()) {
			mBill = Float.valueOf (mTextBill.getText ().toString ());
		}

		else {
			mBill = 0;
		}

		// Calculate tip and total
		mTip = mBill * mSeekBarTip.getProgress () * 0.01f;
		mTotal = mBill + mTip;

		// Set our initial values
		mLabelPercent.setText ("" + mSeekBarTip.getProgress () + "%");
		mLabelTip.setText ("$" + String.format ("%.2f", mTip));
		mLabelTotal.setText ("$" + String.format ("%.2f", mTotal));

		switch (mSplit) {
			case None:
				mLabelPerPerson.setVisibility (View.GONE);
				mLabelTotalPerPerson.setVisibility (View.GONE);
				break;

			case TwoWays:
				mLabelTotalPerPerson.setText ("$" + String.format ("%.2f", (mTotal / 2.0)));
				mLabelPerPerson.setVisibility (View.VISIBLE);
				mLabelTotalPerPerson.setVisibility (View.VISIBLE);
				break;

			case ThreeWays:
				mLabelTotalPerPerson.setText ("$" + String.format ("%.2f", (mTotal / 3.0)));
				mLabelPerPerson.setVisibility (View.VISIBLE);
				mLabelTotalPerPerson.setVisibility (View.VISIBLE);
				break;

			case FourWays:
				mLabelTotalPerPerson.setText ("$" + String.format ("%.2f", (mTotal / 4.0)));
				mLabelPerPerson.setVisibility (View.VISIBLE);
				mLabelTotalPerPerson.setVisibility (View.VISIBLE);
				break;
		}
	}

	private enum SplitTypeEnum {
		None,
		TwoWays,
		ThreeWays,
		FourWays
	}
}
