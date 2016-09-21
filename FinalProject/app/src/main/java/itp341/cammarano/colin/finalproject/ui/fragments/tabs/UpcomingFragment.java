package itp341.cammarano.colin.finalproject.ui.fragments.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import itp341.cammarano.colin.finalproject.EntryDetailActivity;
import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;
import itp341.cammarano.colin.finalproject.ui.UpcomingAdapter;

public class UpcomingFragment extends Fragment {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private UpcomingAdapter mUpcomingAdapter;
	private TextView mLabelEmpty;
	private ListView mListView;

	/******************************************************/
	// FACTORY
	/******************************************************/
	public static UpcomingFragment newInstance () {
		UpcomingFragment fragment = new UpcomingFragment ();
		Bundle args = new Bundle ();
		fragment.setArguments (args);
		return fragment;
	}

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public UpcomingFragment () {}

	/******************************************************/
	// PUBLIC MEMBER METHODS
	/******************************************************/
	public void notifyChanges () {
		mUpcomingAdapter.notifyDataSetChanged ();
		if (mLabelEmpty == null) {
			mLabelEmpty = (TextView) getActivity ().findViewById (R.id.upcoming_label_empty);
		}

		if (mUpcomingAdapter.getCount () > 0) {
			mLabelEmpty.setVisibility (View.INVISIBLE);
		}
	}

	/******************************************************/
	// FRAGMENT METHODS
	/******************************************************/
	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate (R.layout.fragment_upcoming, container, false);

		mUpcomingAdapter = new UpcomingAdapter (getActivity ());
		mListView = (ListView) view.findViewById (R.id.upcoming_listView);
		mListView.setAdapter (mUpcomingAdapter);
		mListView.setOnItemClickListener (new AdapterView.OnItemClickListener () {
			@Override
			public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent (getActivity ().getApplicationContext (), EntryDetailActivity.class);
				intent.putExtra (EntryDetailActivity.KEY_UPCOMING, position);
				startActivity (intent);
			}
		});

		mLabelEmpty = (TextView) view.findViewById (R.id.upcoming_label_empty);
		if (mUpcomingAdapter.getCount () > 0) {
			mLabelEmpty.setVisibility (View.INVISIBLE);
		}

		if (!ApplicationManager.get (getActivity ().getApplicationContext ()).isLoggedIn ()) {
			String text = mLabelEmpty.getText ().toString ();
			mLabelEmpty.setText (text + " " + getResources ().getString (R.string.label_upcoming_login));
		}

		return view;
	}
}
