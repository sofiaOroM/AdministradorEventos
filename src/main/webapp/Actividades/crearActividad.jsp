<%-- 
    Document   : crear
    Created on : 05/09/2025, 19:47:47
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
      <meta charset="UTF-8">
      <title>Crear Actividad</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
      <h2>Nueva Actividad</h2>
      <form method="post" action="${pageContext.request.contextPath}/Actividades/crear" class="card p-4 shadow-sm">
        <input type="hidden" name="congreso_id" value="${param.congresoId}">
        <div class="mb-3"><label>Nombre</label><input type="text" name="nombre" class="form-control" required></div>
        <div class="mb-3"><label>Descripción</label><textarea name="descripcion" class="form-control"></textarea></div>
        <div class="mb-3">
          <label>Tipo</label>
          <select name="tipo" class="form-select" required>
            <option value="PONENCIA">PONENCIA</option>
            <option value="TALLER">TALLER</option>
          </select>
        </div>
        <div class="mb-3"><label>Hora inicio</label><input type="time" name="hora_inicio" class="form-control" required></div>
        <div class="mb-3"><label>Hora fin</label><input type="time" name="hora_fin" class="form-control" required></div>
        <div class="mb-3"><label>Salón ID</label><input type="number" name="salon_id" class="form-control" required></div>
        <div class="mb-3"><label>Cupo (solo talleres)</label><input type="number" name="cupo" class="form-control"></div>
        <button class="btn btn-primary">Crear</button>
      </form>
    </body>
</html>
