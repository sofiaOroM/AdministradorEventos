<%-- 
    Document   : reservasTaller
    Created on : 20/09/2025, 23:57:58
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reporte de Reservas de Taller</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Reservas de Taller</h2>
        <form method="get" class="mb-3">
            <label>ID Taller:</label>
            <input type="number" name="tallerId" class="form-control" required>
            <button class="btn btn-primary mt-2">Ver Reservas</button>
        </form>
        <table class="table table-striped">
            <thead><tr><th>Nombre</th><th>Cupo</th><th>Reservas</th><th>Disponibles</th></tr></thead>
            <tbody>
                <c:forEach var="t" items="${reservas}">
                    <tr>
                        <td>${t.nombre}</td>
                        <td>${t.cupo}</td>
                        <td>${t.reservas}</td>
                        <td>${t.disponibles}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
        <a class="btn btn-sm btn-outline-success"
           href="${pageContext.request.contextPath}/Reportes/exportar/html?tipo=reservasTaller&tallerId=${r.id}">
            Exportar (HTML)
        </a>
    </body>
</html>