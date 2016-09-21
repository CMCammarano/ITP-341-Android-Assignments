package itp341.cammarano.colin.a2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Assignment2Activity extends AppCompatActivity {

	private int m_americanFoodCount;
	private int m_chineseFoodCount;
	private int m_indianFoodCount;
	private int m_italianFoodCount;
	private int m_mideastFoodCount;
	private int m_portugueseFoodCount;
	private ImageView m_americanFood;
	private ImageView m_chineseFood;
	private ImageView m_indianFood;
	private ImageView m_italianFood;
	private ImageView m_mideastFood;
	private ImageView m_portugueseFood;
	private ImageClickListener m_listener;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate (savedInstanceState);
		setContentView (R.layout.assignment2activity);

		m_listener = new ImageClickListener ();

		m_americanFoodCount = 0;
		m_americanFood = (ImageView)findViewById (R.id.img_american);
		m_americanFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_american)).into (m_americanFood);

		m_chineseFoodCount = 0;
		m_chineseFood = (ImageView)findViewById (R.id.img_chinese);
		m_chineseFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_chinese)).into (m_chineseFood);

		m_indianFoodCount = 0;
		m_indianFood = (ImageView)findViewById (R.id.img_indian);
		m_indianFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_indian)).into (m_indianFood);

		m_italianFoodCount = 0;
		m_italianFood = (ImageView)findViewById (R.id.img_italian);
		m_italianFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_italian)).into (m_italianFood);

		m_mideastFoodCount = 0;
		m_mideastFood = (ImageView)findViewById (R.id.img_mideast);
		m_mideastFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_mideast)).into (m_mideastFood);

		m_portugueseFoodCount = 0;
		m_portugueseFood = (ImageView)findViewById (R.id.img_portuguese);
		m_portugueseFood.setOnClickListener (m_listener);
		Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_portuguese)).into (m_portugueseFood);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putInt ("american", m_americanFoodCount);
		savedInstanceState.putInt ("chinese", m_chineseFoodCount);
		savedInstanceState.putInt ("indian", m_indianFoodCount);
		savedInstanceState.putInt ("italian", m_italianFoodCount);
		savedInstanceState.putInt ("mideast", m_mideastFoodCount);
		savedInstanceState.putInt ("portug", m_portugueseFoodCount);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		m_americanFoodCount = savedInstanceState.getInt ("american");
		m_chineseFoodCount = savedInstanceState.getInt ("chinese");
		m_indianFoodCount = savedInstanceState.getInt ("indian");
		m_italianFoodCount = savedInstanceState.getInt ("italian");
		m_mideastFoodCount = savedInstanceState.getInt ("mideast");
		m_portugueseFoodCount = savedInstanceState.getInt ("portug");

		if (m_americanFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_american_clicked)).into (m_americanFood);
		}

		if (m_chineseFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_chinese_clicked)).into (m_chineseFood);
		}

		if (m_indianFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_indian_clicked)).into (m_indianFood);
		}

		if (m_italianFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_italian_clicked)).into (m_italianFood);
		}

		if (m_mideastFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_mideast_clicked)).into (m_mideastFood);
		}

		if (m_portugueseFoodCount > 0) {
			Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_portuguese_clicked)).into (m_portugueseFood);
		}
	}

	private class ImageClickListener implements View.OnClickListener {

		public void onClick (View view) {
			if (view.getId () == R.id.img_american) {
				m_americanFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_american), m_americanFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_american_clicked)).into (m_americanFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}

			if (view.getId () == R.id.img_chinese) {
				m_chineseFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_chinese), m_chineseFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_chinese_clicked)).into (m_chineseFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}

			if (view.getId () == R.id.img_indian) {
				m_indianFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_indian), m_indianFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_indian_clicked)).into (m_indianFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}

			if (view.getId () == R.id.img_italian) {
				m_italianFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_italian), m_italianFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_italian_clicked)).into (m_italianFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}

			if (view.getId () == R.id.img_mideast) {
				m_mideastFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_mideast), m_mideastFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_mideast_clicked)).into (m_mideastFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}

			if (view.getId () == R.id.img_portuguese) {
				m_portugueseFoodCount++;
				String message = String.format (getResources ().getString (R.string.msg_click), getResources ().getString (R.string.str_portuguese), m_portugueseFoodCount);
				Glide.with (getApplicationContext ()).load (getResources ().getString (R.string.url_portuguese_clicked)).into (m_portugueseFood);
				Toast.makeText (Assignment2Activity.this, message, Toast.LENGTH_SHORT).show ();
			}
		}
	}
}
