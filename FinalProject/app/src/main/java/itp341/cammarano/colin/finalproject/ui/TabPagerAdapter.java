package itp341.cammarano.colin.finalproject.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import itp341.cammarano.colin.finalproject.ui.fragments.tabs.ForumFragment;
import itp341.cammarano.colin.finalproject.ui.fragments.tabs.UpcomingFragment;

/**
 * Created by Colin Cammarano on 4/25/16.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private static final int TAB_COUNT = 2;
	private String[] mTitles;

	// Store each fragment as a member of the adapter
	private UpcomingFragment mUpcoming;
	private ForumFragment mForum;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public TabPagerAdapter (FragmentManager fm) {
		super (fm);

		mTitles = new String [] { "Upcoming", "Forum" };

		// Create fragment instances
		mUpcoming = UpcomingFragment.newInstance ();
		mForum = ForumFragment.newInstance ();
	}

	/******************************************************/
	// PUBLIC MEMBER METHODS
	/******************************************************/
	// Update the upcoming adapter with any changes from the model
	public void notifyUpcoming () {
		mUpcoming.notifyChanges ();
	}

	@Override
	public Fragment getItem (int position) {
		switch (position) {
			case 0:
				return mUpcoming;
			case 1:
				return mForum;
			default:
				return mUpcoming;
		}
	}

	@Override
	public int getCount () {
		return TAB_COUNT;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles[position];
	}
}
