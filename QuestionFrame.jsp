<%@ page contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<title>
			Режим добавления вопроса	
		</title>
	</head>
	
	<body>
		<form action="http://localhost:8080/GuessFilm/new_question" method=POST>
		<H1>
			Введите вопрос, который хотите добавить в программу:
		</H1>
		<table>
			<tr>
				<td><input type="text" name="newQuestion"/></td>		
			</tr>
			<tr>
				<td><input type="submit" name="ok" value="OK"/></td>
				<td><input type="submit" name="end" value="End"/></td>
			</tr>
		</table>
		</form>
	</body>
</html>