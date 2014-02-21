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
 * Store data about films
 * 
 */

public class Films {
	private int amountFilms;
	private ArrayList<Film> listFilms;

	public Films() {
		initializeListFilms();
		amountFilms = listFilms.size();
	}

	/**
	 * Initialize list of films (listFilms). Select data from database
	 */
	private void initializeListFilms() {
		
		listFilms = new ArrayList<Film>();
		int index = 1;
		
		try {
			InputStream inputStream = new FileInputStream(new File("films.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					listFilms.add(new Film(index++, line));
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
	 * @return Amount films in database
	 */
	public int getAmountFilms() {
		return amountFilms;
	}

	/**
	 * 
	 * @param idFilm
	 *            Film's ID in database
	 * @return Film with ID = idFilm
	 */
	public Film getFilm(int idFilm) {
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
		int index = amountFilms + 1;
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("newFilms.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				while (line != null) {
					listFilms.add(new Film(index++, line));
					amountFilms++;
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
		}
		
	}

}
