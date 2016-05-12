<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ page import="com.pedroma.juegosEnGrupo.server.dominio.*" %>
<%@ page import="edu.uclm.esi.common.server.domain.*" %>
<%@ page import="edu.uclm.esi.common.server.persistence.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		int idGame=Integer.parseInt(request.getParameter("idGame"));
		Manager manager=Manager.get();
		Game game=manager.findGameById(idGame);
	%>
	<input type="button" onclick="testWS()">
	Has elegido el juego nº <%= idGame %>, <%= game.getName() %> 
	
	<script type="text/javascript">
	function testWS() {
		if ("WebSocket" in window) {
			var socket=new WebSocket("ws://localhost:8080/JuegosEnGrupo/notificador");
			document.write(socket);
			/*socket.onmessage = function(evento) {
				var textoJSON=evento.data;
				var mensaje=JSON.parse(textoJSON);
				if (mensaje.tipo=="error") {
					alert(mensaje.texto);
				} else if (mensaje.tipo=="teReta") {
					jugadorLocal.recibirReto(mensaje.retador);
				} else if (mensaje.tipo=="partida") {
					jugadorLocal.mostrarPartida(mensaje);
				} else if (mensaje.tipo=="avisoDeLlegada") {
					var recienLlegado=mensaje.emailDelLlegado;
					addJugador(recienLlegado);
				} else if (mensaje.tipo=="rechazoDeReto") {
					mostrarRechazoDeReto();
				}
			};*/
			socket.send("Hola");
		} else {
			document.write("No hay websockets");
		}
	}
	</script>
</body>
</html>