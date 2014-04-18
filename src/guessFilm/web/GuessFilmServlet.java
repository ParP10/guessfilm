package guessFilm.web;

import guessFilm.GuessFilm;
import guessFilm.GuessFilm.AnswerOnQuestion;
import guessFilm.model.Film;
import guessFilm.model.Pair;
import guessFilm.model.Question;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuessFilmServlet extends HttpServlet{
	
	static GuessFilm guessFilm;
	static Question oldQuestion;
	static AnswerOnQuestion answerOnQuestion = null;
	//int topN = 20;
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		
		Film film = new Film();
		Question question = new Question();
		
		ArrayList <Pair<Film, Double> > curDistrib;
		
		if (req.getParameter("yes") != null) {
			answerOnQuestion = AnswerOnQuestion.YES;
			guessFilm.quess(oldQuestion, AnswerOnQuestion.YES);
			film = guessFilm.getCurrentFilm();
			req.setAttribute("film", film);
			//curDistrib = guessFilm.getDistributionTopN(topN);
			//req.setAttribute("curDistrib", curDistrib);
		} else if (req.getParameter("do_not_know") != null) {
			answerOnQuestion = AnswerOnQuestion.DO_NOT_KNOW;
			guessFilm.quess(oldQuestion, AnswerOnQuestion.DO_NOT_KNOW);
			film = guessFilm.getCurrentFilm();
			req.setAttribute("film", film);
			//curDistrib = guessFilm.getDistributionTopN(topN);
			//req.setAttribute("curDistrib", curDistrib);
		} else if (req.getParameter("no") != null) {
			answerOnQuestion = AnswerOnQuestion.NO;
			guessFilm.quess(oldQuestion, AnswerOnQuestion.NO);
			film = guessFilm.getCurrentFilm();
			req.setAttribute("film", film);
			//curDistrib = guessFilm.getDistributionTopN(topN);
			//req.setAttribute("curDistrib", curDistrib);
		}
		
		question = guessFilm.getNextQuestion(answerOnQuestion);
		req.setAttribute("question", question);
		if (req.getParameter("end") != null || question == null){
			guessFilm.clearQuestionHistory();
			film = guessFilm.getCurrentFilm();
			req.setAttribute("film", film);
			//curDistrib = guessFilm.getDistributionTopN(topN);
			//req.setAttribute("curDistrib", curDistrib);
			ResultServlet resultServlet = new ResultServlet();
			resultServlet.saveGuessFilm(guessFilm);
			resultServlet.saveFilm(film);
			getServletContext().getRequestDispatcher("/ResultFrame.jsp").forward(req, resp);
			return;
		}
		
		oldQuestion = question;
		getServletContext().getRequestDispatcher("/GuessFilmFrame.jsp").forward(req, resp);
		
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			processRequest(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			processRequest(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveGuessFilm(GuessFilm guessFilm1) {
		guessFilm = guessFilm1;
		
	}

	public void saveQuestion(Question question) {
		oldQuestion = question;
	}
}