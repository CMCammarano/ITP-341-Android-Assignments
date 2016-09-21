package itp341.cammarano.colin.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import itp341.cammarano.colin.finalproject.core.Quote;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;

public class SplashScreenActivity extends AppCompatActivity {

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	private static final int SPLASH_DELAY = 3000;

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private TextView mLabelQuote;
	private TextView mLabelAuthor;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_splash_screen);

		// Setup quotes
		ApplicationManager manager = ApplicationManager.get (this.getApplicationContext ());
		Quote quote = manager.getRandomQuote();

		// Setup text fields
		mLabelQuote = (TextView)findViewById (R.id.splash_label_quote);
		mLabelQuote.setText (quote.getQuote ());

		mLabelAuthor = (TextView)findViewById (R.id.splash_label_author);
		mLabelAuthor.setText (quote.getAuthor ());

		// Load the user from the device's storage -- if there is a user, log them in!
		SharedPreferences preferences = getSharedPreferences (ApplicationManager.PREFERENCES, Context.MODE_PRIVATE);
		String email = preferences.getString (ApplicationManager.KEY_EMAIL, null);
		String password = preferences.getString (ApplicationManager.KEY_PASSWORD, null);
		if (email != null && password != null) {
			ApplicationManager.get (getApplicationContext ()).login (email, password, null);
		}

		// Fire the intent after a delay
		Handler handler = new Handler ();
		handler.postDelayed (new Runnable () {
			@Override
			public void run () {
				Intent intent = new Intent (getApplicationContext (), MainActivity.class);
				startActivity (intent);
				finish ();
			}
		}, SPLASH_DELAY);
	}
}
