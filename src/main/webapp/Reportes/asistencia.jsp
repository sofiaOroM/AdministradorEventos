<%-- 
    Document   : asistencia
    Created on : 22/09/2025, 23:46:21
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reporte de Asistencias</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Asistencia por Actividad</h2>
        <form method="get" class="row g-3 mb-3">
            <div class="col"><input type="number" name="actividadId" placeholder="ID Actividad" class="form-control"></div>
            <div class="col"><input type="number" name="salonId" placeholder="ID Salón" class="form-control"></div>
            <div class="col"><input type="date" name="fechaInicio" class="form-control"></div>
            <div class="col"><input type="date" name="fechaFin" class="form-control"></div>
            <div class="col"><button class="btn btn-primary">Filtrar</button></div>
        </form>
        <table class="table table-striped">
            <thead><tr><th>Actividad</th><th>Salón</th><th>Fecha</th><th>Hora</th><th>Asistencias</th></tr></thead>
            <tbody>
                <c:forEach var="a" items="${asistencias}">
                    <tr>
                        <td>${a.actividad}</td>
                        <td>${a.salon}</td>
                        <td>${a.fecha}</td>
                        <td>${a.horaInicio} - ${a.horaFin}</td>
                        <td>${a.asistencias}</td>
                        <td>
                            <!-- Exportar reservas solo de este taller -->
                            <a class="btn btn-sm btn-outline-success"
                               href="${pageContext.request.contextPath}/Reportes/exportar/html?tipo=reservasTaller&tallerId=${a.id}">
                                Exportar Reservas
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
        <a class="btn btn-outline-primary"
           href="${pageContext.request.contextPath}/Reportes/exportar/html?tipo=asistencia
           &actividadId=${param.actividadId}
           &salonId=${param.salonId}
           &fechaInicio=${param.fechaInicio}
           &fechaFin=${param.fechaFin}">
            Exportar Asistencia (HTML)
        </a>
    </body>
</html>

