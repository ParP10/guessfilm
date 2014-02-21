package guessFilm.model;

import guessFilm.DataBase;


/**
 * 
 * Store data about film
 *
 */
public class Film {
	
	private int id;
	private String name;
	
	public Film(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Film() {
	
	}
	
	
	/**
	 * @return String representation of film
	 */
	public String getFilmName() {
		return name;
	}
	
	/**
	 * Add new Film into database
	 * @param idFilm
	 */
	public void appendNewFilm(int idFilm) {
		// TODO add new film in database
	}

}
