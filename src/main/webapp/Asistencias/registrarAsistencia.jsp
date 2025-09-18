<%-- 
    Document   : registrar
    Created on : 17/09/2025, 19:28:05
    Author     : sofia
--%>

<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Registrar Asistencia</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2>Registrar asistencia</h2>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">${param.error}</div>
        </c:if>
        <c:if test="${not empty param.ok}">
            <div class="alert alert-success">${param.ok}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/Asistencias/registrar" class="card p-4 shadow-sm">
            <!-- Filtro opcional por congreso (solo info visual) -->
            <div class="mb-3">
                <label class="form-label">Congreso</label>
                <select class="form-select" id="congresoFilter">
                    <option value="">Todos</option>
                    <c:forEach var="c" items="${congresos}">
                        <option value="${c.id}">${c.titulo} (${c.fechaInicio} - ${c.fechaFin})</option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Actividad</label>
                <select name="actividad_id" id="actividadSelect" class="form-select" required>
                    <c:forEach var="a" items="${actividades}">
                        <option value="${a.id}" data-congreso="${a.congresoId}">
                            ${a.nombre} — ${a.fecha} ${a.horaInicio}-${a.horaFin}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Participante (correo o identificación)</label>
                <input type="text" name="usuario" class="form-control" placeholder="email@dominio.com o DPI/ID" required>
            </div>

            <button type="submit" class="btn btn-primary">Registrar</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/Participantes/menu">Volver</a>
        </form>

        <script>
            const filtro = document.getElementById('congresoFilter');
            const actividades = document.getElementById('actividadSelect');
            filtro.addEventListener('change', () => {
                const cid = filtro.value;
                for (const opt of actividades.options) {
                    if (!cid || opt.dataset.congreso === cid)
                        opt.hidden = false;
                    else
                        opt.hidden = true;
                }
                // Seleccionar la primera visible
                for (const opt of actividades.options) {
                    if (!opt.hidden) {
                        actividades.value = opt.value; break;
                    }
                }
            });
        </script>
    </body>
</html>

