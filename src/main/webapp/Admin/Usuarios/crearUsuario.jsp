<%-- 
    Document   : crearUsuario
    Created on : 20/09/2025, 00:41:39
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Crear Usuario</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-4">
        <h2>Crear Usuario</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/Admin/usuarios/crear" enctype="multipart/form-data">
            <div class="mb-3">
                <label class="form-label">Nombre*</label>
                <input type="text" name="nombre" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Organización</label>
                <input type="text" name="organizacion" class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Correo*</label>
                <input type="email" name="correo" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Teléfono</label>
                <input type="text" name="telefono" class="form-control">
            </div>

            <div class="mb-3">
                <label class="form-label">Identificación*</label>
                <input type="text" name="identificacion" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Contraseña*</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Rol*</label>
                <select name="rol" class="form-select" required>
                    <option value="participante">Participante</option>
                    <option value="admin_congreso">Admin Congreso</option>
                    <option value="admin_sistema">Admin Sistema</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Activo</label>
                <input type="checkbox" name="activo" checked>
            </div>

            <div class="mb-3">
                <label class="form-label">Foto</label>
                <input type="file" name="foto" accept="image/*" class="form-control">
            </div>

            <button type="submit" class="btn btn-success">Crear</button>
            <a href="${pageContext.request.contextPath}/Admin/Usuarios/listar" class="btn btn-secondary">Cancelar</a>
        </form>
    </body>
</html>