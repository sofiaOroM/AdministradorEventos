<%-- 
    Document   : crear
    Created on : 01/09/2025, 19:44:06
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Crear Congreso</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Nuevo Congreso</h2>
        <form method="post" action="${pageContext.request.contextPath}/Eventos/crear" class="card p-4 shadow-sm">
            <div class="mb-3"><label>ID</label><input type="text" name="id" class="form-control" required></div>
            <div class="mb-3"><label>Título</label><input type="text" name="titulo" class="form-control" required></div>
            <div class="mb-3"><label>Descripción</label><textarea name="descripcion" class="form-control"></textarea></div>
            <div class="mb-3"><label>Fecha inicio</label><input type="date" name="fecha_inicio" class="form-control" required></div>
            <div class="mb-3"><label>Fecha fin</label><input type="date" name="fecha_fin" class="form-control" required></div>
            <div class="mb-3"><label>Precio</label><input type="number" name="precio" class="form-control" required></div>
            <div class="mb-3"><label>ID Institución</label><input type="number" name="institucion_id" class="form-control" required></div>
            <button class="btn btn-primary">Crear</button>
        </form>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú Principal</a>
    </body>
</html>
