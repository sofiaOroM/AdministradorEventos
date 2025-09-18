<%-- 
    Document   : listadoAsistencias
    Created on : 17/09/2025, 19:29:05
    Author     : sofia
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Asistencias por actividad</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2>Asistencias — ${actividad.nombre} (${actividad.fecha} ${actividad.horaInicio}-${actividad.horaFin})</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty param.ok}">
            <div class="alert alert-success">${param.ok}</div>
        </c:if>

        <table class="table table-striped">
            <thead class="table-dark">
                <tr>
                    <th>#</th><th>Participante</th><th>Correo</th><th>Fecha</th><th>Hora registro</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${asistencias}" varStatus="st">
                    <tr>
                        <td>${st.index + 1}</td>
                        <td>${a.usuarioNombre}</td>
                        <td>${a.usuarioCorreo}</td>
                        <td>${a.fecha}</td>
                        <td>${a.horaRegistro}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/Asistencias/registrar">Registrar otra asistencia</a>
        <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/Participantes/menu">Menú</a>
    </body>
</html>

