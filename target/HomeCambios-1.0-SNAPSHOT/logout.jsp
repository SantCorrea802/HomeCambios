<%
    session.invalidate(); // invalida la sesión
    response.sendRedirect("index.html"); // vuelve a la pagina de inicio
%>
