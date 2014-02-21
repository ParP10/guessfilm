package guessFilm;

import guessFilm.Learning.ClassifierType;
import guessFilm.model.Film;
//import javax.jws.WebParam.Mode;
import guessFilm.model.Films;
import guessFilm.model.Question;
import guessFilm.model.Questions;

/**
 * 
 * Core class
 * 
 */
public class GuessFilm {
	public enum Mode {
		TRAINING_MODE, GUESS_MODE, APPEND_NEW_QUESTIONS, APPEND_NEW_FILMS;
	};

	public enum AnswerOnQuestion {
		NO, YES, DO_NOT_KNOW, CLOSE; // CLOSE - for testing
	};

	private Films films = new Films();
	private Questions questions = new Questions();
	private UserInterface user = new UserInterface();

	/**
	 * Program start here
	 * @throws Exception 
	 */

	public static void main(String[] args) throws Exception {
		GuessFilm guessFilm = new GuessFilm();

		/*
		 * Choose mode
		 */
		switch (guessFilm.modeSelection()) {
			case TRAINING_MODE:
				guessFilm.train();
				break;
			case GUESS_MODE:
				guessFilm.guess();
				break;
			case APPEND_NEW_QUESTIONS:
				guessFilm.questions.appendNewQuestions();
				break;
			case APPEND_NEW_FILMS:
				guessFilm.films.appendNewFilms();
				break;
			default:
				break;
		}

	}

	private void guess() throws Exception {

		/*
		 * Choose classifier
		 */
		
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;

		/*
		 * Set classifier and load model
		 */
		Learning classifier = new Learning();
		classifier.loadModel(classifierType);
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		classifier.createFeatureVector();
	
		/*
		 * While there is question and user does't stop program - ask question
		 */
		while (questions.existsQuestion() && !user.stopProgram()) {
			/*
			 * Ask question
			 */
			Question currentQuestion = new Question();
			currentQuestion = questions.getNextQuestion();
			user.printQuestion(currentQuestion);
			/*
			 * Answer question
			 */
			AnswerOnQuestion answerOnQuestion = user.getAnswerOnQuestion();
			if (answerOnQuestion == AnswerOnQuestion.CLOSE) {
				break;
			}
			classifier.addFeature(currentQuestion, answerOnQuestion);
			/*
			 * Classification
			 */
			Film currentFilm = films.getFilm(classifier.classify());
			/*
			 * Show the result
			 */
			user.printFilm(currentFilm);
		}

		/*
		 * If user says right answer, train classifier
		 */
		/*if (user.giveTrueAnswer()) {
			Film trueFilm = new Film();
			trueFilm = user.trueFilm();
			classifier.addAnswer(trueFilm);
			classifier.train();
			classifier.saveModel();
		}*/
	}

	private void train() throws Exception {
		
		// Choose and create classifier
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		Learning classifier = new Learning();
		classifier.createModel(classifierType);
		
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		
		classifier.loadData();
		
		classifier.train();
		
		classifier.saveModel();
		
	}

	/**
	 * 
	 * @return Program mode
	 */
	private Mode modeSelection() {
		return user.getMode();
	}

}

