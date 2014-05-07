package guessFilm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import guessFilm.Learning.ClassifierType;
import guessFilm.model.Film;
import guessFilm.model.Films;
import guessFilm.model.NegativeAnswers;
import guessFilm.model.Pair;
import guessFilm.model.PositiveAnswers;
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
		TRAINING_MODE, GUESS_MODE, APPEND_NEW_QUESTIONS, APPEND_NEW_FILMS, APPEND_NEW_SAMPLES, APPEND_FILMS_AND_QUESTIONS_FROM_KINOPOISK;
	};

	public enum AnswerOnQuestion {
		NO, YES, DO_NOT_KNOW, CLOSE; // CLOSE - for testing
	};

	Films films = new Films();
	Questions questions = new Questions(films);
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
			case APPEND_FILMS_AND_QUESTIONS_FROM_KINOPOISK:
				guessFilm.kinopoisk();
			default:
				break;
		}
		System.out.println("End!");
	}


	private void kinopoisk() throws IOException {
		File filmsDirectory = new File("data/film");
		File[] films = filmsDirectory.listFiles();
		
		for (int i = 0; i < films.length; i++) {
			System.out.println(films[i].getName());
			if (films[i].isDirectory()) {
				File[] params = films[i].listFiles();
				int j = 0;
				for (; j < params.length; j++) {
					if (params[j].getName().equals("keywords")) {
						break;
					}
				}
				if (j == params.length) {
					continue;
				}
				String currentFilm = null;
				int h = 0;
				for (; h < params.length; h++) {
					if (params[h].getName().equals("default.htm")) {
						currentFilm = getFilm(params[h]);
						break;
					}
				}
				if (h == params.length) {
					continue;
				}
				File[] keywords = params[j].listFiles();
				for (j = 0; j < keywords.length; j++) {
					if (keywords[j].getName().equals("default.htm")) {
						processTags(currentFilm, keywords[j]);
					}
				}
			}
		}
		
	}

	private void processTags(String filmName, File file) throws IOException {
		DataBase db = new DataBase();
		Film currentFilm = db.findFilm(filmName);
		// if film already exists in db
		if (currentFilm != null) {
			return;
		}
		currentFilm = new Film();
		currentFilm.setName(filmName);
		currentFilm.setId(db.addFilm(currentFilm));
		currentFilm.setCount(1);
		// get list of tags for this film
		ArrayList <String> tags = retrieveTags(file);
		// create new questions and samples
		for (int i = 0; i < tags.size(); i++) {
			Question question = db.findQuestion(tags.get(i));
			//create question
			if (question == null) {
				// create question
				question = new Question();
				question.setName(tags.get(i));
				question.setId(db.addQuestion(question));
			}
			// create sample
			Sample sample = new Sample();
			sample.setFilmId(currentFilm.getId());
			sample.setQuestionId(question.getId());
			sample.setAnswer(1); // Answer: YES
		}
		
	}

	private ArrayList <String> retrieveTags(File file) throws IOException {
		ArrayList <String> tags = new ArrayList <String>();
		//regex
		Pattern pattern = Pattern.compile("data-real-keyword=\"[^\"]+");
		// text
		Matcher matcher = null;
		String line = null;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		while((line = reader.readLine()) != null) {
			if (line.contains("data-real-keyword")) {
				matcher = pattern.matcher(line);
				matcher.find();
				tags.add(matcher.group().substring(19));
			}
		}
		return tags;
	}

	private String getFilm(File file) throws IOException {
		//regex
		Pattern pattern = Pattern.compile("name_film_html = '[^']*");
		// text
		Matcher matcher = null;
		String line = null;
		FileReader fr = new FileReader(file);
		//System.out.println(fr.getEncoding());
		BufferedReader reader = new BufferedReader(fr);
		while((line = reader.readLine()) != null) {
			//System.out.println(line);
			if (line.contains("name_film_html")) {
				
				//String res = new String(line.getBytes("UTF-8"), "Cp1251");
				//System.out.println(res);
				matcher = pattern.matcher(line);
				
				break;
			}
		}
		matcher.find();
		String film = matcher.group();
		film = film.substring(18);
		System.out.println(film);
		return film;
	}
	
	/*private void convertEncoding(File file, String newEncoding) {
		InputStream in = new FileInputStream(file);
	    OutputStream out = new FileOutputStream(outfile);
	    Reader r=new BufferedReader(new InputStreamReader(in, from));
	    Writer w=new BufferedWriter(new OutputStreamWriter(out, to));
		char[] buffer=new char[4096];
	    int len;
	    while((len=r.read(buffer)) != -1)
	        w.write(buffer, 0, len);
		
	}*/

	public void guess() throws Exception {

		/*
		 * Choose classifier
		 */
		
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;

		/*
		 * Set classifier and load model
		 */
		//Learning classifier = new Learning();
		classifier.loadModel(classifierType);
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		classifier.createFeatureVector();
		
		//Samples samples = new Samples();
	
		/*
		 * While there is question and user does't stop program - ask question
		 */
		AnswerOnQuestion answerOnQuestion = null;
		while (questions.existsQuestion() && !user.stopProgram()) {
			/*
			 * Ask question
			 */
			Question currentQuestion = new Question();
			currentQuestion = questions.getNextQuestion(answerOnQuestion);
			user.printQuestion(currentQuestion);
			/*
			 * Answer question
			 */
			answerOnQuestion = user.getAnswerOnQuestion();
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
		
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		
		Samples samples = new Samples();
		samples.initialize(dao.findSample());
		classifier.loadData(samples);
		
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		classifier.createModel(classifierType);
		
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
		int newId = questions.appendNewQuestion(name);
		
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
	}


	public void appendNewFilm(String name) {
		int newId = films.appendNewFilm(name);
		
		for (int i = 0; i < questions.getAmountQuestions(); i++) {
			PositiveAnswers positiveAnswers = new PositiveAnswers();
			positiveAnswers.setQuestionId(i + 1);
			positiveAnswers.setFilmId(newId);
			positiveAnswers.setCount(0);
			dao.addPositiveAnswers(positiveAnswers);
			
			NegativeAnswers negativeAnswers = new NegativeAnswers();
			negativeAnswers.setQuestionId(i + 1);
			negativeAnswers.setFilmId(newId);
			negativeAnswers.setCount(0);
			dao.addNegativeAnswers(negativeAnswers);
		}
	}

	public void guessInit() throws Exception {
		films.initialize(dao.findFilm());
		questions.initialize(dao.findQuestion());
		
		ClassifierType classifierType = ClassifierType.NAIVE_BAYES;
		classifier.loadModel(classifierType);
		classifier.createAttributes(questions.getAmountQuestions(), films.getAmountFilms());
		classifier.createFeatureVector();
	}

	public Question getNextQuestion(AnswerOnQuestion answerOnQuestion) {
		return questions.getNextQuestion(answerOnQuestion);
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
	
	public ArrayList< Pair<Film, Double> > getDistributionTopN(int n) throws Exception {
		//return classifier.getDistributionTopN(n);
		ArrayList <Pair<Integer, Double> > distrib = classifier.getDistributionTopN(n);
		ArrayList <Pair<Film, Double> > result = new ArrayList<Pair<Film, Double> >();
		for (int i = 0; i < 20; i++) {
			Film film = films.getFilm(distrib.get(i).getFirst());
			result.add(new Pair<Film, Double>(film, distrib.get(i).getSecond()));
			System.out.println(result.get(i).getFirst().getName());
			System.out.println(result.get(i).getSecond());
		}
		return result;
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
		for (int i = 1; i <= films.getAmountFilms(); i++) {
			if (films.getFilm(i).getName().equals(filmName)) {
				saveSamples(films.getFilm(i));
				return;
			}
		}
		films.appendNewFilm(filmName);
		Film film = new Film();
		film = films.getFilm(films.getAmountFilms());
		saveSamples(film);
		
		updateStat(film);
	}

	public void clearQuestionHistory() {
		questions.clearAskedQuestions();
	}

	public void removeSamples() {
		samples.clear();
	}
	
	public void newClassifier() {
		classifier.createFeatureVector();
	}

	public void updateStat(Film resultFilm) {
		questions.updatePosQuestions(resultFilm);
		questions.updateNegQuestions(resultFilm);
		resultFilm.setCount(resultFilm.getCount() + 1);
		dao.editFilm(resultFilm);
	}
}