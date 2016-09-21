package itp341.cammarano.colin.a8;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Colin Cammarano on 4/3/16.
 */
public class NoteManager {

	private static NoteManager mSingleton;
	private static ArrayList<Note> mNotes;

	// CONSTRUCTOR
	private NoteManager () {
		mNotes = new ArrayList<Note> ();
		mNotes.add (new Note ("Note 1", "This is a simple note."));
		mNotes.add (new Note ("Note 2", "This is a second note with different text."));
	}

	// Singleton accessor
	public static NoteManager get () {

		if (mSingleton == null) {
			mSingleton = new NoteManager ();
		}
		return mSingleton;
	}

	// Accessors and mutators
	public ArrayList<Note> getNotes() { return mNotes; }
}
