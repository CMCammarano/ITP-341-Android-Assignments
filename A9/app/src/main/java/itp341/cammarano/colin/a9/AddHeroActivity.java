package itp341.cammarano.colin.a9;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddHeroActivity extends AppCompatActivity {

	private EditText mEditTextName;
	private Spinner mSpinnerPower1;
	private Spinner mSpinnerPower2;
	private Button mButtonAdd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_add_hero);

		mEditTextName = (EditText)findViewById (R.id.editText_name);
		mSpinnerPower1 = (Spinner)findViewById (R.id.spinner_power1);
		mSpinnerPower2 = (Spinner)findViewById (R.id.spinner_power2);

		// Set up spinners
		setupSpinners ();

		// Set up button
		mButtonAdd = (Button)findViewById (R.id.button_save);
		mButtonAdd.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {

				if (mEditTextName.getText ().toString () != null && !mEditTextName.getText ().toString ().isEmpty ()) {

					String power1 = ((TextView)mSpinnerPower1.findViewById (R.id.label_spinner_text)).getText ().toString ();
					String power2 = ((TextView)mSpinnerPower2.findViewById (R.id.label_spinner_text)).getText ().toString ();
					Hero hero = new Hero (mEditTextName.getText ().toString (), power1, power2);
					HeroManager.get (getApplicationContext ()).addHero (hero);

					// Jump back to the main activity
					Intent intent = new Intent (getApplicationContext (), Assignment9Activity.class);
					startActivity (intent);
				}
			}
		});
	}

	private void setupSpinners () {
		Cursor cursor = HeroManager.get (getApplicationContext ()).getUniquePowers ();
		String[] columns = { DbSchema.TABLE_POWERS.KEY_OWN_POWER };
		int[] ids = { R.id.label_spinner_text };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter (getApplicationContext (), R.layout.spinner_view, cursor, columns, ids, 0);
		mSpinnerPower1.setAdapter (adapter);
		mSpinnerPower2.setAdapter (adapter);
	}
}
