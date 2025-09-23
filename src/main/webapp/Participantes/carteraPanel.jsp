<%-- 
    Document   : carteraPanel
    Created on : 18/09/2025, 15:27:29
    Author     : sofia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cartera Digital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg border-0">
                <div class="card-header bg-primary text-white text-center">
                    <h4>Mi Cartera Digital</h4>
                </div>
                <div class="card-body">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <c:if test="${not empty ok}">
                        <div class="alert alert-success">${ok}</div>
                    </c:if>

                    <div class="mb-4 text-center">
                        <h5>Saldo actual</h5>
                        <p class="fs-3 fw-bold text-success">Q ${cartera.saldo}</p>
                    </div>

                    <form method="post" action="${pageContext.request.contextPath}/Cartera/recargar">
                        <input type="hidden" name="usuario_id" value="${usuario.id}">
                        <div class="mb-3">
                            <label class="form-label">Monto a recargar</label>
                            <input type="number" step="0.01" name="monto" class="form-control text-end" placeholder="Ej. 100.00" required>
                        </div>
                        <div class="d-grid gap-2">
                            <button class="btn btn-success btn-lg">Recargar</button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center">
                    <a href="${pageContext.request.contextPath}/Participantes/menu" class="btn btn-outline-secondary">Volver al Menú Principal</a>
                </div>
            </div>

        </div>
    </div>
</div>

</body>
</html>
