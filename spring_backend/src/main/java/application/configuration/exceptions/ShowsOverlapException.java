package application.configuration.exceptions;

import application.model.Shows;

public class ShowsOverlapException extends Exception {

	private static final long serialVersionUID = -6843219760757203689L;

	/**
	 * Shows that is overlapping with current shows
	 */
	private Shows shows;

	public ShowsOverlapException() {

	}

	public ShowsOverlapException(Shows shows) {
		super();
		this.shows = shows;
	}

	public ShowsOverlapException(String message) {
		super(message);
	}

	public ShowsOverlapException(String message, Shows shows) {
		super(message);
		this.shows = shows;
	}

	public Shows getShows() {
		return shows;
	}

	public void setShows(Shows shows) {
		this.shows = shows;
	}

}
