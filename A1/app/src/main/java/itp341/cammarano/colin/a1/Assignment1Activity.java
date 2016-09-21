package itp341.cammarano.colin.a1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Assignment1Activity extends AppCompatActivity {

	private int m_yesClicks;
	private int m_noClicks;
	private int m_turnipClicks;
    private Button m_yesButton;
    private Button m_noButton;
	private Button m_turnipButton;
    private ButtonListener m_buttonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_assignment1);

        m_buttonListener = new ButtonListener ();
		m_yesClicks = 0;
        m_yesButton = (Button)findViewById (R.id.button_yes);
        m_yesButton.setOnClickListener (m_buttonListener);

		m_noClicks = 0;
        m_noButton = (Button)findViewById (R.id.button_no);
        m_noButton.setOnClickListener (m_buttonListener);

		m_turnipClicks = 0;
		m_turnipButton = (Button)findViewById (R.id.button_turnip);
		m_turnipButton.setOnClickListener (m_buttonListener);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId () == R.id.button_yes) {
				m_yesClicks++;
				String text = getResources ().getString (R.string.toast_yes) + " ";
				Toast.makeText (Assignment1Activity.this, text + Integer.toString (m_yesClicks), Toast.LENGTH_SHORT).show ();
            }

            else if (view.getId () == R.id.button_no) {
				m_noClicks++;
				String text = getResources ().getString (R.string.toast_no) + " ";
				Toast.makeText (Assignment1Activity.this, text + Integer.toString (m_noClicks), Toast.LENGTH_SHORT).show ();
			}

			else if (view.getId () == R.id.button_turnip) {
				m_turnipClicks++;
				String text = getResources ().getString (R.string.toast_turnip) + " ";
				Toast.makeText (Assignment1Activity.this, text + Integer.toString (m_turnipClicks), Toast.LENGTH_SHORT).show ();
			}
        }
    }
}
