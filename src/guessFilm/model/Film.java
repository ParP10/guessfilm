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
	
	@Column(name="count")
	private long count;
	
	public Film() {	
		id = 0;
		name = "";
		count = 0;
	}
	
	public Film(int id, String name) {
		this.id = id;
		this.name = name;
		this.count = 0;
	}
	
	public Film(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void appendNewFilm(int idFilm) {
		
	}

}
