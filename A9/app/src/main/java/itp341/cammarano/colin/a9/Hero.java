package itp341.cammarano.colin.a9;

/**
 * Created by cmcammarano on 4/17/16.
 */
public class Hero {

	/*************************************************/
	// PRIVATE MEMBER VARIABLES
	/*************************************************/
	private long _id;
	private String name;
	private String power1;
	private String power2;
	private int health;
	private int numWins;
	private int numLosses;
	private int numTies;

	/*************************************************/
	// CONSTRUCTORS
	/*************************************************/
	public Hero (String name, String power1, String power2) {
		this._id = 0;
		this.name = name;
		this.power1 = power1;
		this.power2 = power2;
		this.health = 5;
		this.numWins = 0;
		this.numLosses = 0;
		this.numTies = 0;
	}

	public Hero (long id, String name, String power1, String power2, int numWins, int numLosses, int numTies) {
		this._id = id;
		this.name = name;
		this.power1 = power1;
		this.power2 = power2;
		this.health = 5;
		this.numWins = numWins;
		this.numLosses = numLosses;
		this.numTies = numTies;
	}

	/*************************************************/
	// PUBLIC MEMBER METHODS
	/*************************************************/
	@Override
	public String toString () {
		StringBuilder sb = new StringBuilder ();
		return sb.toString ();
	}

	public boolean isAlive () {
		return health >= 0;
	}

	/*************************************************/
	// ACCESSORS AND MUTATORS
	/*************************************************/
	public long getID () { return _id; }
	public void setID (long id) { this._id = id; }

	public String getName () { return name; }
	public void setString (String name) { this.name = name; }

	public String getPower1 () { return power1; }
	public void setPower1 (String power1) { this.power1 = power1; }

	public String getPower2 () { return power2; }
	public void setPower2 (String power2) { this.power2 = power2; }

	public int getHealth () { return health; }
	public void setHealth (int health) { this.health = health; }

	public int getNumWins () { return numWins; }
	public void setNumWins (int numWins) { this.numWins = numWins; }

	public int getNumLosses () { return numLosses; }
	public void setNumLosses (int numLosses) { this.numLosses = numLosses; }

	public int getNumTies () { return numTies; }
	public void setNumTies (int numTies) { this.numTies = numTies; }
}
