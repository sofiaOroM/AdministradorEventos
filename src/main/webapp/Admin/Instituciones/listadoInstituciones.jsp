<%-- 
    Document   : listadoInstituciones
    Created on : 20/09/2025, 13:34:53
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Administrar Instituciones</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2 class="mb-3">Administración de Instituciones</h2>

        <c:if test="${not empty param.error}">
            <div class="alert alert-danger">${param.error}</div>
        </c:if>
        <c:if test="${not empty param.ok}">
            <div class="alert alert-success">${param.ok}</div>
        </c:if>

        <!-- Formulario crear -->
        <div class="card mb-4">
            <div class="card-body">
                <form method="post" action="${pageContext.request.contextPath}/Admin/Instituciones/crear" class="row g-2">
                    <div class="col-md-6">
                        <label class="form-label">Nombre de la institución</label>
                        <input type="text" name="nombre" class="form-control" required maxlength="500">
                    </div>
                    <div class="col-md-3 align-self-end">
                        <button class="btn btn-success">Agregar</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Tabla -->
        <table class="table table-striped align-middle">
            <thead>
                <tr><th>ID</th><th>Nombre</th><th class="text-end">Acciones</th></tr>
            </thead>
            <tbody>
                <c:forEach var="i" items="${instituciones}">
                    <tr>
                        <td>${i.id}</td>
                        <td>${i.nombre}</td>
                        <td class="text-end">
                            <a class="btn btn-sm btn-warning"
                               href="${pageContext.request.contextPath}/Admin/Instituciones/editar?id=${i.id}">
                                Editar
                            </a>
                            <form method="post" action="${pageContext.request.contextPath}/Admin/Instituciones/eliminar"
                                  style="display:inline" onsubmit="return confirm('¿Eliminar esta institución?')">
                                <input type="hidden" name="id" value="${i.id}">
                                <button class="btn btn-sm btn-danger">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú principal</a>
    </body>
</html>

