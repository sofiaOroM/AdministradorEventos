<%-- 
    Document   : listado
    Created on : 06/09/2025, 19:46:19
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Listado de Actividades</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">

        <h2>Actividades del Congreso</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty ok}">
            <div class="alert alert-success">${ok}</div>
        </c:if>

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th>ID</th><th>Nombre</th><th>Descripción</th><th>Tipo</th>
                    <th>Fecha</th><th>Hora inicio</th><th>Hora fin</th><th>Salón</th><th>Cupo</th><th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${actividades}">
                    <tr>
                        <td>${a.id}</td>
                        <td>${a.nombre}</td>
                        <td>${a.descripcion}</td>
                        <td>${a.tipo}</td>
                        <td>${a.fecha}</td>
                        <td>${a.horaInicio}</td>
                        <td>${a.horaFin}</td>
                        <td>${a.salonNombre}</td>
                        <td><c:out value="${a.cupo}" default="-" /></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/Actividades/editar?id=${a.id}" class="btn btn-warning btn-sm">Editar</a>
                            <a href="${pageContext.request.contextPath}/Actividades/eliminar?id=${a.id}" class="btn btn-danger btn-sm">Eliminar</a>
                        </td>
                        <td>
                            <c:if test="${a.tipo eq 'TALLER'}">
                                <c:choose>
                                    <c:when test="${usuario.pagado eq true and usuario.saldo >= a.precio}">
                                        <form method="post" action="${pageContext.request.contextPath}/Inscripciones/inscribir">
                                            <input type="hidden" name="usuario_id" value="${usuario.id}">
                                            <input type="hidden" name="actividad_id" value="${a.id}">
                                            <button type="submit" class="btn btn-sm btn-success">Inscribirse</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">Pago o saldo insuficiente</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/Actividades/nuevo" class="btn btn-success">Nueva Actividad</a>
        <a href="${pageContext.request.contextPath}/Eventos/listar" class="btn btn-secondary">Volver a Congresos</a>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú Principal</a>
    </body>
</html>

