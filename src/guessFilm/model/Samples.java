package guessFilm.model;


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
		try {
			Scanner scanner = new Scanner(new File("trainset.txt"));
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
					for (int j = 0; j < questionAmount - 1; j++) {
						listSamples.get(i * (questionAmount - 1) + j).setFilmId(trueFilm);
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
