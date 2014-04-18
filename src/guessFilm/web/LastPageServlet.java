package guessFilm.web;

import guessFilm.GuessFilm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LastPageServlet extends HttpServlet{
	static GuessFilm guessFilm;
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		req.setCharacterEncoding("UTF-8");
		guessFilm.removeSamples();
		System.out.println("At the end");
		if (req.getParameter("start")!= null) {
			getServletContext().getRequestDispatcher("/MainFrame.jsp").forward(req, resp);
		} else {
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
