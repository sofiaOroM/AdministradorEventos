<%-- 
    Document   : menu
    Created on : 28/08/2025, 02:26:26
    Author     : sofia
--%>
<%@ page import="com.mycompany.backend.model.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
  Usuario u = (Usuario) session.getAttribute("usuario");
  if (u == null) {
    response.sendRedirect(request.getContextPath() + "/Participantes/login?msg=Inicia+sesion");
    return;
  }
%>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Menú principal</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 24px;
            }
            header {
                margin-bottom: 20px;
            }
            .tag {
                background: #eee;
                border-radius: 12px;
                padding: 2px 8px;
                font-size: 12px;
            }
            .section-title {
                margin-top: 25px;
                font-size: 18px;
                font-weight: bold;
                border-bottom: 2px solid #ccc;
                padding-bottom: 4px;
            }
            .menu-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
                gap: 12px;
                margin-top: 12px;
            }
            a.btn {
                text-align: center;
                padding: 12px 16px;
                border-radius: 8px;
                font-size: 15px;
            }
        </style>
    </head>
    <body>
        <header>
            <h2>Bienvenido, <%= u.getNombre() %> <span class="tag"><%= u.getRol() %></span></h2>
        </header>

        <!-- Sección común -->
        <div class="section-title">Opciones Generales</div>
        <div class="menu-grid">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Eventos/listar">Ver congresos</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Actividades/listar">Ver actividades</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Participantes/editarPerfil.jsp">Mi perfil</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Cartera/panel">Cartera Digital</a>
            <a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/Participantes/logout">Cerrar sesión</a>
        </div>

        <%
          if ("admin_congreso".equalsIgnoreCase(u.getRol()) || "admin_sistema".equalsIgnoreCase(u.getRol())) {
        %>
        <!-- Sección administración de congresos -->
        <div class="section-title">Administración de Congresos</div>
        <div class="menu-grid">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Eventos/crearEvento.jsp">Crear congreso</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Salones/listar">Gestionar salones</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Actividades/nuevo">Crear actividad</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Asistencias/registrar">Tomar asistencias</a>
        </div>

        <!-- Reportes de administrador de congreso -->
        <div class="section-title">Reportes de Congresos</div>
        <div class="menu-grid">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/gananciasCongreso.jsp">Ganancias por congreso</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/asistencia.jsp">Asistencia por actividad</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/reservasTaller.jsp">Reservas de talleres</a>
        </div>
        <%
          }
          if ("admin_sistema".equalsIgnoreCase(u.getRol())) {
        %>
        <!-- Sección administración del sistema -->
        <div class="section-title">Administración del Sistema</div>
        <div class="menu-grid">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Admin/Instituciones/listar">Administrar instituciones</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Admin/Usuarios/listar">Administrar usuarios</a>
        </div>

        <!-- Reportes globales -->
        <div class="section-title">Reportes Globales</div>
        <div class="menu-grid">
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/participantes.jsp">Participantes</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/ganancias.jsp">Ganancias globales</a>
            <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/Reportes/congresosInstitucion.jsp">Congresos por institución</a>
        </div>
        <%
          }
        %>
    </body>
</html>
