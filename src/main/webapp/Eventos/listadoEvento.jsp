<%-- 
    Document   : listado
    Created on : 02/09/2025, 19:39:08
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Congresos</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Lista de Congresos</h2>
        <table class="table table-striped">
            <thead><tr><th>ID</th><th>Título</th><th>Fechas</th><th>Precio</th><th>Institución</th></tr></thead>
            <tbody>
                <c:forEach var="c" items="${congresos}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.titulo}</td>
                    <td>${c.fechaInicio} - ${c.fechaFin}</td>
                    <td>${c.precio}</td>
                    <td>${c.institucionNombre}</td>
                    
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/Eventos/eliminar" style="display:inline">
                          <input type="hidden" name="id" value="${c.id}">
                          <button class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar este congreso?')">Eliminar</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/Eventos/editarEvento.jsp?id=${c.id}" class="btn btn-warning btn-sm">Editar</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/Eventos/crearEvento.jsp" class="btn btn-success">Nuevo Congreso</a>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
    </body>
</html>
