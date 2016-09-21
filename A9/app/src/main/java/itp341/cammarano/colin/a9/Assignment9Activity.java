package itp341.cammarano.colin.a9;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class Assignment9Activity extends AppCompatActivity {

	public static final int ADD_HERO_ACTIVITY = 1;

	private ListView mRankings;
	private Button mButtonAddHero;
	private Button mButtonHeroFighting;
	private Spinner mSpinnerHero1;
	private Spinner mSpinnerHero2;
	private ScrollView mResults;
	private TextView mResultText;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_assignment9);

		// Set up UI elements
		mRankings = (ListView)findViewById (R.id.listView_rankings);
		mButtonAddHero = (Button)findViewById (R.id.button_add_hero);
		mButtonHeroFighting = (Button)findViewById (R.id.button_fight);
		mSpinnerHero1 = (Spinner)findViewById (R.id.spinner_hero1);
		mSpinnerHero2 = (Spinner)findViewById (R.id.spinner_hero2);
		mResults = (ScrollView)findViewById (R.id.scrollView_results);
		mResultText = (TextView)findViewById (R.id.label_results_text);

		// Set up listeners
		mButtonAddHero.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				Intent intent = new Intent (getApplicationContext (), AddHeroActivity.class);
				startActivity (intent);
			}
		});

		mButtonHeroFighting.setOnClickListener (new View.OnClickListener () {
			@Override
			public void onClick (View v) {
				fight ();
			}
		});

		// Set up rankings
		setupRankings ();

		// Set up spinners
		setupSpinners ();
	}

	// Grab data from the database and set up the list view
	private void setupRankings () {
		Cursor cursor = HeroManager.get (getApplicationContext ()).getRankings ();
		String[] columns = { DbSchema.TABLE_HEROES.KEY_NAME, DbSchema.TABLE_HEROES.KEY_POWER1, DbSchema.TABLE_HEROES.KEY_POWER2 };
		int[] ids = { R.id.label_list_name, R.id.label_list_power1, R.id.label_list_power2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter (getApplicationContext (), R.layout.hero_list_view, cursor, columns, ids, 0);

		mRankings.setAdapter (adapter);
	}

	// Grab data from the database and set up the hero spinners
	private void setupSpinners () {
		Cursor cursor = HeroManager.get (getApplicationContext ()).getHeroes ();
		String[] columns = { DbSchema.TABLE_HEROES.KEY_NAME };
		int[] ids = { R.id.label_spinner_text };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter (getApplicationContext (), R.layout.spinner_view, cursor, columns, ids, 0);
		mSpinnerHero1.setAdapter (adapter);
		mSpinnerHero2.setAdapter (adapter);
	}

	// Conduct five battles, then determine the winner
	private void fight () {
		Hero hero1 = null;
		Hero hero2 = null;

		Cursor cursor = HeroManager.get (getApplicationContext ()).getHeroes ();
		while (cursor.moveToNext ()) {
			String name = cursor.getString (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NAME));

			String name1 = ((TextView)mSpinnerHero1.findViewById (R.id.label_spinner_text)).getText ().toString ();
			if (name.equals (name1)) {

				long id = cursor.getLong (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_ID));
				String power1 = cursor.getString (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_POWER1));
				String power2 = cursor.getString (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_POWER2));
				int wins = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_WINS));
				int losses = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_LOSSES));
				int ties = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_TIES));
				hero1 = new Hero (id, name, power1, power2, wins, losses, ties);
			}

			String name2 = ((TextView)mSpinnerHero2.findViewById (R.id.label_spinner_text)).getText ().toString ();
			if (name.equals (name2)) {

				long id = cursor.getLong (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_ID));
				String power1 = cursor.getString (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_POWER1));
				String power2 = cursor.getString (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_POWER2));
				int wins = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_WINS));
				int losses = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_LOSSES));
				int ties = cursor.getInt (cursor.getColumnIndex (DbSchema.TABLE_HEROES.KEY_NUM_TIES));
				hero2 = new Hero (id, name, power1, power2, wins, losses, ties);
			}
		}

		// Create the string builder to speed up writing to the results
		StringBuilder sb = new StringBuilder ();
		sb.append (hero1.getName ());
		sb.append (" vs. ");
		sb.append (hero2.getName ());
		sb.append ("\n");
		sb.append ("\n");

		// Refresh the results text
		mResultText.setText ("");
		mResultText.setText (sb.toString ());

		int round = 1;
		while (hero1.getHealth () > 0 || hero2.getHealth () > 0) {
			Random random = new Random ();

			// Choose hero1's power
			String hero1Power = null;
			int power = random.nextInt () % 2;
			if (power == 0) {
				hero1Power = hero1.getPower1 ();
			}

			else {
				hero1Power = hero1.getPower2 ();
			}

			String hero2Power = null;
			power = random.nextInt () % 2;
			if (power == 0) {
				hero2Power = hero2.getPower1 ();
			}

			else {
				hero2Power = hero2.getPower2 ();
			}

			sb.append ("======== Round ");
			sb.append (round);
			sb.append (" ========");
			sb.append ("\n");
			sb.append (hero1.getName () + " uses " + hero1Power + "!\n");
			sb.append (hero2.getName () + " uses " + hero2Power + "!\n");

			int result = HeroManager.get (getApplicationContext ()).getPowerResult (hero1Power, hero2Power);
			if (result > 0) {
				hero2.setHealth (hero2.getHealth () - 1);
				sb.append (hero1.getName () + " wins the battle with ");
				sb.append (hero1Power);
				sb.append ("\n");
				sb.append ("\n");
			}

			else if (result < 0) {
				hero1.setHealth (hero1.getHealth () - 1);
				sb.append (hero2.getName () + " wins the battle with ");
				sb.append (hero2Power);
				sb.append ("\n");
				sb.append ("\n");
			}

			else {
				hero2.setHealth (hero2.getHealth () - 1);
				hero1.setHealth (hero1.getHealth () - 1);

				sb.append ("The battle was a tie!");
				sb.append ("\n");
				sb.append ("\n");
			}

			mResultText.setText (sb.toString ());
			round++;
		}

		if (hero1.getHealth () == 0 && hero2.getHealth () == 0) {
			HeroManager.get (getApplicationContext ()).addBattleResult (hero1, 0);
			HeroManager.get (getApplicationContext ()).addBattleResult (hero2, 0);

			sb.append ("The battle was a draw!");
		}

		else if (hero1.getHealth () == 0) {
			HeroManager.get (getApplicationContext ()).addBattleResult (hero1, -1);
			HeroManager.get (getApplicationContext ()).addBattleResult (hero2, 1);

			sb.append (hero2.getName () + " wins the battle!");
		}

		else if (hero2.getHealth () == 0) {
			HeroManager.get (getApplicationContext ()).addBattleResult (hero1, 1);
			HeroManager.get (getApplicationContext ()).addBattleResult (hero2, -1);

			sb.append (hero1.getName () + " wins the battle!");
		}

		sb.append ("\n");
		sb.append ("\n");
		mResultText.setText (sb.toString ());
	}
}
