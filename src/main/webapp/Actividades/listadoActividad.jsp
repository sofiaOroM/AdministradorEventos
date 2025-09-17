<%-- 
    Document   : listado
    Created on : 06/09/2025, 19:46:19
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
      <meta charset="UTF-8">
      <title>Actividades</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Actividades del Congreso</h2>
        <table class="table table-striped">
        <thead><tr><th>ID</th><th>Nombre</th><th>Tipo</th><th>Horario</th><th>Salón</th><th>Cupo</th></tr></thead>
        <tbody>
            <c:forEach var="a" items="${actividades}">
                <tr>
                    <td>${a.id}</td>
                    <td>${a.nombre}</td>
                    <td>${a.tipo}</td>
                    <td>${a.horaInicio} - ${a.horaFin}</td>
                    <td>${a.salonNombre}</td>
                    <td><c:out value="${a.cupo}" default="-" /></td>

                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/Actividades/eliminar" style="display:inline">
                            <input type="hidden" name="id" value="${a.id}">
                            <input type="hidden" name="congresoId" value="${a.congresoId}">
                        <button class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar esta actividad?')">Eliminar</button>
                        </form>
                        <a href="${pageContext.request.contextPath}/Actividades/editarActividad.jsp?id=${a.id}" class="btn btn-warning btn-sm">Editar</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/Actividades/crearActividad.jsp?congresoId=${param.congresoId}" class="btn btn-success">Nueva Actividad</a>
        <a href="${pageContext.request.contextPath}/Eventos/listar" class="btn btn-secondary">Volver</a>
    </body>
</html>
