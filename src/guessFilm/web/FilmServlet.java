package guessFilm.web;

import guessFilm.GuessFilm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilmServlet extends HttpServlet{
	
	static GuessFilm guessFilm;
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		req.setCharacterEncoding("UTF-8");
		if (req.getParameter("ok")!= null) {
			if (req.getParameter("newFilm") != "") {
				guessFilm.appendNewFilm(req.getParameter("newFilm"));
			}
			getServletContext().getRequestDispatcher("/FilmFrame.jsp").forward(req, resp);
		} else if (req.getParameter("end") != null) {
			LastPageServlet lastPageServlet = new LastPageServlet();
			lastPageServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/LastPageFrame.jsp").forward(req, resp);
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
}
