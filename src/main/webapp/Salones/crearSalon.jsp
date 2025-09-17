<%-- 
    Document   : newjspcrear
    Created on : 16/09/2025, 20:22:59
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
      <meta charset="UTF-8">
      <title>Crear Salón</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
      <h2>Registrar Nuevo Salón</h2>
      <form method="post" action="${pageContext.request.contextPath}/Salones/crear">
        <div class="mb-3">
          <label>Nombre</label>
          <input type="text" name="nombre" class="form-control" required/>
        </div>
        <div class="mb-3">
          <label>Ubicación</label>
          <input type="text" name="ubicacion" class="form-control"/>
        </div>
        <div class="mb-3">
          <label>ID Congreso</label>
          <input type="text" name="congreso_id" class="form-control" required/>
        </div>
        <button type="submit" class="btn btn-primary">Guardar</button>
        <a href="${pageContext.request.contextPath}/Salones/listar" class="btn btn-secondary">Cancelar</a>
      </form>
    </body>
</html>
