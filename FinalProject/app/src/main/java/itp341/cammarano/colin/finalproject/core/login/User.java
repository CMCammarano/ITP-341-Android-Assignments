package itp341.cammarano.colin.finalproject.core.login;

/**
 * Created by cmcammarano on 4/26/16.
 */
public class User {

	private String mUserID;
	private String mName;
	private String mUsername;
	private String mEmail;
	private String mPassword;

	public User (String userID, String name, String username, String email, String password) {
		mUserID = userID;
		mName = name;
		mUsername = username;
		mEmail = email;
		mPassword = password;
	}

	public String getUserID () { return mUserID; }
	public String getName () { return mName; }
	public String getUsername () { return mUsername; }
	public String getEmail () { return mEmail; }
	public String getPassword () { return mPassword; }
}
