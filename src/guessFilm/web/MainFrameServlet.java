package guessFilm.web;

import guessFilm.GuessFilm;
import guessFilm.model.Film;
import guessFilm.model.Question;

import java.io.IOException;
import java.sql.SQLException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainFrameServlet extends HttpServlet {
	static GuessFilm guessFilm = new GuessFilm();
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		int answer = 0;
		try {
			answer = checkAction(req);
		} catch (SQLException sql_e) {
			throw new IOException(sql_e.getMessage());
		}
		
		switch(answer) {
		case 1:
			GuessFilmServlet guessFilmServlet = new GuessFilmServlet();
			guessFilm.newClassifier();
			req.setAttribute("guessFilm", guessFilm);
			Question question = new Question();
			
			question = guessFilm.getNextQuestion(null);
			req.setAttribute("question", question);
			
			Film film = new Film();
			film.setName("");
			req.setAttribute("film", film);

			guessFilmServlet.saveGuessFilm(guessFilm);
			guessFilmServlet.saveQuestion(question);
			getServletContext().getRequestDispatcher("/GuessFilmFrame.jsp").forward(req, resp);
			return;
		case 2:
			guessFilm.train();
			getServletContext().getRequestDispatcher("/TrainFrame.jsp").forward(req, resp);
			break;
		case 3:
			QuestionServlet questionServlet = new QuestionServlet();
			questionServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/QuestionFrame.jsp").forward(req, resp);
			break;
		case 4:
			FilmServlet filmServlet = new FilmServlet();
			filmServlet.saveGuessFilm(guessFilm);
			getServletContext().getRequestDispatcher("/FilmFrame.jsp").forward(req, resp);
			break;
		case 5:
			//TODO
			guessFilm.addSamples();
			SampleServlet sampleServlet = new SampleServlet();
			
			getServletContext().getRequestDispatcher("/SampleFrame.jsp").forward(req, resp);
			break;
		default:
			getServletContext().getRequestDispatcher("/MainFrame.jsp").forward(req, resp);
			guessFilm.guessInit();
			//break;
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
	
	private int checkAction(HttpServletRequest req) throws SQLException {
		if (req.getParameter("guess") != null) {
			System.out.println("guess");
			return 1;
		} else if (req.getParameter("train") != null) {
			return 2;
		} else if (req.getParameter("question") != null) {
			return 3;
		} else if (req.getParameter("film") != null) {
			return 4;
		} else if (req.getParameter("sample") != null) {
			return 5;
		}
		return 0;
	}
}
