package guessFilm;

import guessFilm.GuessFilm.AnswerOnQuestion;
import guessFilm.model.Film;
import guessFilm.model.Question;

import java.util.Scanner;

/**
 * 
 * Class for communication with user
 *
 */
public class UserInterface {
	private boolean flag = false; // for testing

	public UserInterface() {
		
	}
	/**
	 * @return true, if user stops program
	 */
	public boolean stopProgram() {
		return flag;
	}
	
	/**
	 * Print question on display
	 */
	public void printQuestion(Question question) {
		System.out.println(question.getName());
		
	}
	
	public void changeFlag (boolean fl) {
		flag = fl;
	}
	
	/**
	 * Get answer on the question from user
	 */
	public AnswerOnQuestion getAnswerOnQuestion() {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		if (s.equals("y")) {
			return AnswerOnQuestion.YES;
		} else if (s.equals("n")) {
			return AnswerOnQuestion.NO;
		} else if (s.equals("d")) {
			return AnswerOnQuestion.DO_NOT_KNOW;
		}
		changeFlag(true);
		return AnswerOnQuestion.CLOSE;
	}
	
	/**
	 * Print film's name on display
	 */
	public void printFilm(Film film) {
		System.out.println(film.getName());
		
	}
	
	/**
	 * Is user give true film's name?
	 */
	public boolean giveTrueAnswer() {
		System.out.println("Do you now right film?");
		AnswerOnQuestion answer = getAnswerOnQuestion();
		if (answer == AnswerOnQuestion.YES) {
			return true;
		}
		return false;
	}
	
	/**
	 * Get true film from user
	 */
	public int trueFilm() {
		System.out.println("Please, write film's Id");
		Scanner in = new Scanner(System.in);
		return in.nextInt();
	}
	
	/**
	 * User chooses mode
	 */
	public GuessFilm.Mode getMode() {
		System.out.println("Choose program mode:");
		System.out.println("1 - guess mode");
		System.out.println("2 - training mode");
		System.out.println("3 - add questions in database");
		System.out.println("4 - add films in database");
		System.out.println("5 - add samples in database from file");
		
		Scanner in = new Scanner(System.in);
		int mode = in.nextInt();
		switch (mode) {
		case 1:
			return GuessFilm.Mode.GUESS_MODE;
		case 2:
			return GuessFilm.Mode.TRAINING_MODE;
		case 3:
			return GuessFilm.Mode.APPEND_NEW_QUESTIONS;
		case 4:
			return GuessFilm.Mode.APPEND_NEW_FILMS;
		case 5:
			return GuessFilm.Mode.APPEND_NEW_SAMPLES;
		default:
			System.out.println("Wrong input");
			break;
		}
		return null;
	}

}
