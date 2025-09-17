<%-- 
    Document   : editar
    Created on : 16/09/2025, 19:51:14
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
      <meta charset="UTF-8">
      <title>Editar Actividad</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
      <h2>Editar Actividad</h2>
      <form method="post" action="${pageContext.request.contextPath}/Actividades/editar" class="card p-4 shadow-sm">
        <input type="hidden" name="id" value="${a.id}">
        <input type="hidden" name="congreso_id" value="${a.congresoId}">
        <div class="mb-3"><label>Nombre</label><input type="text" name="nombre" class="form-control" value="${a.nombre}" required></div>
        <div class="mb-3"><label>Descripción</label><textarea name="descripcion" class="form-control">${a.descripcion}</textarea></div>
        <div class="mb-3"><label>Tipo</label>
          <input type="text" class="form-control" value="${a.tipo}" readonly>
          <!-- Restricción: no se puede cambiar el tipo -->
        </div>
        <div class="mb-3"><label>Hora inicio</label><input type="time" name="hora_inicio" class="form-control" value="${a.horaInicio}" required></div>
        <div class="mb-3"><label>Hora fin</label><input type="time" name="hora_fin" class="form-control" value="${a.horaFin}" required></div>
        <div class="mb-3"><label>Salón ID</label><input type="number" name="salon_id" class="form-control" value="${a.salonId}" required></div>
        <c:if test="${a.tipo eq 'TALLER'}">
          <div class="mb-3"><label>Cupo</label><input type="number" name="cupo" class="form-control" value="${a.cupo}" required></div>
        </c:if>
        <button class="btn btn-primary">Guardar cambios</button>
        <a href="${pageContext.request.contextPath}/Actividades/listar?congresoId=${a.congresoId}" class="btn btn-secondary">Cancelar</a>
      </form>
    </body>
</html>
