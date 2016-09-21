package itp341.cammarano.colin.finalproject.core;

/**
 * Created by cmcammarano on 5/7/16.
 */
public class Quote {

	/******************************************************/
	// PRIVATE MEMBER VARIABLES
	/******************************************************/
	private String mQuote;
	private String mAuthor;

	/******************************************************/
	// CONSTRUCTOR
	/******************************************************/
	public Quote (String quote, String author) {
		mQuote = quote;
		mAuthor = author;
	}

	/******************************************************/
	// ACCESSORS AND MUTATORS
	/******************************************************/
	public String getQuote () { return mQuote; }
	public void setQuote (String quote) { mQuote = quote; }

	public String getAuthor () { return mAuthor; }
	public void setAuthor (String author) { mAuthor = author; }
}
