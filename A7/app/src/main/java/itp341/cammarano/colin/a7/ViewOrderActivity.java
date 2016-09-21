package itp341.cammarano.colin.a7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewOrderActivity extends AppCompatActivity {

	private Button mButtonConfirm;
	private Button mButtonCancel;

	private TextView mTextBrew;
	private TextView mTextSize;
	private TextView mTextAddons;
	private TextView mTextExtras;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_view_order);

		mButtonConfirm = (Button)findViewById (R.id.button_confirm);
		mButtonCancel = (Button)findViewById (R.id.button_cancel);

		mTextBrew = (TextView)findViewById (R.id.text_confirm_brew);
		mTextSize = (TextView)findViewById (R.id.text_confirm_size);
		mTextAddons = (TextView)findViewById (R.id.text_confirm_addons);
		mTextExtras = (TextView)findViewById (R.id.text_confirm_extras);

		// Get data from the previous intent
		Intent i = getIntent ();
		CoffeeOrder order = (CoffeeOrder)i.getSerializableExtra (Assignment7Activity.ORDER_EXTRA);

		if (order != null) {
			// Set up the text views
			mTextBrew.setText (String.format (getResources ().getString (R.string.text_confirm_brew), order.getBrew ()));
			mTextSize.setText (String.format (getResources ().getString (R.string.text_confirm_size), order.getSize ().toString ()));
			mTextExtras.setText (String.format (getResources ().getString (R.string.text_confirm_extras), order.getExtras ()));

			String addons;
			if (order.getMilk () && order.getSugar ()) {
				addons = "milk and sugar";
			}

			else if (order.getMilk () && !order.getSugar ()) {
				addons = "milk";
			}

			else if (!order.getMilk () && order.getSugar ()) {
				addons = "sugar";
			}

			else {
				addons = "N/A";
			}
			mTextAddons.setText (String.format (getResources ().getString (R.string.text_confirm_addons), addons));
		}

		// Set up listeners
		mButtonConfirm.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), Assignment7Activity.class);
				setResult (AppCompatActivity.RESULT_OK, i);
				finish ();
			}
		});

		mButtonCancel.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent i = new Intent (getApplicationContext (), Assignment7Activity.class);
				setResult (AppCompatActivity.RESULT_CANCELED, i);
				finish ();
			}
		});
	}
}
