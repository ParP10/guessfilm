<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.lang.Double" %>
<%@ page import = "guessFilm.model.Film" %>
<%@ page import = "guessFilm.model.Question" %>
<%@ page import = "guessFilm.model.Pair" %>
<%@ page import = "java.util.ArrayList" %>
<jsp:useBean id="film" class="guessFilm.model.Film"/>
<jsp:useBean id="question" class="guessFilm.model.Question"/>


<html>
	<head>
		<title>
			Основной режим
		</title>
	</head>
	
	<body>
		<form action="guess_film" method=POST>
		<%  question = (Question) request.getAttribute("question"); %>
		<% if (question != null) { %>
		<H1>
			ВОПРОС:
		</H1>
		<table>
			<tr>
				<%= question.getName() %>
				
			</tr>
		</table>
		<table>
			<tr>			
				<td><input type="submit" value="ДА" name="yes"/></td>			
				<td><input type="submit" value="Не знаю" name="do_not_know"/></td>			
				<td><input type="submit" value="НЕТ" name="no"/></td>
				<td><input type="submit" value="Завершить работу" name="end"/></td>
			</tr>
		</table>
		<% } %>
		<H1>
			Текущий результат:
		</H1>
		<table>
			<tr>
				<% film = (Film) request.getAttribute("film"); %>
				<%= film.getName() %>
				<%// if(film.getName() != "") { %>
				<%// ArrayList <Pair <Film, Double> > curDistrib = (ArrayList<Pair<Film, Double> >) request.getAttribute("curDistrib"); %>
				<%// for (int i = 0; i < 20; i++) { %>
				<%//= curDistrib.get(i).getFirst().getName() %>
				<%//= curDistrib.get(i).getSecond() %>
				<%//} %>
				<%//} %>
				
			</tr>
		</table>
		</form>
	</body>
</html>