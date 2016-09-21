package itp341.cammarano.colin.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;
import itp341.cammarano.colin.finalproject.ui.TabPagerAdapter;
import itp341.cammarano.colin.finalproject.ui.fragments.dialog.EntryDialogFragment;

public class MainActivity extends AppCompatActivity {

	/******************************************************/
	// CONSTANTS
	/******************************************************/
	public static final int ACTIVITY_ADD = 1;
	public static final int ACTIVITY_LOGIN = 2;
	public static final int ACTIVITY_REGISTER = 3;

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private TabPagerAdapter mTabPager;
	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private FloatingActionButton mButtonAdd;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_main);

		// Setup the ViewPager to use the TabPagerAdapter
		mTabPager = new TabPagerAdapter (getSupportFragmentManager ());
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter (mTabPager);

		// Link the TabLayout to the ViewPager
		mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
		mTabLayout.setupWithViewPager (mViewPager);

		// Setup the floating action button
		mButtonAdd = (FloatingActionButton) findViewById (R.id.fab_add);
		mButtonAdd.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View view) {
				EntryDialogFragment dialog = new EntryDialogFragment ();
				FragmentManager fm = getSupportFragmentManager ();
				dialog.show (fm, null);
			}
		});

		// Check to see if the current user is logged in. If they are not, disable the add button
		ApplicationManager manager = ApplicationManager.get (getApplicationContext ());
		mButtonAdd.setEnabled (manager.isLoggedIn ());
		if (!manager.isLoggedIn ()) {
			mButtonAdd.setVisibility (View.INVISIBLE);
		}

		else {
			mButtonAdd.setVisibility (View.VISIBLE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult (requestCode, resultCode, data);

		if (requestCode == ACTIVITY_ADD) {
			if (resultCode == RESULT_OK) {

				// Notify the upcoming fragment of changes
				mTabPager.notifyUpcoming ();

				String success = getResources ().getString (R.string.string_add_success);
				Snackbar.make (mButtonAdd, success, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
			}
		}

		else if (requestCode == ACTIVITY_LOGIN) {
			if (resultCode == RESULT_OK) {

				Intent intent = new Intent (getApplicationContext (), MainActivity.class);
				startActivity (intent);
				finish ();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate (R.menu.menu_main, menu);

		// If the user is currently logged in, remove the login option
		if (ApplicationManager.get (this).isLoggedIn ()) {
			MenuItem login = menu.findItem (R.id.action_login);
			login.setVisible (false);

			MenuItem username = menu.findItem (R.id.action_username);
			username.setVisible (true);
			username.setEnabled (false);
			username.setTitle (ApplicationManager.get (getApplicationContext ()).getActiveUser ().getName ());

			MenuItem logoff = menu.findItem (R.id.action_logoff);
			logoff.setVisible (true);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent intent;
		switch (item.getItemId()) {
			case R.id.action_login:
				intent = new Intent (getApplicationContext (), LoginActivity.class);
				startActivityForResult (intent, ACTIVITY_LOGIN);
				return true;

			case R.id.action_logoff:
				ApplicationManager.get (getApplicationContext ()).logoff ();
				intent = new Intent (getApplicationContext (), MainActivity.class);
				startActivity (intent);
				finish ();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
