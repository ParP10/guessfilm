package guessFilm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



/**
 * 
 * Store data about film
 *
 */

@Entity (name="films")
public class Film {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	public Film() {	
	}
	
	public Film(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Film(String name) {
		this.name = name;
	}
	
	public int getFilmId() {
		return id;
	}

	public void setFilmId(int id) {
		this.id = id;
	}

	public String getFilmName() {
		return name;
	}

	public void setFilmName(String name) {
		this.name = name;
	}

	public void appendNewFilm(int idFilm) {
		
	}

}
