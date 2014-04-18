package guessFilm.model;

import guessFilm.DataBase;
import guessFilm.GuessFilm.AnswerOnQuestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.lang.Math;

/**
 * 
 * Store data about questions
 * 
 */

public class Questions {
	private int amountQuestions;
	private int amountAskedQuestions;
	private ArrayList<Question> listQuestions;
	private BitSet askedQuestions;
	
	private Films films;
	private Question currentQuestion = new Question();
	//entropy H(question, answer)
	private double entropy[];
	
	private ArrayList<Question> posQuestions = new ArrayList<Question>();
	private ArrayList<Question> negQuestions = new ArrayList<Question>();
	
	private DataBase db = new DataBase();

	public Questions(Films films) {
		this.films = films;
	}

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
		this.askedQuestions = new BitSet(amountQuestions);
		
		//this.currentQuestion = null;
		this.entropy = new double[this.amountQuestions];
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
		return listQuestions.get(idQuestion - 1);
	}

	/**
	 * Append new questions in database
	 */
	public void appendNewQuestions() {		
		//int index = amountQuestions + 1;
		DataBase dao = new DataBase();
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("data/newQuestions.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					
					Question newQuestion = new Question(line); 
					listQuestions.add(newQuestion);
					amountQuestions++;
					int newId = dao.addQuestion(newQuestion);
					
					for (int i = 0; i < films.getAmountFilms(); i++) {
						PositiveAnswers positiveAnswers = new PositiveAnswers();
						positiveAnswers.setFilmId(i + 1);
						positiveAnswers.setQuestionId(newId);
						positiveAnswers.setCount(0);
						dao.addPositiveAnswers(positiveAnswers);
						
						NegativeAnswers negativeAnswers = new NegativeAnswers();
						negativeAnswers.setFilmId(i + 1);
						negativeAnswers.setQuestionId(newId);
						negativeAnswers.setCount(0);
						dao.addNegativeAnswers(negativeAnswers);
					}
					
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
	public Question getNextQuestion(AnswerOnQuestion answer) {
		
		if(!existsQuestion()) {
			return null;
		}
		//Question curQuestion = listQuestions.get(amountAskedQuestions);
		
		//update probability of film for asked questions
		if (answer == null) {
			/*if (currentQuestion != null) {
				throw new IllegalArgumentException("answer is null");
			}*/
			for (int i = 0; i < films.getAmountFilms(); i++) {
				films.setProb(i, films.getFilm(i + 1).getCount() + 1);
			}
		} else {
			switch(answer) {
				case DO_NOT_KNOW:
					// do nothing
					break;
				case YES:
					posQuestions.add(currentQuestion);
					for (int i = 0; i < films.getAmountFilms(); i++) {
						PositiveAnswers positiveAnswers = db.findPositiveAnswers(i + 1, currentQuestion.getId());
						NegativeAnswers negativeAnswers = db.findNegativeAnswers(i + 1, currentQuestion.getId());
						films.setProb(i, films.getProb(i) * (positiveAnswers.getCount() + 1)/ (positiveAnswers.getCount() + negativeAnswers.getCount() + 2));
					}
					break;
				case NO:
					negQuestions.add(currentQuestion);
					for (int i = 0; i < films.getAmountFilms(); i++) {
						PositiveAnswers positiveAnswers = db.findPositiveAnswers(i + 1, currentQuestion.getId());
						NegativeAnswers negativeAnswers = db.findNegativeAnswers(i + 1, currentQuestion.getId());
						films.setProb(i, films.getProb(i) * (negativeAnswers.getCount() + 1)/ (positiveAnswers.getCount() + negativeAnswers.getCount() + 2));
					}
					break;
				default:
					break;
			}
		}
		// Compute entropy
		// If user doesn't know answer, find next question with old data.
		if (answer != AnswerOnQuestion.DO_NOT_KNOW) {
			for (int q = 0; q < amountQuestions; q++) {
				// if question q was asked yet
				if (askedQuestions.get(q)) {
					continue;
				}
				double probYes = 0.0;
				double probNo = 0.0;
				double conditionalEntropyYes = 0.0;
				double conditionalEntropyNo = 0.0;
				for (int f = 0; f < films.getAmountFilms(); f++) {
					PositiveAnswers positiveAnswers = db.findPositiveAnswers(f + 1, q + 1);
					NegativeAnswers negativeAnswers = db.findNegativeAnswers(f + 1, q + 1);
					double posProb = (positiveAnswers.getCount() + 1.0) / (positiveAnswers.getCount() + negativeAnswers.getCount() + 2.0);
					double negProb = (negativeAnswers.getCount() + 1.0) / (positiveAnswers.getCount() + negativeAnswers.getCount() + 2.0);
					probYes += films.getProb(f) * posProb;
					probNo += films.getProb(f) * negProb;
					conditionalEntropyYes -= Math.log(films.getProb(f) * posProb) * posProb * films.getProb(f);
					conditionalEntropyYes -= Math.log(films.getProb(f) * negProb) * negProb * films.getProb(f);
				}
				entropy[q] = conditionalEntropyYes * probYes + conditionalEntropyNo * probNo; 
			}
		}
		
		// Find next questions. It is question with min entropy
		Question nextQuestion = null;
		for (int q = 0; q < amountQuestions; q++) {
			if (askedQuestions.get(q)) {
				continue;
			}
			if (nextQuestion == null || entropy[q] > entropy[nextQuestion.getId() - 1]) {
				nextQuestion = getQuestion(q + 1);
			}
		}
		
		amountAskedQuestions++;
		askedQuestions.set(nextQuestion.getId() - 1);
		currentQuestion = nextQuestion;
		return nextQuestion;
	}

	public int appendNewQuestion(String name) {
		DataBase dao = new DataBase();
		Question newQuestion = new Question(name);
		listQuestions.add(newQuestion);
		amountQuestions++;
		return dao.addQuestion(newQuestion); // TODO Check
	}

	public void updatePosQuestions(Film resultFilm) {
		for (int i = 0; i < posQuestions.size(); i++) {
			PositiveAnswers positiveAnswers = db.findPositiveAnswers(resultFilm.getId(), posQuestions.get(0).getId());
			positiveAnswers.setCount(positiveAnswers.getCount() + 1);
			db.editPositiveAnswers(positiveAnswers);
		}	
	}

	public void updateNegQuestions(Film resultFilm) {
		for (int i = 0; i < negQuestions.size(); i++) {
			NegativeAnswers negativeAnswers = db.findNegativeAnswers(resultFilm.getId(), posQuestions.get(0).getId());
			negativeAnswers.setCount(negativeAnswers.getCount() + 1);
			db.editNegativeAnswers(negativeAnswers);
		}
	}

	public void clearAskedQuestions() {
		amountAskedQuestions = 0;
		askedQuestions.clear();
	}
}