<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<title>
			Guess Film
		</title>
	</head>
	
	<body>
		<form action="http://localhost:8080/GuessFilm/main" method=POST>
		<H1>
			Выберите режим работы
		</H1>
		<table>
			<tr>			
				<td><input type="submit" value="guess" name="guess"/></td>		
				<td><input type="submit" value="train" name="train"/></td>			
				<td><input type="submit" value="question" name="question"/></td>
				<td><input type="submit" value="film" name="film"/></td>
				<td><input type="submit" value="sample" name="sample"/></td>
			</tr>
		</table>
		</form>
	</body>
</html>