package itp341.cammarano.colin.a5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by cmcammarano on 2/28/16.
 */
public class ExchangeRateFragment extends Fragment {

	private EditText mEditValue;
	private Spinner mFromSpinner;
	private Spinner mToSpinner;
	private Button mButtonCalculate;
	private TextView mLabelResult;

	private ExchangeEnum mExchangeFromType;
	private ExchangeEnum mExchangeToType;

	private float mFinalResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate (R.layout.frag_exchange_rate_layout, container, false);
		mEditValue = (EditText)view.findViewById (R.id.edit_money);
		mFromSpinner = (Spinner)view.findViewById (R.id.spinner_from_currency);
		mToSpinner = (Spinner)view.findViewById (R.id.spinner_to_currency);
		mButtonCalculate = (Button)view.findViewById (R.id.button_calculate_exchange);
		mLabelResult = (TextView)view.findViewById (R.id.label_exchange_result_value);

		// Set basic primitives
		mFinalResult = 0;

		mFromSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						mExchangeFromType = ExchangeEnum.USD;
						break;

					case 1:
						mExchangeFromType = ExchangeEnum.Yuan;
						break;

					case 2:
						mExchangeFromType = ExchangeEnum.Euro;
						break;
				}
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {

			}
		});

		mToSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						mExchangeToType = ExchangeEnum.USD;
						break;

					case 1:
						mExchangeToType = ExchangeEnum.Yuan;
						break;

					case 2:
						mExchangeToType = ExchangeEnum.Euro;
						break;
				}
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {

			}
		});

		mButtonCalculate.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				float exchange = 0;
				if (mExchangeToType == mExchangeFromType) {
					exchange = 1;
				}

				else {
					if (mExchangeFromType == ExchangeEnum.USD && mExchangeToType == ExchangeEnum.Euro) {
						exchange = 0.9f;
					}

					else if (mExchangeFromType == ExchangeEnum.USD && mExchangeToType == ExchangeEnum.Yuan) {
						exchange = 6.51f;
					}

					else if (mExchangeFromType == ExchangeEnum.Yuan && mExchangeToType == ExchangeEnum.USD) {
						exchange = 0.15f;
					}

					else if (mExchangeFromType == ExchangeEnum.Yuan && mExchangeToType == ExchangeEnum.Euro) {
						exchange = 0.14f;
					}

					else if (mExchangeFromType == ExchangeEnum.Euro && mExchangeToType == ExchangeEnum.Yuan) {
						exchange = 7.27f;
					}

					else if (mExchangeFromType == ExchangeEnum.USD && mExchangeToType == ExchangeEnum.Yuan) {
						exchange = 1.12f;
					}
				}

				String temp = mEditValue.getText ().toString ();
				if (temp != null && !temp.isEmpty ()) {
					mFinalResult = exchange * Float.valueOf (temp);
					mLabelResult.setText (String.format ("%.2f", mFinalResult));
				}
			}
		});

		mLabelResult.setText (String.format ("%.2f", mFinalResult));

		return view;
	}

	private enum ExchangeEnum {
		USD,
		Yuan,
		Euro
	}
}
