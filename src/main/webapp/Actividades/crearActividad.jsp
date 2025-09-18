<%-- 
    Document   : crear
    Created on : 05/09/2025, 19:47:47
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Crear Actividad</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">

        <h2>Crear Actividad</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/Actividades/crear">

            <!-- Congreso -->
            <div class="mb-3">
                <label class="form-label">Congreso</label>
                <select name="congreso_id" class="form-select" required>
                    <c:forEach var="c" items="${congresos}">
                        <option value="${c.id}">${c.titulo} (${c.fechaInicio} - ${c.fechaFin})</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Nombre -->
            <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" required>
            </div>

            <!-- Descripción -->
            <div class="mb-3">
                <label class="form-label">Descripción</label>
                <textarea name="descripcion" class="form-control"></textarea>
            </div>

            <!-- Tipo -->
            <div class="mb-3">
                <label class="form-label">Tipo</label>
                <select name="tipo" id="tipo" class="form-select" required>
                    <option value="PONENCIA">Ponencia</option>
                    <option value="TALLER">Taller</option>
                </select>
            </div>

            <!-- Fecha -->
            <div class="mb-3">
                <label class="form-label">Fecha</label>
                <input type="date" name="fecha" class="form-control" required>
            </div>

            <!-- Hora inicio -->
            <div class="mb-3">
                <label class="form-label">Hora inicio</label>
                <input type="time" name="hora_inicio" class="form-control" required>
            </div>

            <!-- Hora fin -->
            <div class="mb-3">
                <label class="form-label">Hora fin</label>
                <input type="time" name="hora_fin" class="form-control" required>
            </div>

            <!-- Salón -->
            <div class="mb-3">
                <label class="form-label">Salón</label>
                <select name="salon_id" class="form-select" required>
                    <c:forEach var="s" items="${salones}">
                        <option value="${s.id}">${s.nombre} (${s.ubicacion})</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Cupo (solo si es taller) -->
            <div class="mb-3" id="cupoGroup">
                <label class="form-label">Cupo (solo si es taller)</label>
                <input type="number" name="cupo" class="form-control" value="0">
            </div>

            <!-- Botones -->
            <button type="submit" class="btn btn-success">Guardar</button>
            <a href="${pageContext.request.contextPath}/Actividades/listar" class="btn btn-secondary">Cancelar</a>
            <a href="${pageContext.request.contextPath}/Participantes/menu.jsp" class="btn btn-secondary">Menú Principal</a>
        </form>

        <!-- Script para mostrar/ocultar cupo -->
        <script>
            const tipoSelect = document.getElementById("tipo");
            const cupoGroup = document.getElementById("cupoGroup");

            function toggleCupo() {
                cupoGroup.style.display = (tipoSelect.value === "TALLER") ? "block" : "none";
            }
            tipoSelect.addEventListener("change", toggleCupo);
            toggleCupo(); // inicializa
        </script>

    </body>
</html>

