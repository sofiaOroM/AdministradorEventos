<%-- 
    Document   : editarInstitucion
    Created on : 20/09/2025, 18:35:48
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Editar Institución</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2>Editar institución</h2>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">${param.error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/Admin/Instituciones/editar" class="row g-3">
            <input type="hidden" name="id" value="${institucion.id}">
            <div class="col-md-6">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" required maxlength="500" value="${institucion.nombre}">
            </div>
            <div class="col-12">
                <button class="btn btn-primary">Guardar cambios</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/Admin/Instituciones/listar">Cancelar</a>
            </div>
        </form>
    </body>
</html>

