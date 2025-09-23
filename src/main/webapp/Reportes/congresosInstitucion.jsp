<%-- 
    Document   : congresosInstitucion
    Created on : 21/09/2025, 23:31:05
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Congresos por Institución</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2>Reporte de Congresos por Institución</h2>

        <!-- Filtros -->
        <form method="get" action="${pageContext.request.contextPath}/Reportes/congresosInstitucion" class="row g-3 mb-4">
            <div class="col-md-3">
                <label>Fecha inicio</label>
                <input type="date" name="inicio" class="form-control">
            </div>
            <div class="col-md-3">
                <label>Fecha fin</label>
                <input type="date" name="fin" class="form-control">
            </div>
            <div class="col-md-3">
                <label>Institución</label>
                <select name="institucionId" class="form-select">
                    <option value="">-- Todas --</option>
                    <c:forEach var="i" items="${instituciones}">
                        <option value="${i.id}">${i.nombre}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3 align-self-end">
                <button class="btn btn-primary">Filtrar</button>
            </div>
        </form>

        <!-- Resultados -->
        <c:if test="${not empty reporte}">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Institución</th>
                        <th>Congreso</th>
                        <th>Fechas</th>
                        <th>Precio</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${reporte}">
                        <tr>
                            <td>${c.institucionNombre}</td>
                            <td>${c.titulo}</td>
                            <td>${c.fechaInicio} - ${c.fechaFin}</td>
                            <td>${c.precio}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty reporte}">
            <p>No se encontraron registros.</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
    </body>
</html>

