<%-- 
    Document   : editarPerfil
    Created on : 20/09/2025, 21:11:20
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Perfil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-5">
    <h2>Editar Perfil</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/Participantes/editarPerfil">
        <div class="mb-3">
            <label class="form-label">Nombre</label>
            <input type="text" name="nombre" value="${usuario.nombre}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Correo</label>
            <input type="email" name="correo" value="${usuario.correo}" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Teléfono</label>
            <input type="text" name="telefono" value="${usuario.telefono}" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Organización</label>
            <input type="text" name="organizacion" value="${usuario.organizacion}" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Identificación</label>
            <input type="text" name="identificacion" value="${usuario.identificacion}" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Contraseña</label>
            <input type="password" name="password" value="${usuario.password}" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Rol</label>
            <input type="text" value="${usuario.rol}" class="form-control" readonly>
        </div>

        <button type="submit" class="btn btn-primary">Guardar cambios</button>
        <a href="${pageContext.request.contextPath}/Participantes/menu.jsp" class="btn btn-secondary">Cancelar</a>
    </form>
</body>
</html>