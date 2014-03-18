package guessFilm.web;

import java.io.IOException;
import java.sql.SQLException;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SampleServlet extends HttpServlet {
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)	throws Exception {
		req.setCharacterEncoding("UTF-8");
		getServletContext().getRequestDispatcher("/SampleFrame.jsp").forward(req, resp);
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
}
