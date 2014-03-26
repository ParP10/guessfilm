<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "guessFilm.model.Film" %>
<jsp:useBean id="film" class="guessFilm.model.Film"/>

<html>
	<head>
		<title>
			Основной режим
		</title>
	</head>
	
	<body>
		<form action="http://localhost:8080/GuessFilm/result" method=POST>
		<H1>
			Результат:
		</H1>
		<table>
			<tr>
				<% film = (Film) request.getAttribute("film"); %>
				<%= film.getName() %>
			</tr>
		</table>
		<H1>
			Является ли результат искомым?
		</H1>
		<table>
			<tr>			
				<td><input type="submit" value="ДА" name="yes"/></td>			
				<td><input type="submit" value="Не знаю" name="do_not_know"/></td>			
				<td><input type="submit" value="НЕТ" name="no"/></td>
			</tr>
		</table>
		</form>
	</body>
</html>