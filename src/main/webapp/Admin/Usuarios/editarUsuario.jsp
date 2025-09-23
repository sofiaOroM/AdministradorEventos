<%-- 
    Document   : editarUsuario
    Created on : 20/09/2025, 00:42:38
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container py-4">
    <h2>Editar Usuario</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/Admin/usuarios/editar" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${usuarioEditar.id}">

        <div class="mb-3">
            <label class="form-label">Nombre*</label>
            <input type="text" name="nombre" class="form-control" value="${usuarioEditar.nombre}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Organización</label>
            <input type="text" name="organizacion" class="form-control" value="${usuarioEditar.organizacion}">
        </div>

        <div class="mb-3">
            <label class="form-label">Correo*</label>
            <input type="email" name="correo" class="form-control" value="${usuarioEditar.correo}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Teléfono</label>
            <input type="text" name="telefono" class="form-control" value="${usuarioEditar.telefono}">
        </div>

        <div class="mb-3">
            <label class="form-label">Identificación*</label>
            <input type="text" name="identificacion" class="form-control" value="${usuarioEditar.identificacion}" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Rol*</label>
            <select name="rol" class="form-select" required>
                <option value="participante" <c:if test="${usuarioEditar.rol eq 'participante'}">selected</c:if>>Participante</option>
                <option value="admin_congreso" <c:if test="${usuarioEditar.rol eq 'admin_congreso'}">selected</c:if>>Admin Congreso</option>
                <option value="admin_sistema" <c:if test="${usuarioEditar.rol eq 'admin_sistema'}">selected</c:if>>Admin Sistema</option>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Activo</label>
            <input type="checkbox" name="activo" <c:if test="${usuarioEditar.activo}">checked</c:if>>
        </div>

        <div class="mb-3">
            <label class="form-label">Foto</label>
            <input type="file" name="foto" accept="image/*" class="form-control">
            <c:if test="${usuarioEditar.foto != null}">
                <img src="${pageContext.request.contextPath}/Participantes/foto?id=${usuarioEditar.id}" 
                     class="img-thumbnail mt-2" style="max-width:150px">
            </c:if>
        </div>

        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
        <a href="${pageContext.request.contextPath}/Admin/usuarios/listar" class="btn btn-secondary">Cancelar</a>
    </form>
</body>
</html>