<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Juegos en grupo</title>
</head>
<body>
	Welcome.
	
	<script type="text/javascript">
		var socket=new WebSocket("ws://localhost:8080/JuegosEnGrupo/notificador/notificador");
		socket.onopen = function(event) {
			alert("Open");
		}
		socket.onmessage = function(event) {
			alert(event.data);
		}
	</script>
</body>
</html>