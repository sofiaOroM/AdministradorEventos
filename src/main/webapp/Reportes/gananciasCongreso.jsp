<%-- 
    Document   : gananciasCongreso
    Created on : 22/09/2025, 23:59:12
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reporte de Ganancias por Congreso</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Ganancias por Congreso</h2>
        <form method="get" class="row g-3 mb-3">
            <div class="col"><input type="number" name="congresoId" placeholder="ID Congreso" class="form-control"></div>
            <div class="col"><input type="date" name="fechaInicio" class="form-control"></div>
            <div class="col"><input type="date" name="fechaFin" class="form-control"></div>
            <div class="col"><button class="btn btn-primary">Filtrar</button></div>
        </form>
        <table class="table table-striped">
            <thead><tr><th>ID</th><th>Título</th><th>Recaudado</th><th>Comisión</th><th>Ganancia</th></tr></thead>
            <tbody>
                <c:forEach var="g" items="${ganancias}">
                    <tr>
                        <td>${g.id}</td>
                        <td>${g.titulo}</td>
                        <td>${g.recaudado}</td>
                        <td>${g.comision}</td>
                        <td>${g.ganancia}</td>
                    </tr>
                </c:forEach>
            </tbody>
            <a class="btn btn-outline-primary"
               href="${pageContext.request.contextPath}/Reportes/exportar/html?tipo=gananciasCongreso&inicio=${param.inicio}&fin=${param.fin}">
                Exportar Ganancias (HTML)
            </a>
        </table>
        <a class="btn btn-outline-primary"    <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
    </body>
</html>
