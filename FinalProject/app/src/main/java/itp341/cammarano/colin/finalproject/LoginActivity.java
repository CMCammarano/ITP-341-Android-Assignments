package itp341.cammarano.colin.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;

public class LoginActivity extends AppCompatActivity {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private EditText mEditEmail;
	private EditText mEditPassword;
	private Button mButtonSubmit;
	private Button mButtonRegister;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_login);

		// Get EditText references
		mEditEmail = (EditText)findViewById (R.id.login_edit_email);
		mEditPassword = (EditText)findViewById (R.id.login_edit_password);

		mButtonSubmit = (Button)findViewById (R.id.login_button_submit);
		mButtonSubmit.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {

				String email = mEditEmail.getText ().toString ();
				if (email == null || email.isEmpty ()) {
					String error = getResources ().getString (R.string.error_field_required);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				String password = mEditPassword.getText ().toString ();
				if (password == null || password.isEmpty ()) {
					String error = getResources ().getString (R.string.error_field_required);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				ApplicationManager.get (getApplicationContext ()).login (email, password, new OnLoginListener () {
					@Override
					public void onSuccess () {
						Intent intent = new Intent (getApplicationContext (), MainActivity.class);
						setResult (Activity.RESULT_OK, intent);
						finish ();
					}

					@Override
					public void onFailure () {
						String error = getResources ().getString (R.string.string_login_error);
						Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					}
				});
			}
		});

		mButtonRegister = (Button)findViewById (R.id.login_button_register);
		mButtonRegister.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getApplicationContext (), RegisterActivity.class);
				startActivityForResult (intent, MainActivity.ACTIVITY_REGISTER);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult (requestCode, resultCode, data);

		if (requestCode == MainActivity.ACTIVITY_REGISTER) {
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent (getApplicationContext (), MainActivity.class);
				setResult (RESULT_OK, intent);
				finish ();
			}

			else if (resultCode == RESULT_CANCELED) {
				Intent intent = new Intent (getApplicationContext (), MainActivity.class);
				setResult (RESULT_CANCELED, intent);
				finish ();
			}
		}
	}

	// Listener to send to singleton
	public abstract class OnLoginListener {
		public abstract void onSuccess ();
		public abstract void onFailure ();
	}
}
