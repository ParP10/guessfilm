package guessFilm;

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
		System.out.println(question.getQuestionName());
		
	}
	
	public void changeFlag (boolean fl) {
		flag = fl;
	}
	
	/**
	 * Get answer on the question from user
	 */
	public GuessFilm.AnswerOnQuestion getAnswerOnQuestion() {
		Scanner in = new Scanner(System.in);
		String s = in.next();
		if (s.equals("y")) {
			return GuessFilm.AnswerOnQuestion.YES;
		} else if (s.equals("n")) {
			return GuessFilm.AnswerOnQuestion.NO;
		} else if (s.equals("d")) {
			return GuessFilm.AnswerOnQuestion.DO_NOT_KNOW;
		}
		changeFlag(true);
		return GuessFilm.AnswerOnQuestion.CLOSE;
	}
	
	/**
	 * Print film's name on display
	 */
	public void printFilm(Film film) {
		System.out.println(film.getFilmName());
		
	}
	
	/**
	 * Is user give true film's name?
	 */
	public boolean giveTrueAnswer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Get true film from user
	 */
	public Film trueFilm() {
		// TODO Auto-generated method stub
		return null;
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
		default:
			System.out.println("Wrong input");
			break;
		}
		return null;
	}

}
