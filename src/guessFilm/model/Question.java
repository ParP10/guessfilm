package guessFilm.model;

import guessFilm.DataBase;

/**
 * 
 * Store data about question
 *
 */
public class Question {

	private int id;
	private String name;
	private boolean used;
	
	public Question() {
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
	}

	/**
	 * @return String representation of question
	 */
	public String getQuestionName() {
		return name;
	}
	/**
	 * Append new Question into database
	 * @return
	 */
	public void appendNewQuestion(int idQuestion) {
		// TODO append new question into database
	}
	
	public boolean isUsed() {
		return used;
	}

	public int getIndex() {
		return id;
	}
	
	public void setUsed() {
		used = true;
	}

}
