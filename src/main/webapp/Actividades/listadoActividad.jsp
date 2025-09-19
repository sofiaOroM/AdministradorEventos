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
        <title>Actividades</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">

        <h2 class="mb-4">Actividades</h2>

        <!-- Mensajes -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty ok}">
            <div class="alert alert-success">${ok}</div>
        </c:if>

        <!-- Barra de filtro por congreso -->
        <form method="get" action="${pageContext.request.contextPath}/Actividades/listar" class="mb-3">
            <label for="congresoId" class="form-label">Filtrar por congreso:</label>
            <select name="congresoId" id="congresoId" class="form-select" onchange="this.form.submit()">
                <option value="">-- Todos --</option>
                <c:forEach var="c" items="${congresos}">
                    <option value="${c.id}" <c:if test="${c.id eq congresoSeleccionado}">selected</c:if>>
                        ${c.titulo}
                    </option>
                </c:forEach>
            </select>
        </form>

        <!-- Tabla de actividades -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Tipo</th>
                    <th>Fecha</th>
                    <th>Hora</th>
                    <th>Salón</th>
                    <th>Cupo</th>
                    <th>Congreso</th>
                    <th>Acciones</th>
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
                        <td>${a.horaInicio} - ${a.horaFin}</td>
                        <td>${a.salonNombre}</td>
                        <td>
                            <c:choose>
                                <c:when test="${a.tipo eq 'TALLER'}">${a.cupo}</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${a.congresoTitulo}</td>
                        <td>
                            <!-- Botón inscribirse SOLO si es taller -->
                            <c:if test="${a.tipo eq 'TALLER'}">
                                <form method="post" action="${pageContext.request.contextPath}/Talleres/inscribirse" style="display:inline">
                                    <input type="hidden" name="actividad_id" value="${a.id}">
                                    <button class="btn btn-primary btn-sm">Inscribirse</button>
                                </form>
                            </c:if>

                            <!-- Acciones para administradores -->
                            <c:if test="${usuario.rol ne 'participante'}">
                                <a href="${pageContext.request.contextPath}/Actividades/editar?id=${a.id}" 
                                   class="btn btn-warning btn-sm">Editar</a>
                                    <form method="post" action="${pageContext.request.contextPath}/Actividades/eliminar" 
                                      style="display:inline">
                                    <input type="hidden" name="id" value="${a.id}">
                                    <button class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar esta actividad?')">Eliminar</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Botón crear actividad solo para administradores -->
        <c:if test="${usuario.rol ne 'participante'}">
            <a href="${pageContext.request.contextPath}/Actividades/nuevo" class="btn btn-success">Nueva Actividad</a>
        </c:if>

        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>

    </body>
</html>