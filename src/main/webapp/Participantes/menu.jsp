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
      <style>
        body{font-family:Arial,sans-serif;margin:24px}
        .grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(220px,1fr));gap:12px;max-width:900px}
        a.btn{display:block;text-align:center;padding:12px 16px;border:1px solid #ccc;border-radius:8px;text-decoration:none}
        a.btn:hover{background:#f5f5f5}
        .tag{display:inline-block;padding:2px 8px;border-radius:999px;background:#eee;margin-left:8px;font-size:12px}
        header{margin-bottom:16px}
      </style>
    </head>
    <body>
      <header>
        <h2>Bienvenido, <%= u.getNombre() %> <span class="tag"><%= u.getRol() %></span></h2>
      </header>

      <div class="grid">
        <!-- Comunes para cualquier usuario logueado -->
        <a class="btn" href="${pageContext.request.contextPath}/Eventos/listar">Ver congresos</a>
        <a class="btn" href="${pageContext.request.contextPath}/Actividades/listadoActividad.jsp">Ver actividades</a>
        <a class="btn" href="${pageContext.request.contextPath}/usuarios/perfil.jsp">Mi perfil</a>
        <a class="btn" href="${pageContext.request.contextPath}/Participantes/logout">Cerrar sesión</a>

        <!-- Opciones para admin_congreso -->
        <%
          if ("admin_congreso".equalsIgnoreCase(u.getRol()) || "admin_sistema".equalsIgnoreCase(u.getRol())) {
        %>
          <a class="btn" href="${pageContext.request.contextPath}/Eventos/crearEvento.jsp">Crear congreso</a>
          <a class="btn" href="${pageContext.request.contextPath}/salones/listado.jsp">Gestionar salones</a>
          <a class="btn" href="${pageContext.request.contextPath}/Actividades/crearActividad.jsp">Crear actividad</a>
          <a class="btn" href="${pageContext.request.contextPath}/asistencias/tomar.jsp">Tomar asistencias</a>
          <a class="btn" href="${pageContext.request.contextPath}/reportes/ganancias-congreso.jsp">Reporte: ganancias por congreso</a>
          <a class="btn" href="${pageContext.request.contextPath}/reportes/asistencia-actividad.jsp">Reporte: asistencia por actividad</a>
          <a class="btn" href="${pageContext.request.contextPath}/reportes/reservas-taller.jsp">Reporte: reservas de talleres</a>
        <%
          }
          if ("admin_sistema".equalsIgnoreCase(u.getRol())) {
        %>
          <a class="btn" href="${pageContext.request.contextPath}/admin/instituciones.jsp">Administrar instituciones</a>
          <a class="btn" href="${pageContext.request.contextPath}/admin/usuarios.jsp">Administrar usuarios</a>
          <a class="btn" href="${pageContext.request.contextPath}/reportes/ganancias-global.jsp">Reporte: ganancias globales</a>
          <a class="btn" href="${pageContext.request.contextPath}/reportes/congresos-por-institucion.jsp">Reporte: congresos por institución</a>
        <%
          }
        %>
      </div>
    </body>
</html>