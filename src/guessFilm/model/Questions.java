package guessFilm.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

	public Questions() {
		initializeListQuestions();
		amountQuestions = listQuestions.size();
		amountAskedQuestions = 0;
	}

	/**
	 * Initialize list of questions (listQuestions). Select data from database
	 */
	private void initializeListQuestions() {
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
	}

	/**
	 * @return Amount questions in database
	 */
	public int getAmountQuestions() {
		return amountQuestions;
	}

	/**
	 * 
	 * @param idQuestion
	 *            Question's ID in database
	 * @return Question with ID = idQuestion
	 */
	public Question getQuestion(int idQuestion) {
		return listQuestions.get(idQuestion);
	}

	/**
	 * Append new questions in database
	 */
	public void appendNewQuestions() {		
		int index = amountQuestions + 1;
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("newQuestions.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					listQuestions.add(new Question(index++, line));
					amountQuestions++;
					line = reader.readLine();
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// save new information in file
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
		curQuestion.setUsed();
		return curQuestion;
	}

}