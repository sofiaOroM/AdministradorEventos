<%-- 
    Document   : editar
    Created on : 16/09/2025, 19:51:14
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Editar Actividad</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">

        <h2>Editar Actividad</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/Actividades/editar">
            <input type="hidden" name="id" value="${actividad.id}" />
            <div class="mb-3">
                <label class="form-label">Congreso</label>
                <select name="congreso_id" class="form-select" required>
                    <c:forEach var="c" items="${congresos}">
                        <option value="${c.id}" <c:if test="${c.id == actividad.congresoId}">selected</c:if>>
                            ${c.titulo} (${c.fechaInicio} - ${c.fechaFin})
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" value="${actividad.nombre}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcion" class="form-control">${actividad.descripcion}</textarea>
            </div>

            <div class="mb-3">
                <label class="form-label">Tipo (no modificable)</label>
                <input type="text" class="form-control" value="${actividad.tipo}" readonly>
                <input type="hidden" name="tipo" value="${actividad.tipo}">
            </div>

            <div class="mb-3">
                <label class="form-label">Fecha</label>
                <input type="date" name="fecha" class="form-control" value="${actividad.fecha}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Hora inicio</label>
                <input type="time" name="hora_inicio" class="form-control" value="${actividad.horaInicio}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Hora fin</label>
                <input type="time" name="hora_fin" class="form-control" value="${actividad.horaFin}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Salón</label>
                <select name="salon_id" class="form-select" required>
                    <c:forEach var="s" items="${salones}">
                        <option value="${s.id}" <c:if test="${s.id == actividad.salonId}">selected</c:if>>
                            ${s.nombre} (${s.ubicacion})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${actividad.tipo == 'TALLER'}">
                <div class="mb-3">
                    <label class="form-label">Cupo</label>
                    <input type="number" name="cupo" class="form-control" value="${actividad.cupo}">
                </div>
            </c:if>

            <button type="submit" class="btn btn-success">Guardar cambios</button>
            <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú Principal</a>
        </form>
    </body>
</html>
