package guessFilm;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import guessFilm.model.Film;
import guessFilm.model.Pair;
import guessFilm.model.Question;
import guessFilm.model.Samples;

/**
 * 
 * Class for WEKA Library
 * 
 */

public class Learning {
	private Classifier cls;
	private ArrayList<Attribute> attributes;
	private Instances data;

	
	public enum ClassifierType {
		NAIVE_BAYES;
		// TODO: add classifier
	}

	
	public void createModel(ClassifierType classifierType) {
		switch (classifierType) {
		case NAIVE_BAYES:
			cls = new NaiveBayes();
			break;
		default:
			break;
		}
	}

	public void loadModel(ClassifierType classifierType) throws Exception {
		switch (classifierType) {
		case NAIVE_BAYES:
			// Deserializing a classifier
				cls = (Classifier) SerializationHelper.read("naive_bayes.model");
			break;
		default:
			break;
		}
	}
	
	
	public void createAttributes(int numtree, int numFilms) {
		attributes = new ArrayList<Attribute>();
		
		ArrayList<String> questionAnswers = new ArrayList<String>();
		questionAnswers.add("NO");
		questionAnswers.add("YES");
		questionAnswers.add("DO NOT KNOW");
		
		for (int i = 0; i < numtree; i++) {
			attributes.add(new Attribute(Integer.toString(i), questionAnswers));
		}
		
		ArrayList<String> filmAnswers = new ArrayList<String>();
		for (int i = 0; i <= numFilms; i++) {
			filmAnswers.add(Integer.toString(i));
		}
		
		attributes.add(new Attribute("Result", filmAnswers));
	}
	
	public void loadData(Samples samples) throws Exception {
		data = new Instances("rel", attributes, 0);
		System.out.println(samples.size());
		
		for (int i = 0; i < samples.size(); i++) {
			Instance inst = new DenseInstance(data.numAttributes());
			inst.setDataset(data);
			System.out.println(samples.getSample(i).getQuestionId() - 1);
			System.out.println(samples.getSample(i).getAnswer());
			inst.setValue(samples.getSample(i).getQuestionId() - 1, samples.getSample(i).getAnswer());//
			inst.setValue(data.numAttributes() - 1, samples.getSample(i).getFilmId());
			data.add(inst);
		}
		
		data.setClassIndex(data.numAttributes() - 1);
		//Old version
		/*
		Scanner scanner = new Scanner(new File("trainset.txt"), "UTF-8");
		
		int numInstances = scanner.nextInt();
		int oldNumAttributes = scanner.nextInt();
		
		for (int i = 0; i < numInstances; i++) {
			Instance inst = new DenseInstance(data.numAttributes());
			inst.setDataset(data);
			
			for (int j = 0; j < oldNumAttributes; j++) {
				
				 //NO = 0
				  //YES = 1
				  //DO NOT KNOW = 2
				  //NULL = 3
				 
				int value = scanner.nextInt();
				inst.setValue(j, value);
			}
			
			data.add(inst);
		}
		
		data.setClassIndex(data.numAttributes() - 1);
		*/
	}
	
	
	public void createFeatureVector() {
		
		// Create instances
		data = new Instances("Dataset", attributes, 0);
		data.setClassIndex(data.numAttributes() - 1);
		
		Instance inst = new DenseInstance(data.numAttributes());
		data.add(inst);
		
	}

	/**
	 * Add feature in the vector of features
	 * 
	 * @param currentQuestion
	 * @param answerOnQuestion
	 */
	public void addFeature(Question currentQuestion,
			GuessFilm.AnswerOnQuestion answerOnQuestion) {
		
		switch(answerOnQuestion) {
		case NO:
			data.get(0).setValue(data.attribute(Integer.toString(currentQuestion.getId() - 1)), 0);
			break;
		case YES:
			data.get(0).setValue(data.attribute(Integer.toString(currentQuestion.getId() - 1)), 1);
			break;
		case DO_NOT_KNOW:
			data.get(0).setValue(data.attribute(Integer.toString(currentQuestion.getId() - 1)), 2);
			break;
		default:
			break;	
		}

	}
	
	/**
	 * 
	 * @return Film ID in String representation
	 * @throws Exception
	 */

	public String classify() throws Exception {
		double res = cls.classifyInstance(data.instance(0));
		data.instance(0).setClassValue(res);
		return data.classAttribute().value((int)res);
	}
	
	/**
	 * Get N films with top probability
	 */
	public ArrayList<Pair<Integer, Double> > getDistributionTopN(int n) throws Exception {
		double [] filmsDistrib = cls.distributionForInstance(data.instance(0));
		if (n > filmsDistrib.length) {
			n = filmsDistrib.length;
		}
		ArrayList<Pair<Integer, Double> > result = new ArrayList<Pair<Integer, Double> >(n + 1);
		for (int i = 0; i < filmsDistrib.length; i++) {
			result.add(new Pair<Integer, Double>(i + 1, filmsDistrib[i + 1]));
		}
		return result;
		/*for (int i = 0; i < filmsDistrib.length; i++) {
			int j;
			if (n > i) {
				for (j = i - 1; j >= 0; j--) {
					if (result.get(j).getSecond() > filmsDistrib[i]) break;
				}
				result.add(new Pair<Integer, Double>(i + 1, filmsDistrib[i]));
				for (int h = i; h > j + 1; h--) {
					//result.set(h, result.get(h - 1));
					result.get(h).setSecond(result.get(h - 1).getSecond());
					result.get(h).setFirst(result.get(h - 1).getFirst());
				}
				result.get(j + 1).setSecond(filmsDistrib[i]);
				result.get(j + 1).setFirst(i + 1);
				
			} else {
				for (j = n - 1; j >= 0; j--) {
					if (result.get(j).getSecond() > filmsDistrib[i]) break;
				}
				if (j == n - 1) continue;
				for (int h = n - 1; h > j + 1; h--) {
					//result.set(h, result.get(h - 1));
					result.get(h).setSecond(result.get(h - 1).getSecond());
					result.get(h).setFirst(result.get(h - 1).getFirst());
				}
				result.get(j + 1).setSecond(filmsDistrib[i]);
				result.get(j + 1).setFirst(i + 1);
			}
			/*for (j = m - 1; j >= 0; j--) {
				if (result.get(j).getSecond() > filmsDistrib[i]) break;
			}
			if (j == n - 1) continue;
			if (j == m - 1) {
				result.add(new Pair<Integer, Double>(i + 1, filmsDistrib[i]));
				continue;
			}
			for (int h = m - 1; h > j + 1; h--) {
				result.set(h, result.get(h - 1));
				//result.get(h).setSecond(result.get(h - 1).getSecond());
				//result.get(h).setIndex(result.get(h - 1).getIndex());
			}
			result.get(j + 1).setSecond(filmsDistrib[i]);
			result.get(j + 1).setFirst(i + 1);*/
		/*}
		return result;*/
	}


	public void train() throws Exception {
		cls.buildClassifier(data);
	}

	
	public void saveModel() throws Exception {
		SerializationHelper.write("naive_bayes.model", cls);
	}

}
