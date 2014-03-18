package guessFilm;

import guessFilm.Learning.ClassifierType;
import guessFilm.model.Film;
//import javax.jws.WebParam.Mode;
import guessFilm.model.Films;
import guessFilm.model.Question;
import guessFilm.model.Questions;
import guessFilm.model.Sample;
import guessFilm.model.Samples;

/**
 * 
 * Core class
 * 
 */
public class GuessFilm {

	public enum Mode {
		TRAINING_MODE, GUESS_MODE, APPEND_NEW_QUESTIONS, APPEND_NEW_FILMS, APPEND_NEW_SAMPLES;
	};

	public enum AnswerOnQuestion {
		NO, YES, DO_NOT_KNOW, CLOSE; // CLOSE - for testing
	};

	Films films = new Films();
	Questions questions = new Questions();
	UserInterface user = new UserInterface();
	DataBase dao = new DataBase();
	Learning classifier = new Learning();
	Samples samples = new Samples();
	
	public GuessFilm() {
		
	}

	/**
	 * Program start here
	 * @throws Exception 
	 */

	public static void main(String[] args) throws Exception {
		
		GuessFilm guessFilm = new GuessFilm();

		guessFilm.films.initialize(guessFilm.dao.findFilm());
		guessFilm.questions.initialize(guessFilm.dao.findQuestion());

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
			case APPEND_NEW_SAMPLES:
				guessFilm.addSamples();
			default:
				break;
		}
		System.out.println("End!");
	}


	public void guess() throws Exception {

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
		
		//Samples samples = new Samples();
	
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
			if (answerOnQuestion != AnswerOnQuestion.DO_NOT_KNOW) {
				classifier.addFeature(currentQuestion, answerOnQuestion);
			}
			/*
			 * Add new sample
			 */
			Sample currentSample = new Sample();
			currentSample.setQuestionId(currentQuestion.getId());
			currentSample.setAnswer(answerOnQuestion.ordinal());
			samples.addSample(currentSample);
			
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
		 * If user says right answer, save sample
		 */
		if (user.giveTrueAnswer()) {
			int id = user.trueFilm();
			Sample curSample = new Sample();
			for (int i = 0; i < samples.size(); i++) {
				curSample = samples.getSample(i);
				curSample.setFilmId(id);
				dao.addSample(curSample);
			}
			
		}
	}
/* Old version
	public void train() throws Exception {
		
		// Choose and create classifier
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		Learning classifier = new Learning();
		classifier.createModel(classifierType);
		
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		
		Samples samples = new Samples();
		samples.initialize(dao.findSample());
		classifier.loadData(samples);
		
		classifier.train();
		
		classifier.saveModel();
		
	}
*/
	public void train() throws Exception {
		
		// Choose and create classifier
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		//Learning classifier = new Learning();
		classifier.createModel(classifierType);
		System.out.println("AMOUNT QUESTIONS:   " + questions.getAmountQuestions());
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		
		Samples samples = new Samples();
		samples.initialize(dao.findSample());
		classifier.loadData(samples);
		
		classifier.train();
		
		classifier.saveModel();
		
	}

	public Mode modeSelection() {
		return user.getMode();
	}
	
	public void addSamples() {
		Samples samples = new Samples();
		samples.appendNewSamples();
		
		for (int i = 0; i < samples.size(); i++) {
			dao.addSample(samples.getSample(i));
		}
		
	}


	public void appendNewQuestion(String name) {
		questions.appendNewQuestion(name);
	}


	public void appendNewFilm(String name) {
		films.appendNewFilm(name);
	}

	public void guessInit() throws Exception {
		films.initialize(dao.findFilm());
		questions.initialize(dao.findQuestion());
		
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		classifier.loadModel(classifierType);
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		classifier.createFeatureVector();
	}

	public Question getNextQuestion() {
		return questions.getNextQuestion();
	}

	public void quess(Question question, AnswerOnQuestion answerOnQuestion) {
		if (answerOnQuestion != AnswerOnQuestion.DO_NOT_KNOW) {
			classifier.addFeature(question, answerOnQuestion);
		}
		Sample currentSample = new Sample();
		currentSample.setQuestionId(question.getId());
		currentSample.setAnswer(answerOnQuestion.ordinal());
		samples.addSample(currentSample);
	}

	public Film getCurrentFilm() throws Exception {
		return films.getFilm(classifier.classify());
	}

	public void saveSamples(Film resultFilm) {
		Sample curSample = new Sample();
		for (int i = 0; i < samples.size(); i++) {
			curSample = samples.getSample(i);
			curSample.setFilmId(resultFilm.getId());
			dao.addSample(curSample);
		}
	}

	public void setTrueFilm(String filmName) {
		// TODO optimize this algorithm
		System.out.println("On setTrueFilm");
		for (int i = 1; i <= films.getAmountFilms(); i++) {
			System.out.println("iteration" + i);
			if (films.getFilm(i).getName().equals(filmName)) {
				saveSamples(films.getFilm(i));
				return;
			}
		}
		System.out.println("After cycle");
		films.appendNewFilm(filmName);
		Film film = new Film();
		film = films.getFilm(films.getAmountFilms());
		saveSamples(film);
		System.out.println("At the end of function");
	}

	public void clearQuestionHistory() {
		questions.setAmountAskedQuestions(0);
	}

	public void removeSamples() {
		samples.clear();
	}

}
