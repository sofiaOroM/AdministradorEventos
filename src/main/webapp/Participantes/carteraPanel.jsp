<%-- 
    Document   : carteraPanel
    Created on : 18/09/2025, 15:27:29
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cartera Digital</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <h2>Mi Cartera Digital</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${not empty ok}">
        <div class="alert alert-success">${ok}</div>
    </c:if>

    <p>Saldo actual: <strong>${cartera.saldo}</strong></p>

    <form method="post" action="${pageContext.request.contextPath}/Cartera/recargar">
        <input type="hidden" name="usuario_id" value="${usuario.id}">
        <div class="mb-3">
            <label>Monto a recargar</label>
            <input type="number" step="0.01" name="monto" class="form-control" required>
        </div>
        <button class="btn btn-primary">Recargar</button>
    </form>
    <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-secondary">Menú Principal</a>
</body>
</html>
