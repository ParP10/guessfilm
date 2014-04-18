package guessFilm.model;


import guessFilm.DataBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Samples {

	private ArrayList<Sample> listSamples = new ArrayList<Sample>();

	public void addSample(Sample currentSample) {
		listSamples.add(currentSample);
	}

	public int size() {
		return listSamples.size();
	}

	public Sample getSample(int index) {
		return listSamples.get(index);
	}

	public void initialize(ArrayList<Sample> listSamples) {
		this.listSamples = listSamples;
	}

	public void appendNewSamples() {
		// read from file
		DataBase db = new DataBase();
		Films films = new Films();
		films.initialize(db.findFilm());
		
		
		try {
			Scanner scanner = new Scanner(new File("data/trainset.txt"));
			int filmsAmount = scanner.nextInt();
			int questionAmount = scanner.nextInt();
			try {
				for (int i = 0; i < filmsAmount; i++) {
					for (int j = 0; j < questionAmount - 1; j++) {
						Sample newSample = new Sample();
						newSample.setAnswer(scanner.nextInt());
						newSample.setQuestionId(j + 1);
						
						listSamples.add(newSample);
					}
					int trueFilm = scanner.nextInt();
					
					films.getFilm(trueFilm).setCount(films.getFilm(trueFilm).getCount() + 1);
					db.editFilm(films.getFilm(trueFilm));
					
					for (int j = 0; j < questionAmount - 1; j++) {
						listSamples.get(i * (questionAmount - 1) + j).setFilmId(trueFilm);
						if (listSamples.get(i * (questionAmount - 1) + j).getAnswer() == 1) {
							PositiveAnswers positiveAnswers = db.findPositiveAnswers(trueFilm, j + 1);
							positiveAnswers.setCount(positiveAnswers.getCount() + 1);
							db.editPositiveAnswers(positiveAnswers);
						} else {
							NegativeAnswers negativeAnswers = db.findNegativeAnswers(trueFilm, j + 1);
							negativeAnswers.setCount(negativeAnswers.getCount() + 1);
							db.editNegativeAnswers(negativeAnswers);
						}
					}
					
				}
			} finally {
				scanner.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void clear() {
		listSamples.clear();
	}

}
