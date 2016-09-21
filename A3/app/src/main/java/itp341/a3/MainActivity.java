package itp341.a3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final double COST_MULTIPLIER = 1.2;
    private final double numShoeMultiplier = 0.5;
    private final double numTreatMultiplier = 1.5;
    private final double numHumanMultiplier = 10.0;
    private final double numHydrantMultiplier = 100.0;

    private TextView textViewDoge;
    private TextView textViewHydrant;
    private TextView textViewHuman;
    private TextView textViewShoe;
    private TextView textViewTreat;

    private ImageButton imageButtonMocha;

    private Button buttonHydrant;
    private Button buttonHuman;
    private Button buttonShoe;
    private Button buttonTreat;
    private Button buttonDetails;

    private long numDoge = 0;
    private long numHydrant = 0;
    private long numHuman = 0;
    private long numShoe = 0;
    private long numTreat = 0;

    private long numHydrantCost = 10000;
    private long numHumanCost = 1000;
    private long numShoeCost = 10;
    private long numTreatCost = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        Log.d(TAG, "onCreate Start");

        textViewDoge = (TextView) findViewById(R.id.textViewDogeCount);
        textViewHydrant = (TextView) findViewById(R.id.textViewHydrantCount);
        textViewHuman = (TextView) findViewById(R.id.textViewHumanCount);
        textViewShoe = (TextView) findViewById(R.id.textViewShoesCount);
        textViewTreat = (TextView) findViewById(R.id.textViewTreatsCount);

        Log.v(TAG, "Finished linking textviews");

        imageButtonMocha = (ImageButton) findViewById(R.id.imageButtonMocha);
        imageButtonMocha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double addAmount = 1 + numShoe * numShoeMultiplier + numTreat * numTreatMultiplier
                        + numHuman * numHumanMultiplier + numHydrant * numHydrantMultiplier;

                Log.v(TAG, "Adding " + numShoe * numShoeMultiplier + " from shoes");
                Log.v(TAG, "Adding " + numTreat * numTreatMultiplier + " from treats");
                Log.v(TAG, "Adding " + numHuman * numHumanMultiplier + " from humans" );
                Log.v(TAG, "Adding " + numHydrant * numHydrantMultiplier + " from hydrants");

                Log.d(TAG, "Adding: " + addAmount + " to existing: " + numDoge);

                numDoge += addAmount;
                updateDogeCount();
                updateBuyButtons();
            }
        });

        DogeClickerButtonListener listener = new DogeClickerButtonListener();

        buttonHydrant = (Button) findViewById(R.id.buttonBuyHydrant);
        buttonHuman = (Button) findViewById(R.id.buttonBuyHuman);
        buttonShoe = (Button) findViewById(R.id.buttonBuyShoe);
        buttonTreat = (Button) findViewById(R.id.buttonBuyTreat);
        buttonDetails = (Button) findViewById (R.id.button_details);

        Log.v(TAG, "Finished linking buttons");

        buttonHydrant.setOnClickListener(listener);
        buttonHuman.setOnClickListener(listener);
        buttonShoe.setOnClickListener(listener);
        buttonTreat.setOnClickListener(listener);
		buttonDetails.setOnClickListener (listener);

        Log.v(TAG, "Finished button listeners");

        String output = getResources().getString(R.string.text_cost) + " ";

        buttonHydrant.setText(output + numHydrantCost);
        buttonHuman.setText(output + numHumanCost);
        buttonShoe.setText(output + numShoeCost);
        buttonTreat.setText(output + numTreatCost);

        updateBuyButtons();
        updateDogeCount();

        Log.d(TAG, "onCreate end");
    }

	@Override
    protected void onSaveInstanceState (Bundle savedInstanceState) {
		savedInstanceState.putLong ("numDoge", numDoge);
		savedInstanceState.putLong ("numHydrant", numHydrant);
		savedInstanceState.putLong ("numHuman", numHuman);
		savedInstanceState.putLong ("numShoe", numShoe);
		savedInstanceState.putLong ("numTreat", numTreat);
		savedInstanceState.putLong ("numHydrantCost", numHydrantCost);
		savedInstanceState.putLong ("numHumanCost", numHumanCost);
		savedInstanceState.putLong ("numShoeCost", numShoeCost);
		savedInstanceState.putLong ("numTreatCost", numTreatCost);
	}

	public void onRestoreInstanceState(Bundle savedInstanceState) {
		numDoge = savedInstanceState.getLong ("numDoge");
		numHydrant = savedInstanceState.getLong ("numHydrant");
		numHuman = savedInstanceState.getLong ("numHuman");
		numShoe = savedInstanceState.getLong ("numShoe");
		numTreat = savedInstanceState.getLong ("numTreat");

		numHydrantCost = savedInstanceState.getLong ("numHydrantCost");
		numHumanCost = savedInstanceState.getLong ("numHumanCost");
		numShoeCost = savedInstanceState.getLong ("numShoeCost");
		numTreatCost = savedInstanceState.getLong ("numTreatCost");

		textViewDoge.setText(numDoge + "");
		textViewHuman.setText(numHuman + "");
		textViewHydrant.setText(numHydrant + "");
		textViewShoe.setText(numShoe + "");
		textViewTreat.setText(numTreat + "");

		buttonHuman.setText(getResources().getString(R.string.text_cost) + " " + numHumanCost);
		buttonHydrant.setText(getResources().getString(R.string.text_cost) + " " + numHydrantCost);
		buttonShoe.setText(getResources().getString(R.string.text_cost) + " " + numShoeCost);
		buttonTreat.setText(getResources().getString(R.string.text_cost) + " " + numTreatCost);
	}

    private void updateDogeCount(){
        Log.v(TAG, "New numDoge: " + numDoge);
        textViewDoge.setText(numDoge + "");
    }

    private void updateBuyButtons(){
        Log.v(TAG, "buttonHydrant enabled: " + (numDoge >= numHydrantCost));
        buttonHydrant.setEnabled(numDoge >= numHydrantCost);
        Log.v(TAG, "buttonHuman enabled: " + (numDoge >= numHumanCost));
        buttonHuman.setEnabled(numDoge >= numHumanCost);
        Log.v(TAG, "buttonShoe enabled: " + (numDoge >= numShoeCost));
        buttonShoe.setEnabled(numDoge >= numShoeCost);
        Log.v(TAG, "buttonTreat enabled: " + (numDoge >= numTreatCost));
        buttonTreat.setEnabled(numDoge >= numTreatCost);
    }

    private class DogeClickerButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d(TAG, "button clicked with id: " + v.getId());
            switch(v.getId()){
                case R.id.buttonBuyHuman:
                    Log.v(TAG, "Buying human for " + numHumanCost + " with " + numDoge + " in bank");
                    numDoge -= numHumanCost;
                    ++numHuman;
                    numHumanCost *= COST_MULTIPLIER;
                    Log.v(TAG, "Humans now cost " + numHumanCost);
                    ((Button) v).setText(getResources().getString(R.string.text_cost) + " " + numHumanCost);
                    textViewHuman.setText(numHuman + "");
                    break;

                case R.id.buttonBuyHydrant:
                    Log.v(TAG, "Buying hydrant for " + numHydrantCost + " with " + numDoge + " in bank");
                    numDoge -= numHydrantCost;
                    ++numHydrant;
                    numHydrantCost *= COST_MULTIPLIER;
                    Log.v(TAG, "Hydrants now cost " + numHydrantCost);
                    ((Button) v).setText(getResources().getString(R.string.text_cost) + " " + numHydrantCost);
                    textViewHydrant.setText(numHydrant + "");
                    break;

                case R.id.buttonBuyShoe:
                    Log.v(TAG, "Buying shoe for " + numShoeCost + " with " + numDoge + " in bank");
                    numDoge -= numShoeCost;
                    ++numShoe;
                    numShoeCost *= COST_MULTIPLIER;
                    Log.v(TAG, "Shoes now cost " + numShoeCost);
                    ((Button) v).setText(getResources().getString(R.string.text_cost) + " " + numShoeCost);
                    textViewShoe.setText(numShoe + "");
                    break;

                case R.id.buttonBuyTreat:
                    Log.v(TAG, "Buying treat for " + numTreatCost + " with " + numDoge + " in bank");
                    numDoge -= numTreatCost;
                    ++numTreat;
                    numTreatCost *= COST_MULTIPLIER;
                    Log.v (TAG, "Treats now cost " + numTreatCost);
                    ((Button) v).setText (getResources ().getString (R.string.text_cost) + " " + numTreatCost);
                    textViewTreat.setText(numTreat + "");
                    break;

				case R.id.button_details: {
					StringBuilder output = new StringBuilder ();
					output.append (String.format (getApplicationContext ().getString (R.string.text_message), numHydrant, getApplicationContext ().getString (R.string.text_fireHydrant), 1 + numHydrant * numHydrantMultiplier));
					output.append ("\n");
					output.append (String.format (getApplicationContext ().getString (R.string.text_message), numHuman, getApplicationContext ().getString (R.string.text_human), 1 + numHuman * numHumanMultiplier));
					output.append ("\n");
					output.append (String.format (getApplicationContext ().getString (R.string.text_message), numShoe, getApplicationContext ().getString (R.string.text_shoes), 1 + numShoe * numShoeMultiplier));
					output.append ("\n");
					output.append (String.format (getApplicationContext ().getString (R.string.text_message), numTreat, getApplicationContext ().getString (R.string.text_treats), 1 + numTreat * numTreatMultiplier));
					output.append ("\n");

					Toast.makeText (getApplicationContext (), output.toString (), Toast.LENGTH_SHORT).show ();
					break;
				}
            }

            updateBuyButtons();
            updateDogeCount ();
        }
    }
}
