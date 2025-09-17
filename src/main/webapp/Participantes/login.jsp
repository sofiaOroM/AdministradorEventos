<%-- 
    Document   : login
    Created on : 28/08/2025, 02:23:41
    Author     : sofia
--%>
<%-- 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
      <meta charset="UTF-8">
      <title>Login</title>
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light d-flex justify-content-center align-items-center vh-100">

        <div class="card shadow p-4" style="width: 24rem;">
            <h2 class="text-center mb-4">Iniciar sesión</h2>

            <form method="post" action="${pageContext.request.contextPath}/Participantes/login">
            <div class="mb-3">
                <label class="form-label">Nombre</label>
                <input type="text" name="nombre" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Contraseña</label>
                <input type="password" name="password" class="form-control" required>
            </div>
                <button class="btn btn-primary w-100">Entrar</button>
            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-success mt-3">${msg}</div>
            </c:if>

            <p class="mt-3 text-center">
                <a href="${pageContext.request.contextPath}/Participantes/register">Crear cuenta</a>
            </p>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>

