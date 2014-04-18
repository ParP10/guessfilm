package guessFilm;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import guessFilm.model.Film;
import guessFilm.model.NegativeAnswers;
import guessFilm.model.PositiveAnswers;
import guessFilm.model.Question;
import guessFilm.model.Sample;
import guessFilm.utils.HibernateUtil;


public class DataBase {

	public int addFilm(Film film) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int result = (Integer) session.save(film);
		session.getTransaction().commit();
		return result;
	}
	
	public Film getFilm(int filmId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Film result = (Film) session.load(Film.class, filmId);
		session.getTransaction().commit();
		return result;
	}
	
	 public ArrayList<Film> findFilm() {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
		@SuppressWarnings("unchecked")
		ArrayList<Film> result = (ArrayList<Film>) session.createQuery("from films").list();
	    session.getTransaction().commit();
	    return result;
	 }
	
	public int addQuestion(Question question) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		int result = (Integer) session.save(question);
		session.getTransaction().commit();
		return result;
	}
	
	public Question getQuestion(int questionId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Question result = (Question) session.load(Film.class, questionId);
		session.getTransaction().commit();
		return result;
	}
	
	
	 public ArrayList<Question> findQuestion() {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
		@SuppressWarnings("unchecked")
		ArrayList<Question> result = (ArrayList<Question>) session.createQuery("from questions").list();
	    session.getTransaction().commit();
	    return result;
	 }
	 
	public long addSample(Sample sample) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		long result = (Long) session.save(sample);
		session.getTransaction().commit();
		return result;
	}
	
	 public ArrayList<Sample> findSample() {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
		@SuppressWarnings("unchecked")
		ArrayList<Sample> result = (ArrayList<Sample>) session.createQuery("from samples").list();
	    session.getTransaction().commit();
	    return result;
	 }
	 
	 public void addPositiveAnswers(PositiveAnswers positiveAnswers) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(positiveAnswers);
		session.getTransaction().commit();
	 }
	 
	 public void addNegativeAnswers(NegativeAnswers negativeAnswers) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.save(negativeAnswers);
		session.getTransaction().commit();
	}
 
	 public PositiveAnswers findPositiveAnswers(int filmId, int questionId) {
	    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();
	    
	    PositiveAnswers result = (PositiveAnswers) session.createCriteria(PositiveAnswers.class)
	    	.add(Restrictions.eq("filmId", filmId)).add(Restrictions.eq("questionId", questionId))
	    	.list().get(0);
	    session.getTransaction().commit();
	    return result;
	 }
	 
	 public NegativeAnswers findNegativeAnswers(int filmId, int questionId) {
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	    session.beginTransaction();

	    NegativeAnswers result = (NegativeAnswers) session.createCriteria(NegativeAnswers.class)
	    	.add(Restrictions.eq("filmId", filmId)).add(Restrictions.eq("questionId", questionId))
	    	.list().get(0);
	    session.getTransaction().commit();
	    return result;
	 }
	 
	 public void editPositiveAnswers(PositiveAnswers positiveAnswers) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(positiveAnswers);
		session.getTransaction().commit();
	 }
	 
	 public void editNegativeAnswers(NegativeAnswers negativeAnswers) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(negativeAnswers);
		session.getTransaction().commit();
	 }
	 
	 public void editFilm(Film film) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(film);
		session.getTransaction().commit();
	 }
}

