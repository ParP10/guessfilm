package guessFilm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 
 * Store data about question
 *
 */

@Entity(name="questions")
public class Question {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String name;
	
	//private boolean used = false;
	
	/*public Question() {
		id = -1;
		used = false;
	}

	public Question(int idQuestion) {
		used = false;
		id = idQuestion;
		
		DataBase dataBase = new DataBase();
		name = dataBase.selectQuestionName(id);
	}
	
	public Question(int id, String name) {
		this.id = id;
		this.name = name;
	}*/
	
	public Question() {
	//	this.used = false;
	}
	
	public Question(String name) {
		this.name = name;
	}

	public int getQuestionId() {
		return id;
	}

	public void setQuestionId(int id) {
		this.id = id;
	}

	public String getQuestionName() {
		return name;
	}

	public void setQuestionName(String name) {
		this.name = name;
	}

	/*public void setQuestionUsed(boolean used) {
		this.used = used;
	}*/

	/**
	 * Append new Question into database
	 * @return
	 */
	/*public void appendNewQuestion(int idQuestion) {
		// TODO append new question into database
	}*/
	
	/*public boolean isUsed() {
		return used;
	}*/

	/*public void setUsed() {
		used = true;
	}*/

}
