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

public class RegisterActivity extends AppCompatActivity {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private EditText mEditName;
	private EditText mEditUsername;
	private EditText mEditEmail1;
	private EditText mEditEmail2;
	private EditText mEditPassword1;
	private EditText mEditPassword2;
	private Button mButtonSubmit;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_register);

		// Set up edit text
		mEditName = (EditText)findViewById (R.id.register_edit_name);
		mEditUsername = (EditText)findViewById (R.id.register_edit_username);
		mEditEmail1 = (EditText)findViewById (R.id.register_edit_email1);
		mEditEmail2 = (EditText)findViewById (R.id.register_edit_email2);
		mEditPassword1 = (EditText)findViewById (R.id.register_edit_pwd1);
		mEditPassword2 = (EditText)findViewById (R.id.register_edit_pwd2);

		// Set up button to submit registration information
		mButtonSubmit = (Button)findViewById (R.id.register_button_submit);
		mButtonSubmit.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				String name = mEditName.getText ().toString ();
				String username = mEditUsername.getText ().toString ();
				String email1 = mEditEmail1.getText ().toString ();
				String email2 = mEditEmail2.getText ().toString ();
				String password1 = mEditPassword1.getText ().toString ();
				String password2 = mEditPassword2.getText ().toString ();

				// Intermediary variables to help determine errors
				String error = getResources ().getString (R.string.error_authenticating);

				// Test each string to make sure that they have been filled out
				if (name == null || name.isEmpty ()) {
					error = getResources ().getString (R.string.error_field_required);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				if (email1 == null || email1.isEmpty () || email2 == null || email2.isEmpty ()) {
					error = getResources ().getString (R.string.error_field_required);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				if (password1 == null || password1.isEmpty () || password2 == null || password2.isEmpty ()) {
					error = getResources ().getString (R.string.error_field_required);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// Make sure email addresses match
				if (!email1.equals (email2)) {
					error = getResources ().getString (R.string.error_email_no_match);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// Make sure passwords match
				if (!password1.equals (password2)) {
					error = getResources ().getString (R.string.error_pwd_no_match);
					Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					return;
				}

				// If we reach this point, we can attempt to add the data to the database
				ApplicationManager.get (getApplicationContext ()).register (name, username, email1, password1, new OnRegisterListener () {
					@Override
					public void onSuccess () {
						Intent intent = new Intent (getApplicationContext (), LoginActivity.class);
						setResult (Activity.RESULT_OK, intent);
						finish ();
					}

					@Override
					public void onFailure () {
						String error = getResources ().getString (R.string.error_register_failure);
						Snackbar.make (mButtonSubmit, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
					}
				});
			}
		});
	}

	// Listener to send to singleton
	public abstract class OnRegisterListener {
		public abstract void onSuccess ();
		public abstract void onFailure ();
	}
}
