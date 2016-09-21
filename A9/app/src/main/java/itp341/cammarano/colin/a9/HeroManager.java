package itp341.cammarano.colin.a9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Colin Cammarano on 4/17/16.
 */
public class HeroManager {

	/*************************************************/
	// PRIVATE MEMBER VARIABLES
	/*************************************************/
	private SQLiteDatabase mDatabase;

	/*************************************************/
	// CONSTRUCTORS
	/*************************************************/
	private HeroManager (Context context) {
		mInstance = null;
		mDatabase = new DbHelper (context).getWritableDatabase ();
	}

	/*************************************************/
	// PUBLIC MEMBER METHODS
	/*************************************************/
	public void addHero (Hero hero) {

		Log.v ("Power 1: ", hero.getPower1 ());
		Log.v ("Power 2: ", hero.getPower2 ());

		ContentValues cv = new ContentValues ();
		cv.put (DbSchema.TABLE_HEROES.KEY_NAME, hero.getName ());
		cv.put (DbSchema.TABLE_HEROES.KEY_POWER1, hero.getPower1 ());
		cv.put (DbSchema.TABLE_HEROES.KEY_POWER2, hero.getPower2 ());
		cv.put (DbSchema.TABLE_HEROES.KEY_NUM_WINS, hero.getNumWins ());
		cv.put (DbSchema.TABLE_HEROES.KEY_NUM_LOSSES, hero.getNumLosses ());
		cv.put (DbSchema.TABLE_HEROES.KEY_NUM_TIES, hero.getNumTies ());

		mDatabase.insert (DbSchema.TABLE_HEROES.NAME, null, cv);
	}

	public void addBattleResult (Hero hero, int result) {
		ContentValues cv = new ContentValues ();
		if (result == 1) {
			cv.put (DbSchema.TABLE_HEROES.KEY_NUM_WINS, hero.getNumWins () + 1);
		}

		else if (result == -1) {
			cv.put (DbSchema.TABLE_HEROES.KEY_NUM_WINS, hero.getNumLosses () + 1);
		}

		else {
			cv.put (DbSchema.TABLE_HEROES.KEY_NUM_WINS, hero.getNumTies () + 1);
		}

		mDatabase.update (DbSchema.TABLE_HEROES.NAME, cv, "_id=?", new String[] { Long.toString (hero.getID ()) });
	}

	public Cursor getUniquePowers () {
		Cursor cursor = mDatabase.query (true, DbSchema.TABLE_POWERS.NAME, null, null, null, DbSchema.TABLE_POWERS.KEY_OWN_POWER, null, null, null);
		return cursor;
	}

	public Cursor getRankings () {
		Cursor cursor = mDatabase.query (DbSchema.TABLE_HEROES.NAME, null, null, null, null, null, "num_wins desc");
		return cursor;
	}

	public Cursor getHeroes () {
		Cursor cursor = mDatabase.query (DbSchema.TABLE_HEROES.NAME, null, null, null, null, null, "name asc");
		return cursor;
	}

	public int getPowerResult (String power1, String power2) {
		int winner = 0;
		Cursor cursor = mDatabase.query (DbSchema.TABLE_POWERS.NAME, null, DbSchema.TABLE_POWERS.KEY_OWN_POWER + "=? and " + DbSchema.TABLE_POWERS.KEY_OPPOSING_POWER + "=?", new String[] { power1, power2 }, null, null, null);
		if (cursor.moveToFirst ()) {
			winner = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_POWERS.KEY_WINNING_POWER));
		}
		return winner;
	}

	/*************************************************/
	// SINGLETON
	/*************************************************/
	private static HeroManager mInstance;
	public static HeroManager get (Context context) {
		if (mInstance == null) {
			mInstance = new HeroManager (context);
		}

		return mInstance;
	}
}
