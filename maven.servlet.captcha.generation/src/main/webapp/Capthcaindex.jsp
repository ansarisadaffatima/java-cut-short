<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>

<html>
<head>
<meta charset="UTF-8">
<title>Captcha Generation</title>
</head>
<body>
	CAPTCHA GENERATION
	<br>
	<br>
	<form method="post" action = "${pageContext.request.contextPath}/captchaGeneration">
		<img src="/maven.servlet.captcha.generation/captchaGeneration"><br>
		Enter Captcha : <input type="text" name="captchText"> <br> 
		<input type="submit" value="Submit">
	</form>

</body>
</html>