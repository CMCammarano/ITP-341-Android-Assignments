package itp341.cammarano.colin.finalproject.ui.fragments.tabs;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import itp341.cammarano.colin.finalproject.R;
import itp341.cammarano.colin.finalproject.core.Quote;
import itp341.cammarano.colin.finalproject.singleton.ApplicationManager;

public class ForumFragment extends Fragment {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private EditText mQuoteField;
	private EditText mAuthorField;
	private Button mAddButton;
	private TextView mLabelQuote;
	private TextView mLabelAuthor;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public ForumFragment () {
		// Required empty public constructor
	}

	/******************************************************/
	// FACTORY
	/******************************************************/
	public static ForumFragment newInstance () {
		ForumFragment fragment = new ForumFragment ();
		Bundle args = new Bundle ();
		fragment.setArguments (args);
		return fragment;
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
		View view = inflater.inflate (R.layout.fragment_forum, container, false);

		// Set up the labels
		mLabelQuote = (TextView)view.findViewById (R.id.forum_label_quote);
		mLabelQuote.setVisibility (View.INVISIBLE);

		mLabelAuthor = (TextView)view.findViewById (R.id.forum_label_author);
		mLabelAuthor.setVisibility (View.INVISIBLE);

		// Set up the edit texts
		mQuoteField = (EditText)view.findViewById (R.id.forum_edit_quote);
		mAuthorField = (EditText)view.findViewById (R.id.forum_edit_author);

		// Set up the button!
		mAddButton = (Button)view.findViewById (R.id.forum_button_add);
		mAddButton.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {

				String quote = mQuoteField.getText ().toString ();
				String author = mAuthorField.getText ().toString ();

				// Otherwise, display an error message!
				if (quote == null || quote.isEmpty ()) {
					String error = getString (R.string.error_no_quote);
					Snackbar.make (mAddButton, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
				}

				if (author == null || author.isEmpty ()) {
					String error = getString (R.string.error_no_author);
					Snackbar.make (mAddButton, error, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();
				}

				// Check the info here--if it's good, add the quote to the database
				else {
					String success = getString (R.string.success_quote);
					Snackbar.make (mAddButton, success, Snackbar.LENGTH_LONG).setAction ("Action", null).show ();

					// Add the quote
					Quote q = new Quote (quote, author);
					ApplicationManager.get (getActivity ().getApplicationContext ()).addQuote (q);

					// Clear the text fields
					mQuoteField.setText (null);
					mAuthorField.setText (null);

					// Update the labels
					mLabelQuote.setVisibility (View.VISIBLE);
					mLabelQuote.setText (quote);

					mLabelAuthor.setVisibility (View.VISIBLE);
					mLabelAuthor.setText (author);
				}
			}
		});

		// If we are not logged in, disable the button
		ApplicationManager manager = ApplicationManager.get (getActivity ().getApplicationContext ());
		if (!manager.isLoggedIn ()) {
			mAddButton.setEnabled (false);
			mAddButton.setText (getString (R.string.button_forum_login));
		}

		return view;
	}
}
