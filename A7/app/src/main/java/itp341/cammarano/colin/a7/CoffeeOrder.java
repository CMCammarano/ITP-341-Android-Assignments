package itp341.cammarano.colin.a7;

import java.io.Serializable;

/**
 * Created by cmcammarano on 3/15/16.
 */
public class CoffeeOrder implements Serializable {

	private String mBrew;
	private String mExtras;
	private SizeEnum mSize;
	private boolean mSugar;
	private boolean mMilk;

	public CoffeeOrder () {
		mBrew = "Kona";
		mExtras = "";
		mSize = SizeEnum.Tall;
		mSugar = false;
		mMilk = false;
	}

	// To String method
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();
		return sb.toString ();
	}

	// Accessors and mutators
	public String getBrew () { return mBrew; }
	public void setBrew (String brew) { mBrew = brew; }

	public String getExtras () { return mExtras; }
	public void setExtras (String extras) { mExtras = extras; }

	public SizeEnum getSize () { return mSize; }
	public void setSize (SizeEnum size) { mSize = size; }

	public boolean getSugar () { return mSugar; }
	public void setSugar (boolean sugar) { mSugar = sugar; }

	public boolean getMilk () { return mMilk; }
	public void setMilk (boolean milk) { mMilk = milk; }

	// Size enum
	public enum SizeEnum {
		Tall,
		Grande,
		Vente
	}
}
