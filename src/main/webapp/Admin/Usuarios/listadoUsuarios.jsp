<%-- 
    Document   : listadoUsuarios
    Created on : 23/09/2025, 00:30:02
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Administrar Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-4">
    <h2 class="mb-4">Administrar Usuarios</h2>

    <!-- Mensajes -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty ok}">
        <div class="alert alert-success">${ok}</div>
    </c:if>

    <!-- Botón crear -->
    <a href="${pageContext.request.contextPath}/Admin/usuarios/crear" class="btn btn-success mb-3">Nuevo Usuario</a>

    <!-- Tabla de usuarios -->
    <table class="table table-bordered table-hover align-middle">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Foto</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Identificación</th>
                <th>Rol</th>
                <th>Activo</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${usuarios}">
                <tr>
                    <td>${u.id}</td>
                    <td>
                        <c:if test="${u.foto != null}">
                            <img src="${pageContext.request.contextPath}/Participantes/foto?id=${u.id}" 
                                 class="img-thumbnail" style="max-width:60px; max-height:60px">
                        </c:if>
                        <c:if test="${u.foto == null}">
                            <span class="text-muted">Sin foto</span>
                        </c:if>
                    </td>
                    <td>${u.nombre}</td>
                    <td>${u.correo}</td>
                    <td>${u.identificacion}</td>
                    <td>
                        <span class="badge 
                            <c:choose>
                                <c:when test="${u.rol eq 'admin_sistema'}">bg-danger</c:when>
                                <c:when test="${u.rol eq 'admin_congreso'}">bg-warning</c:when>
                                <c:otherwise>bg-primary</c:otherwise>
                            </c:choose>
                        ">
                            ${u.rol}
                        </span>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${u.activo}">
                                <span class="text-success fw-bold">Sí</span>
                            </c:when>
                            <c:otherwise>
                                <span class="text-danger fw-bold">No</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/Admin/usuarios/editar?id=${u.id}" 
                           class="btn btn-sm btn-warning">Editar</a>
                        <form method="post" action="${pageContext.request.contextPath}/Admin/usuarios/eliminar" 
                              style="display:inline">
                            <input type="hidden" name="id" value="${u.id}">
                            <button class="btn btn-sm btn-danger" onclick="return confirm('¿Eliminar este usuario?')">
                                Eliminar
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Botón volver -->
    <a href="${pageContext.request.contextPath}/Participantes/menu.jsp" class="btn btn-secondary">Volver al menú</a>
</body>
</html>