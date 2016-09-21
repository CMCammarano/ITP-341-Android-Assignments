package itp341.cammarano.colin.a5;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Assignment5Activity extends AppCompatActivity {

	private Button mButtonTip;
	private Button mButtonMetric;
	private Button mButtonMoney;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_assignment5);

		mButtonTip = (Button)findViewById (R.id.button_tip);
		mButtonMetric = (Button)findViewById (R.id.button_metric);
		mButtonMoney = (Button)findViewById (R.id.button_money);

		mButtonTip.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				FragmentManager fragmentManager = getSupportFragmentManager ();
				Fragment fragment = fragmentManager.findFragmentById (R.id.fragment_container);
				if (fragment == null) {
					fragment = new TipCalculatorFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
				}

				else {
					fragment = new TipCalculatorFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.replace (R.id.fragment_container, fragment);
					transaction.commit ();
				}
			}
		});

		mButtonMetric.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				FragmentManager fragmentManager = getSupportFragmentManager ();
				Fragment fragment = fragmentManager.findFragmentById (R.id.fragment_container);
				if (fragment == null) {
					fragment = new UnitConvertFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
				} else {
					fragment = new UnitConvertFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.replace (R.id.fragment_container, fragment);
					transaction.commit ();
				}
			}
		});

		mButtonMoney.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				FragmentManager fragmentManager = getSupportFragmentManager ();
				Fragment fragment = fragmentManager.findFragmentById (R.id.fragment_container);
				if (fragment == null) {
					fragment = new ExchangeRateFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
				}

				else {
					fragment = new ExchangeRateFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.replace (R.id.fragment_container, fragment);
					transaction.commit ();
				}
			}
		});
	}
}
