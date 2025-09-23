<%-- 
    Document   : participantes
    Created on : 19/09/2025, 23:42:53
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reporte de Participantes</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Listado de Participantes</h2>

        <form method="get" class="mb-3">
            <input type="hidden" name="congresoId" value="${param.congresoId}">
            <label>Filtrar por tipo:</label>
            <select name="tipo" onchange="this.form.submit()" class="form-select">
                <option value="">-- Todos --</option>
                <option value="ponente">Ponente</option>
                <option value="tallerista">Tallerista</option>
                <option value="asistente">Asistente</option>
                <option value="invitado">Ponente invitado</option>
            </select>
        </form>

        <table class="table table-striped">
            <thead>
                <tr><th>ID</th><th>Nombre</th><th>Organización</th><th>Correo</th><th>Teléfono</th><th>Tipo</th></tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${participantes}">
                <tr>
                    <td>${u.identificacion}</td>
                    <td>${u.nombre}</td>
                    <td>${u.organizacion}</td>
                    <td>${u.correo}</td>
                    <td>${u.telefono}</td>
                    <td>${u.rol}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
