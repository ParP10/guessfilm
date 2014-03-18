<%@ page contentType="text/html; charset=UTF-8" %>

<html>
	<head>
		<title>
			ТЕСТ
		</title>
	</head>
	
	<body>
		<form action="http://localhost:8080/GuessFilm/new_sample" method=POST>
		<table>
			<tr>
				<td><input type="color" name="1"/></td>		
			</tr>
			<tr>
				<td><input type="range" name="2"/></td>		
			</tr>
			<tr>
				<td><input type="search" name="3"/></td>		
			</tr>
			<tr>
				<td><input type="date" name="4"/></td>		
			</tr>
			<tr>
				<td><input type="url" name="5"/></td>		
			</tr>
		</table>
		</form>
	</body>
</html>