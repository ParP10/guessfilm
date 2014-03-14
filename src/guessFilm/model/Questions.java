package guessFilm.model;

import guessFilm.DataBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
/**
 * 
 * Store data about questions
 * 
 */

public class Questions {
	private int amountQuestions;
	private int amountAskedQuestions;
	private ArrayList<Question> listQuestions;

	/*public Questions() {
		initializeListQuestions();
		amountQuestions = listQuestions.size();
		amountAskedQuestions = 0;
	}*/

	public int getAmountAskedQuestions() {
		return amountAskedQuestions;
	}

	public void setAmountAskedQuestions(int amountAskedQuestions) {
		this.amountAskedQuestions = amountAskedQuestions;
	}

	public ArrayList<Question> getListQuestions() {
		return listQuestions;
	}

	public void setListQuestions(ArrayList<Question> listQuestions) {
		this.listQuestions = listQuestions;
	}
	
	public int getAmountQuestions() {
		return amountQuestions;
	}

	public void setAmountQuestions(int amountQuestions) {
		this.amountQuestions = amountQuestions;
	}
	
	public void initialize(ArrayList<Question> listQuestions) {
		this.listQuestions = listQuestions;
		this.amountQuestions = listQuestions.size();
		this.amountAskedQuestions = 0; 
	}


	/**
	 * Initialize list of questions (listQuestions). Select data from database
	 */
	/*private void initializeListQuestions() {
		listQuestions = new ArrayList<Question>();
		int index = 1;
		
		try {
			InputStream inputStream = new FileInputStream(new File("questions.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					listQuestions.add(new Question(index++, line));
					line = reader.readLine();
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/



	/**
	 * 
	 * @param idQuestion
	 *            Question's ID in database
	 * @return Question with ID = idQuestion
	 */
	public Question getQuestion(int idQuestion) {
		//TODO Optimize here
		return listQuestions.get(idQuestion);
	}

	/**
	 * Append new questions in database
	 */
	public void appendNewQuestions() {		
		//int index = amountQuestions + 1;
		DataBase dao = new DataBase();
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("newQuestions.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					
					Question newQuestion = new Question(line); 
					listQuestions.add(newQuestion);
					amountQuestions++;
					dao.addQuestion(newQuestion);
					line = reader.readLine();
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save new information in file. Old version
		/*
		try {
			OutputStream outputStream = new FileOutputStream(new File("questions.txt"));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			try {
				for (int i = 0; i < amountQuestions; i++) {
					writer.write(listQuestions.get(i).getQuestionName());
					writer.newLine();
				}
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}

	/**
	 * @return true, if there is a question, which hasn't been asked yet.
	 *         false, otherwise
	 */
	public boolean existsQuestion() {
		if (amountQuestions > amountAskedQuestions) {
			return true;
		}
		return false;
	}

	/**
	 * next question
	 */
	public Question getNextQuestion() {
		Question curQuestion = listQuestions.get(amountAskedQuestions);

		// TODO realize algorithm

		amountAskedQuestions++;
		//curQuestion.setUsed();
		return curQuestion;
	}

}