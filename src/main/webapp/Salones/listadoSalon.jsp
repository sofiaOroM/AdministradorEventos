<%-- 
    Document   : listado.jsp
    Created on : 16/09/2025, 20:21:45
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
      <meta charset="UTF-8">
      <title>Lista de Salones</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
      <h2 class="mb-4">Salones Registrados</h2>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <table class="table table-striped">
        <thead>
          <tr><th>ID</th><th>Nombre</th><th>Ubicación</th><th>Congreso</th><th>Acciones</th></tr>
        </thead>
        <tbody>
          <c:forEach var="s" items="${salones}">
            <tr>
              <td>${s.id}</td>
              <td>${s.nombre}</td>
              <td>${s.ubicacion}</td>
              <td>${s.congresoId}</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/Salones/eliminar" style="display:inline">
                  <input type="hidden" name="id" value="${s.id}"/>
                  <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <a href="${pageContext.request.contextPath}/Salones/crear.jsp" class="btn btn-success">Nuevo salón</a>
      <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Volver</a>
    </body>
</html>
