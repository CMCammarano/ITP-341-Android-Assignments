package itp341.cammarano.colin.a5;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by cmcammarano on 2/28/16.
 */
public class UnitConvertFragment extends Fragment {

	private EditText mEditValue;
	private RadioGroup mRadioGroupFrom;
	private RadioGroup mRadioGroupTo;
	private Button mButtonCalculate;
	private TextView mLabelResult;

	private UnitTypeEnum mUnitTypeFrom = UnitTypeEnum.Centimeters;
	private UnitTypeEnum mUnitTypeTo = UnitTypeEnum.Centimeters;

	private float mFinalResult;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate (R.layout.frag_unit_convert_layout, container, false);
		mEditValue = (EditText)view.findViewById (R.id.edit_value_unit);
		mRadioGroupFrom = (RadioGroup)view.findViewById (R.id.rg_unit_from);
		mRadioGroupTo = (RadioGroup)view.findViewById (R.id.rg_unit_to);
		mButtonCalculate = (Button)view.findViewById (R.id.button_calculate_unit);
		mLabelResult = (TextView)view.findViewById (R.id.label_unit_result_value);

		// Set up primitives
		mFinalResult = 0;

		// Set up listeners
		mRadioGroupFrom.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
			@Override
			public void onCheckedChanged (RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_from_centimeters:
						mUnitTypeFrom = UnitTypeEnum.Centimeters;
						break;

					case R.id.rb_from_meters:
						mUnitTypeFrom = UnitTypeEnum.Meters;
						break;

					case R.id.rb_from_feet:
						mUnitTypeFrom = UnitTypeEnum.Feet;
						break;

					case R.id.rb_from_miles:
						mUnitTypeFrom = UnitTypeEnum.Miles;
						break;

					case R.id.rb_from_kilometers:
						mUnitTypeFrom = UnitTypeEnum.Kilometers;
						break;
				}
			}
		});

		mRadioGroupTo.setOnCheckedChangeListener (new RadioGroup.OnCheckedChangeListener () {
			@Override
			public void onCheckedChanged (RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.rb_to_centimeters:
						mUnitTypeTo = UnitTypeEnum.Centimeters;
						break;

					case R.id.rb_to_meters:
						mUnitTypeTo = UnitTypeEnum.Meters;
						break;

					case R.id.rb_to_feet:
						mUnitTypeTo = UnitTypeEnum.Feet;
						break;

					case R.id.rb_to_miles:
						mUnitTypeTo = UnitTypeEnum.Miles;
						break;

					case R.id.rb_to_kilometers:
						mUnitTypeTo = UnitTypeEnum.Kilometers;
						break;
				}
			}
		});

		mButtonCalculate.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				float multiplier = 0;
				if (mUnitTypeFrom == mUnitTypeTo) {
					multiplier = 1;
				}

				else {
					if (mUnitTypeFrom == UnitTypeEnum.Centimeters && mUnitTypeTo == UnitTypeEnum.Meters) {
						multiplier = 0.01f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Centimeters && mUnitTypeTo == UnitTypeEnum.Feet) {
						multiplier = 0.0328f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Centimeters && mUnitTypeTo == UnitTypeEnum.Miles) {
						multiplier = 6.21f * 0.000001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Centimeters && mUnitTypeTo == UnitTypeEnum.Kilometers) {
						multiplier = 0.00001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Meters && mUnitTypeTo == UnitTypeEnum.Centimeters) {
						multiplier = 100f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Meters && mUnitTypeTo == UnitTypeEnum.Feet) {
						multiplier = 3.2808f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Meters && mUnitTypeTo == UnitTypeEnum.Miles) {
						multiplier = 6.21f * 0.0001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Meters && mUnitTypeTo == UnitTypeEnum.Kilometers) {
						multiplier = 0.001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Feet && mUnitTypeTo == UnitTypeEnum.Centimeters) {
						multiplier = 30.48f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Feet && mUnitTypeTo == UnitTypeEnum.Meters) {
						multiplier = 0.3048f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Feet && mUnitTypeTo == UnitTypeEnum.Miles) {
						multiplier = 1.89f * 0.0001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Feet && mUnitTypeTo == UnitTypeEnum.Kilometers) {
						multiplier = 3.04f * 0.001f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Kilometers && mUnitTypeTo == UnitTypeEnum.Centimeters) {
						multiplier = 10000;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Kilometers && mUnitTypeTo == UnitTypeEnum.Meters) {
						multiplier = 1000;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Kilometers && mUnitTypeTo == UnitTypeEnum.Feet) {
						multiplier = 3280.84f;
					}

					else if (mUnitTypeFrom == UnitTypeEnum.Kilometers && mUnitTypeTo == UnitTypeEnum.Miles) {
						multiplier = 0.62137f;
					}
				}

				String temp = mEditValue.getText ().toString ();
				if (temp != null && !temp.isEmpty ()) {
					mFinalResult = multiplier * Float.valueOf (temp);
					mLabelResult.setText(String.format ("%e", mFinalResult));
				}
			}
		});

		mLabelResult.setText(String.format ("%e", mFinalResult));

		return view;
	}

	private enum UnitTypeEnum {
		Centimeters,
		Meters,
		Feet,
		Miles,
		Kilometers
	}
}
