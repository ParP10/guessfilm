<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<title>
			Основной режим
		</title>
	</head>
	
	<body>
		<form action="http://localhost:8080/GuessFilm/true_film" method=POST>
		<H1>
			Введите, пожалуйста, правильный фильм:
		</H1>
		<table>
			<tr>
				<td><input type="search" name="filmName"/></td>
			</tr>
			<tr>			
				<td><input type="submit" value="ok" name="ok"/></td>			
				<td><input type="submit" value="exit" name="exit"/></td>			
			</tr>
		</table>
		</form>
	</body>
</html>