<%-- 
    Document   : register
    Created on : 30/08/2025, 02:25:36
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

    <div class="card shadow p-4" style="max-width: 500px; width: 100%;">
        <h2 class="text-center mb-4">Registro de usuario</h2>

        <form method="post"
              action="${pageContext.request.contextPath}/Participantes/register"
              enctype="multipart/form-data">

        <div class="mb-3">
            <label class="form-label">Nombre completo*</label>
            <input type="text" name="nombre" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Organización</label>
            <input type="text" name="organizacion" class="form-control">
        </div>

        <div class="mb-3">
            <label class="form-label">Correo electrónico*</label>
            <input type="email" name="correo" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Teléfono</label>
            <input type="text" name="telefono" class="form-control" placeholder="+502 5555-5555">
        </div>

        <div class="mb-3">
            <label class="form-label">Número de identificación*</label>
            <input type="text" name="identificacion" class="form-control" required placeholder="Puede incluir letras">
        </div>

        <div class="mb-3">
            <label class="form-label">Contraseña*</label>
            <input type="password" name="password" class="form-control" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Foto (imagen)</label>
            <input type="file" name="foto" class="form-control" accept="image/*">
        </div>

        <button type="submit" class="btn btn-primary w-100">Registrar</button>
        </form>

        <c:if test="${not empty error}">
          <div class="alert alert-danger mt-3"><c:out value="${error}"/></div>
        </c:if>
        <c:if test="${not empty ok}">
          <div class="alert alert-success mt-3"><c:out value="${ok}"/></div>
        </c:if>

        <p class="mt-3 text-center">
          <a href="${pageContext.request.contextPath}/Participantes/login">¿Ya tienes cuenta? Inicia sesión</a>
        </p>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


