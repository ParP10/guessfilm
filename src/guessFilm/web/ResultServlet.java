package guessFilm.web;

import guessFilm.GuessFilm;
import guessFilm.model.Film;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultServlet extends HttpServlet{
	
	static GuessFilm guessFilm;
	static Film resultFilm;
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		req.setCharacterEncoding("UTF-8");
		if (req.getParameter("yes") != null) {
			guessFilm.saveSamples(resultFilm);
			guessFilm.updateStat(resultFilm);
			LastPageServlet lastPageServlet = new LastPageServlet();
			lastPageServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/LastPageFrame.jsp").forward(req, resp);
		} else if (req.getParameter("do_not_know") != null) {
			LastPageServlet lastPageServlet = new LastPageServlet();
			lastPageServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/LastPageFrame.jsp").forward(req, resp);
		} else if (req.getParameter("no") != null) {
			GetFilmServlet getFilmServlet = new GetFilmServlet();
			getFilmServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/GetFilmFrame.jsp").forward(req, resp);
		}
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

	public void saveFilm(Film film) {
		resultFilm = film;
	}
}