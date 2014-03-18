package guessFilm.web;

import guessFilm.GuessFilm;
import guessFilm.model.Question;

import java.io.IOException;
import java.sql.SQLException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TrainServlet extends HttpServlet {
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		req.setCharacterEncoding("UTF-8");
		if (req.getParameter("start")!= null) {
			getServletContext().getRequestDispatcher("/MainFrame.jsp").forward(req, resp);
		} else {
			getServletContext().getRequestDispatcher("/TrainFrame.jsp").forward(req, resp);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			System.out.println("Get");
			processRequest(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			System.out.println("Post");
			processRequest(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
