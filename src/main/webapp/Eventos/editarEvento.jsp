<%-- 
    Document   : editar
    Created on : 16/09/2025, 19:49:22
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Editar Congreso</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <h2>Editar Congreso</h2>
        <form method="post" action="${pageContext.request.contextPath}/Eventos/editar" class="card p-4 shadow-sm">
            <input type="hidden" name="id" value="${c.id}">
            <div class="mb-3"><label>Título</label><input type="text" name="titulo" class="form-control" value="${c.titulo}" required></div>
            <div class="mb-3"><label>Descripción</label><textarea name="descripcion" class="form-control">${c.descripcion}</textarea></div>
            <div class="mb-3"><label>Fecha inicio</label><input type="date" name="fecha_inicio" class="form-control" value="${c.fechaInicio}" required></div>
            <div class="mb-3"><label>Fecha fin</label><input type="date" name="fecha_fin" class="form-control" value="${c.fechaFin}" required></div>
            <div class="mb-3"><label>Precio</label><input type="number" name="precio" class="form-control" value="${c.precio}" required></div>
            <div class="mb-3"><label>ID Institución</label><input type="number" name="institucion_id" class="form-control" value="${c.institucionId}" required></div>
            <button class="btn btn-primary">Guardar cambios</button>
            <a href="${pageContext.request.contextPath}/Eventos/listar" class="btn btn-secondary">Cancelar</a>
        </form>
        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú Principal</a>
    </body>
</html>
