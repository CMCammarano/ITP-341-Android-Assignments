package itp341.cammarano.colin.finalproject;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * As soon as I attempted to test my ApplicationManager singleton, I hit a bad roadblock. See the top comment in that class for details.
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

	// Constructor
	public ApplicationTest () {
		super (Application.class);
	}
}