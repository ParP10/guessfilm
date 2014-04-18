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
 * Store data about films
 * 
 */

public class Films {
	private int amountFilms;
	private ArrayList<Film> listFilms;
	/*
	 * prob[f] = P(f| <q_i, A_i>) = P(f) * P(<q_i, A_i> | f), i = 1,k
	 * q_i = questions[i]
	 * A_i = answers[i] 
	 */
	private double prob[];
	
	public ArrayList<Film> getListFilms() {
		return listFilms;
	}

	public void setListFilms(ArrayList<Film> listFilms) {
		this.listFilms = listFilms;
	}
	
	public int getAmountFilms() {
		return amountFilms;
	}

	public void setAmountFilms(int amountFilms) {
		this.amountFilms = amountFilms;
	}


	public double[] getProb() {
		return prob;
	}

	public void setProb(double[] prob) {
		this.prob = prob;
	}
	
	public double getProb(int i) {
		return prob[i];
	}
	
	public void setProb(int i, double prob) {
		this.prob[i] = prob;
	}

	public void initialize(ArrayList<Film> listFilms) {
		this.listFilms = listFilms;
		this.amountFilms = listFilms.size();
		this.prob = new double[this.amountFilms];
	}




	/**
	 * 
	 * @param idFilm
	 *            Film's ID in database
	 * @return Film with ID = idFilm
	 */
	public Film getFilm(int idFilm) {
		// TODO Optimize here
		if (idFilm > amountFilms) idFilm = 1;
		return listFilms.get(idFilm - 1);
	}
	
	public Film getFilm (String strIndex) {
		// Convert index from string to int
		int index = Integer.parseInt(strIndex);
		return getFilm(index);
	}

	/**
	 * Append new Films in database
	 */
	public void appendNewFilms() {
		// append new films in database from "newFilms.txt"
		//int index = amountFilms + 1;
		DataBase dao = new DataBase();
		Questions questions = new Questions(null);
		questions.initialize(dao.findQuestion());
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("data/newFilms.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				int i = 0;
				while (line != null) {
					System.out.println(i++ + line);
					Film newFilm = new Film(line);
					listFilms.add(newFilm);
					amountFilms++;
					int newId = dao.addFilm(newFilm);
					
					for (int j = 0; j < questions.getAmountQuestions(); j++) {
						PositiveAnswers positiveAnswers = new PositiveAnswers();
						positiveAnswers.setQuestionId(j + 1);
						positiveAnswers.setFilmId(newId);
						positiveAnswers.setCount(0);
						dao.addPositiveAnswers(positiveAnswers);
						
						NegativeAnswers negativeAnswers = new NegativeAnswers();
						negativeAnswers.setQuestionId(j + 1);
						negativeAnswers.setFilmId(newId);
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
			OutputStream outputStream = new FileOutputStream(new File("films.txt"));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
			try {
				for (int i = 0; i < amountFilms; i++) {
					writer.write(listFilms.get(i).getFilmName());
					writer.newLine();
				}
			} finally {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public int appendNewFilm(String name) {
		DataBase dao = new DataBase();
		Film newFilm = new Film(name);
		listFilms.add(newFilm);
		amountFilms++;
		return dao.addFilm(newFilm);
	}

}
