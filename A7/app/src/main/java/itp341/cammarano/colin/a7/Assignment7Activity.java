package itp341.cammarano.colin.a7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class Assignment7Activity extends AppCompatActivity {

	public static final String PREFERENCES = "itp341.cammarano.colin.A7.preferences";
	public static final String PREFERENCES_BREW = "itp341.cammarano.colin.A7.brew";
	public static final String PREFERENCES_SIZE = "itp341.cammarano.colin.A7.size";
	public static final String PREFERENCES_MILK = "itp341.cammarano.colin.A7.milk";
	public static final String PREFERENCES_SUGAR = "itp341.cammarano.colin.A7.sugar";
	public static final String PREFERENCES_EXTRAS = "itp341.cammarano.colin.A7.extras";
	public static final String ORDER_EXTRA = "itp341.cammarano.colin.A7.ORDER";

	public static final int ACTIVITY_MAIN = 0;
	public static final int ACTIVITY_VIEW = 1;

	private Button mButtonLoad;
	private Button mButtonSave;
	private Button mButtonOrder;
	private Button mButtonClear;

	private RadioGroup mRadioGroupSize;
	private Spinner mSpinnerBrew;
	private Switch mSwitchSugar;
	private CheckBox mCheckboxMilk;
	private EditText mEditExtras;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_assignment7);

		// Get references to all UI elements
		mButtonLoad = (Button)findViewById (R.id.button_load);
		mButtonSave = (Button)findViewById (R.id.button_save);
		mButtonOrder = (Button)findViewById (R.id.button_order);
		mButtonClear = (Button)findViewById (R.id.button_clear);

		mRadioGroupSize = (RadioGroup)findViewById (R.id.rg_size);
		mSpinnerBrew = (Spinner)findViewById (R.id.spinner_brew);
		mSwitchSugar = (Switch)findViewById (R.id.switch_sugar);
		mCheckboxMilk = (CheckBox)findViewById (R.id.checkBox_milk);
		mEditExtras = (EditText)findViewById (R.id.edit_extras);

		// Set up button listeners
		mButtonLoad.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				SharedPreferences sharedpreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
				mSpinnerBrew.setSelection (sharedpreferences.getInt (PREFERENCES_BREW, 0));
				mRadioGroupSize.check (sharedpreferences.getInt (PREFERENCES_SIZE, R.id.radio_small));
				mSwitchSugar.setChecked (sharedpreferences.getBoolean (PREFERENCES_SUGAR, false));
				mCheckboxMilk.setChecked (sharedpreferences.getBoolean (PREFERENCES_MILK, false));
				mEditExtras.setText (sharedpreferences.getString (PREFERENCES_EXTRAS, ""));

				Toast.makeText (getApplicationContext (), getResources ().getText (R.string.toast_loaded), Toast.LENGTH_SHORT).show ();
			}
		});

		mButtonSave.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				SharedPreferences sharedpreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedpreferences.edit();
				editor.putInt(PREFERENCES_BREW, mSpinnerBrew.getSelectedItemPosition ());
				editor.putInt (PREFERENCES_SIZE, mRadioGroupSize.getCheckedRadioButtonId ());
				editor.putBoolean (PREFERENCES_MILK, mCheckboxMilk.isChecked ());
				editor.putBoolean (PREFERENCES_SUGAR, mSwitchSugar.isChecked ());
				editor.putString (PREFERENCES_EXTRAS, mEditExtras.getText ().toString ());
				editor.commit ();

				Toast.makeText (getApplicationContext (), getResources ().getText (R.string.toast_saved), Toast.LENGTH_SHORT).show ();
			}
		});

		mButtonOrder.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), ViewOrderActivity.class);

				CoffeeOrder order = makeOrder ();
				i.putExtra (ORDER_EXTRA, order);

				startActivityForResult (i, ACTIVITY_VIEW);
			}
		});

		mButtonClear.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				resetOrder ();
			}
		});
	}

	private void resetOrder () {
		mRadioGroupSize.clearCheck ();
		mSpinnerBrew.setSelection (0);
		mSwitchSugar.setChecked (false);
		mCheckboxMilk.setChecked (false);
		mEditExtras.setText ("");
	}

	private CoffeeOrder makeOrder () {
		CoffeeOrder order = new CoffeeOrder ();

		// Get the brew
		if (!mSpinnerBrew.getSelectedItem ().toString ().isEmpty ()) {
			order.setBrew (mSpinnerBrew.getSelectedItem ().toString ());
		}

		// Get the size
		switch (mRadioGroupSize.getCheckedRadioButtonId ()) {
			case R.id.radio_small: {
				order.setSize (CoffeeOrder.SizeEnum.Tall);
				break;
			}

			case R.id.radio_medium: {
				order.setSize (CoffeeOrder.SizeEnum.Grande);
				break;
			}

			case R.id.radio_large: {
				order.setSize (CoffeeOrder.SizeEnum.Vente);
				break;
			}
		}

		// Get sugar
		order.setSugar (mSwitchSugar.isChecked ());

		// Get milk
		order.setMilk (mCheckboxMilk.isChecked ());

		// Get extras
		if (!mEditExtras.getText ().toString ().isEmpty ()) {
			order.setExtras (mEditExtras.getText ().toString ());
		}

		return order;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult (requestCode, resultCode, data);

		if (requestCode == ACTIVITY_VIEW && resultCode == AppCompatActivity.RESULT_OK) {
			Toast.makeText (getApplicationContext (), getResources ().getText (R.string.toast_success), Toast.LENGTH_SHORT).show ();
			resetOrder ();
		}

		else {
			Toast.makeText (getApplicationContext (), getResources ().getText (R.string.toast_failure), Toast.LENGTH_SHORT).show ();
			resetOrder ();
		}
	}
}
