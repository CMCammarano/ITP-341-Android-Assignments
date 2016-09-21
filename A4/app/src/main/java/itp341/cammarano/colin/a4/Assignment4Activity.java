package itp341.cammarano.colin.a4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class Assignment4Activity extends AppCompatActivity {

	private EditText m_textBill;
	private SeekBar m_seekBarTip;
	private Spinner m_spinnerSplit;
	private TextView m_labelTip;
	private TextView m_labelPercent;
	private TextView m_labelTotal;
	private TextView m_labelPerPerson;
	private TextView m_labelTotalPerPerson;

	private float m_bill = 0;
	private float m_tip = 0;
	private float m_total = 0;
	private SplitTypeEnum m_split = SplitTypeEnum.None;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.assignment4_activity);

		m_textBill = (EditText)findViewById (R.id.text_bill);
		m_seekBarTip = (SeekBar)findViewById (R.id.seekBar_percentage);
		m_spinnerSplit = (Spinner)findViewById (R.id.spinner_split);
		m_labelTip = (TextView)findViewById (R.id.label_tip_amount);
		m_labelPercent = (TextView)findViewById (R.id.label_percentage);
		m_labelTotal = (TextView)findViewById (R.id.label_total_amount);
		m_labelPerPerson = (TextView)findViewById (R.id.label_split);
		m_labelTotalPerPerson = (TextView)findViewById (R.id.label_perperson);

		// Set our initial values
		m_labelTip.setText ("$" + m_tip);
		m_labelTotal.setText ("$" + m_total);
		m_labelTotalPerPerson.setText ("$" + m_total);
		m_labelPercent.setText ("" + m_seekBarTip.getProgress ()  + "%");

		// Hide the per person stuff until it's changed
		m_labelPerPerson.setVisibility (View.GONE);
		m_labelTotalPerPerson.setVisibility (View.GONE);

		// Set up our listeners
		m_seekBarTip.setOnSeekBarChangeListener (new SeekBar.OnSeekBarChangeListener () {
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

		m_spinnerSplit.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				int val = (int) parent.getItemIdAtPosition (position);
				switch (val) {
					case 0:
						m_split = SplitTypeEnum.None;
						break;

					case 1:
						m_split = SplitTypeEnum.TwoWays;
						break;

					case 2:
						m_split = SplitTypeEnum.ThreeWays;
						break;

					case 3:
						m_split = SplitTypeEnum.FourWays;
						break;
				}

				displayOutput ();
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {

			}
		});

		m_textBill.setOnEditorActionListener (new TextView.OnEditorActionListener () {
			@Override
			public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {
				displayOutput ();
				return false;
			}
		});
	}

	public void displayOutput () {

		// Get the bill value
		if (!m_textBill.getText ().toString ().isEmpty ()) {
			m_bill = Float.valueOf (m_textBill.getText ().toString ());
		}

		else {
			m_bill = 0;
		}

		// Calculate tip and total
		m_tip = m_bill * m_seekBarTip.getProgress () * 0.01f;
		m_total = m_bill + m_tip;

		// Set our initial values
		m_labelPercent.setText ("" + m_seekBarTip.getProgress ()  + "%");
		m_labelTip.setText ("$" + String.format ("%.2f", m_tip));
		m_labelTotal.setText ("$" + String.format ("%.2f", m_total));

		switch (m_split) {
			case None:
				m_labelPerPerson.setVisibility (View.GONE);
				m_labelTotalPerPerson.setVisibility (View.GONE);
				break;

			case TwoWays:
				m_labelTotalPerPerson.setText ("$" + String.format ("%.2f", (m_total / 2.0)));
				m_labelPerPerson.setVisibility (View.VISIBLE);
				m_labelTotalPerPerson.setVisibility (View.VISIBLE);
				break;

			case ThreeWays:
				m_labelTotalPerPerson.setText ("$" + String.format ("%.2f", (m_total / 3.0)));
				m_labelPerPerson.setVisibility (View.VISIBLE);
				m_labelTotalPerPerson.setVisibility (View.VISIBLE);
				break;

			case FourWays:
				m_labelTotalPerPerson.setText ("$" + String.format ("%.2f", (m_total / 4.0)));
				m_labelPerPerson.setVisibility (View.VISIBLE);
				m_labelTotalPerPerson.setVisibility (View.VISIBLE);
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
