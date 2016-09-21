package itp341.cammarano.colin.a6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EndActivity extends AppCompatActivity {

	private TextView mTextMessage;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_end);

		mTextMessage = (TextView)findViewById (R.id.text_end);

		Intent i = getIntent ();
		boolean colorRight = i.getBooleanExtra (Assignment6Activity.COLOR_RESULT, false);
		boolean sizeRight = i.getBooleanExtra (Assignment6Activity.SIZE_RESULT, false);
		boolean nameRight = i.getBooleanExtra (Assignment6Activity.NAME_RESULT, false);

		if (colorRight && sizeRight && nameRight) {
			mTextMessage.setText (getResources ().getString (R.string.text_end_win));
		}

		else {
			mTextMessage.setText (getResources ().getString (R.string.text_end_lose));
		}
	}

	@Override
	public void onBackPressed () {
		Intent i = new Intent (getApplicationContext (), Assignment6Activity.class);
		setResult (AppCompatActivity.RESULT_CANCELED, i);
		finish ();
	}
}
