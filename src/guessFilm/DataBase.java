package guessFilm;

import java.util.ArrayList;

import org.hibernate.Session;

import guessFilm.model.Film;
import guessFilm.model.Question;
import guessFilm.utils.HibernateUtil;


public class DataBase {

	public Long addFilm(Film film) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Long result = (Long) session.save(film);
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
	
	public Long addQuestion(Question question) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Long result = (Long) session.save(question);
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

}

