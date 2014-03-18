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


	public void initialize(ArrayList<Film> listFilms) {
		this.listFilms = listFilms;
		this.amountFilms = listFilms.size();
	}




	/**
	 * 
	 * @param idFilm
	 *            Film's ID in database
	 * @return Film with ID = idFilm
	 */
	public Film getFilm(int idFilm) {
		// TODO Optimize here
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
		
		// read from file
		try {
			InputStream inputStream = new FileInputStream(new File("newFilms.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			try {
				String line = reader.readLine();
				int i = 0;
				while (line != null) {
					System.out.println(i++ + line);
					Film newFilm = new Film(line);
					listFilms.add(newFilm);
					amountFilms++;
					dao.addFilm(newFilm);
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

	public void appendNewFilm(String name) {
		DataBase dao = new DataBase();
		Film newFilm = new Film(name);
		listFilms.add(newFilm);
		amountFilms++;
		dao.addFilm(newFilm);
	}

}
