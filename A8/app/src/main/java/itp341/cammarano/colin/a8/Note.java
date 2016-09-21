package itp341.cammarano.colin.a8;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Colin Cammarano on 4/3/16.
 */
public class Note {

	private String mTitle;
	private String mContent;
	private Date mDate;
	private DateFormat mFormat;

	public Note (String title, String content) {
		mTitle = title;
		mContent = content;
		mDate = new Date ();
		mFormat = new SimpleDateFormat ("MM/dd/yy");
	}

	public String getTitle () { return mTitle; }
	public void setTitle (String title) { mTitle = title; }
	public String getContent () { return mContent; }
	public void setContent (String content) { mContent = content; }
	public String getDate () { return mFormat.format (mDate); }
}
