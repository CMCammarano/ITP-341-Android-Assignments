package itp341.cammarano.colin.finalproject;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import itp341.cammarano.colin.finalproject.ui.fragments.AddBlockFragment;
import itp341.cammarano.colin.finalproject.ui.fragments.AddEventFragment;
import itp341.cammarano.colin.finalproject.ui.fragments.AddNotificationFragment;
import itp341.cammarano.colin.finalproject.ui.fragments.dialog.EntryDialogFragment;

public class AddActivity extends AppCompatActivity {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	public static final int OPTION_CREATE_EVENT = 1;
	public static final int OPTION_CREATE_BLOCK = 0;
	public static final int OPTION_CREATE_NOTIFICATION = 2;

	/******************************************************/
	// ACTIVITY METHODS
	/******************************************************/
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_add);

		Intent intent = getIntent ();
		if (intent != null) {
			int entry = intent.getIntExtra (EntryDialogFragment.EXTRA_SELECTION, 0);
			switch (entry) {
				case OPTION_CREATE_EVENT: {
					FragmentManager fragmentManager = getSupportFragmentManager ();
					Fragment fragment = new AddEventFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
					break;
				}

				case OPTION_CREATE_BLOCK: {
					FragmentManager fragmentManager = getSupportFragmentManager ();
					Fragment fragment = new AddBlockFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
					break;
				}

				case OPTION_CREATE_NOTIFICATION: {
					FragmentManager fragmentManager = getSupportFragmentManager ();
					Fragment fragment = new AddNotificationFragment ();
					FragmentTransaction transaction = fragmentManager.beginTransaction ();
					transaction.add (R.id.fragment_container, fragment);
					transaction.commit ();
					break;
				}
			}
		}
	}
}
