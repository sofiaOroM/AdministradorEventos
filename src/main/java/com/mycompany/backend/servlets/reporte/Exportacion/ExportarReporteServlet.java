/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.reporte.Exportacion;

import com.mycompany.backend.db.ReporteDB;
import com.mycompany.backend.model.reportes.ReporteGanancias;
import com.mycompany.backend.model.Congreso;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author sofia
 */
@WebServlet("/Reportes/exportar/html")
public class ExportarReporteServlet extends HttpServlet {

    private final ReporteDB reporteDB = new ReporteDB();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String tipo = n(req.getParameter("tipo"));
        if (tipo.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'tipo'.");
            return;
        }

        // Nombre de archivo sugerido
        String filename = "reporte_" + tipo + "_" + System.currentTimeMillis() + ".html";
        resp.setHeader("Content-Disposition", "attachment; filename=" + filename);

        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'><head><meta charset='UTF-8'/>");
            out.println("<title>Export " + esc(tipo) + "</title>");
            // Mini estilos para que se vea bien también en el navegador/Excel
            out.println("<style>table{border-collapse:collapse;width:100%}th,td{border:1px solid #999;padding:6px;font-family:Arial, sans-serif;font-size:12px}th{background:#eee;text-align:left}</style>");
            out.println("</head><body>");

            switch (tipo) {
                case "ganancias": {
                    // filtros: inicio, fin, institucionId (opcional)
                    Date inicio = asDate(req.getParameter("inicio"));
                    Date fin = asDate(req.getParameter("fin"));
                    Integer institucionId = asInt(req.getParameter("institucionId"));

                    List<ReporteGanancias> data = reporteDB.gananciasPorIntervalo(inicio, fin, institucionId);

                    BigDecimal totalRecaudado = BigDecimal.ZERO;
                    BigDecimal totalComision = BigDecimal.ZERO;
                    BigDecimal totalGanancia = BigDecimal.ZERO;

                    out.println("<h2>Reporte de Ganancias por Congreso</h2>");
                    out.println("<p><strong>Rango:</strong> " + esc(s(inicio)) + " a " + esc(s(fin))
                            + (institucionId != null ? " &nbsp;|&nbsp; <strong>Institución ID:</strong> " + institucionId : "")
                            + "</p>");
                    out.println("<table><thead><tr>");
                    out.println("<th>Institución</th><th>Congreso</th><th>Inicio</th><th>Fin</th><th>Precio</th><th>Inscritos</th><th>Recaudado</th><th>Comisión</th><th>Ganancia</th>");
                    out.println("</tr></thead><tbody>");

                    for (ReporteGanancias r : data) {
                        out.println("<tr>");
                        out.println(td(r.getInstitucionNombre()));
                        out.println(td(r.getTitulo()));
                        out.println(td(s(r.getFechaInicio())));
                        out.println(td(s(r.getFechaFin())));
                        out.println(td(cur(r.getPrecio())));
                        out.println(td(String.valueOf(r.getInscritos())));
                        out.println(td(cur(r.getRecaudado())));
                        out.println(td(cur(r.getComision())));
                        out.println(td(cur(r.getGanancia())));
                        out.println("</tr>");

                        totalRecaudado = totalRecaudado.add(nz(r.getRecaudado()));
                        totalComision = totalComision.add(nz(r.getComision()));
                        totalGanancia = totalGanancia.add(nz(r.getGanancia()));
                    }
                    out.println("</tbody>");
                    out.println("<tfoot><tr>");
                    out.println("<th colspan='6' style='text-align:right'>Totales</th>");
                    out.println(th(cur(totalRecaudado)));
                    out.println(th(cur(totalComision)));
                    out.println(th(cur(totalGanancia)));
                    out.println("</tr></tfoot></table>");
                    break;
                }

                case "congresosInstitucion": {
                    Date inicio = asDate(req.getParameter("inicio"));
                    Date fin = asDate(req.getParameter("fin"));
                    Integer institucionId = asInt(req.getParameter("institucionId"));

                    List<Congreso> data = reporteDB.congresosPorInstitucion(inicio, fin, institucionId);

                    out.println("<h2>Congresos por Institución</h2>");
                    out.println("<p><strong>Rango:</strong> " + esc(s(inicio)) + " a " + esc(s(fin))
                            + (institucionId != null ? " &nbsp;|&nbsp; <strong>Institución ID:</strong> " + institucionId : "")
                            + "</p>");
                    out.println("<table><thead><tr>");
                    out.println("<th>Institución</th><th>ID</th><th>Título</th><th>Inicio</th><th>Fin</th><th>Precio</th>");
                    out.println("</tr></thead><tbody>");

                    for (Congreso c : data) {
                        out.println("<tr>");
                        out.println(td(c.getInstitucionNombre()));
                        out.println(td(c.getId()));
                        out.println(td(c.getTitulo()));
                        out.println(td(s(c.getFechaInicio())));
                        out.println(td(s(c.getFechaFin())));
                        out.println(td(cur(c.getPrecio())));
                        out.println("</tr>");
                    }
                    out.println("</tbody></table>");
                    break;
                }

                case "asistencia": {
                    Integer actividadId = asInt(req.getParameter("actividadId"));
                    Integer salonId = asInt(req.getParameter("salonId"));
                    Date fechaInicio = asDate(req.getParameter("fechaInicio"));
                    Date fechaFin = asDate(req.getParameter("fechaFin"));

                    List<Map<String, Object>> data = reporteDB.asistenciaPorActividad(actividadId, salonId, fechaInicio, fechaFin);

                    out.println("<h2>Asistencia por Actividad</h2>");
                    out.println("<p>"
                            + (actividadId != null ? "<strong>Actividad ID:</strong> " + actividadId + " &nbsp;|&nbsp; " : "")
                            + (salonId != null ? "<strong>Salón ID:</strong> " + salonId + " &nbsp;|&nbsp; " : "")
                            + "<strong>Rango:</strong> " + esc(s(fechaInicio)) + " a " + esc(s(fechaFin)) + "</p>");

                    out.println("<table><thead><tr>");
                    out.println("<th>Actividad</th><th>Salón</th><th>Fecha</th><th>Hora Inicio</th><th>Hora Fin</th><th>Asistencias</th>");
                    out.println("</tr></thead><tbody>");

                    for (Map<String, Object> fila : data) {
                        out.println("<tr>");
                        out.println(td(s(fila.get("actividad"))));
                        out.println(td(s(fila.get("salon"))));
                        out.println(td(s(fila.get("fecha"))));
                        out.println(td(s(fila.get("horaInicio"))));
                        out.println(td(s(fila.get("horaFin"))));
                        out.println(td(s(fila.get("asistencias"))));
                        out.println("</tr>");
                    }
                    out.println("</tbody></table>");
                    break;
                }

                case "reservasTaller": {
                    Integer tallerId = asInt(req.getParameter("tallerId"));
                    if (tallerId == null) {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta 'tallerId'.");
                        return;
                    }

                    // Encabezado simple + resumen; si necesitas el detalle de participantes,
                    // invoca tu método de detalle (participantes por taller) y añade otra tabla.
                    List<Map<String, Object>> data = reporteDB.reservasDeTaller(tallerId);

                    out.println("<h2>Reservas de Taller</h2>");
                    out.println("<p><strong>Taller ID:</strong> " + tallerId + "</p>");
                    out.println("<table><thead><tr>");
                    out.println("<th>Nombre</th><th>Cupo</th><th>Reservas</th><th>Disponibles</th>");
                    out.println("</tr></thead><tbody>");

                    for (Map<String, Object> fila : data) {
                        out.println("<tr>");
                        out.println(td(s(fila.get("nombre"))));
                        out.println(td(s(fila.get("cupo"))));
                        out.println(td(s(fila.get("reservas"))));
                        out.println(td(s(fila.get("disponibles"))));
                        out.println("</tr>");
                    }
                    out.println("</tbody></table>");
                    break;
                }

                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de reporte no soportado: " + tipo);
                    return;
            }

            out.println("</body></html>");
            out.flush();

        } catch (SQLException e) {
            throw new ServletException("Error al exportar reporte HTML: " + e.getMessage(), e);
        }
    }

    private static String n(String v) {
        return v == null ? "" : v.trim();
    }

    private static String s(Object v) {
        return v == null ? "" : String.valueOf(v);
    }

    private static String esc(String v) {
        return v == null ? "" : v.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    private static String td(String v) {
        return "<td>" + esc(v) + "</td>";
    }

    private static String td(Object v) {
        return "<td>" + esc(s(v)) + "</td>";
    }

    private static String th(String v) {
        return "<th>" + esc(v) + "</th>";
    }

    private static Integer asInt(String v) {
        try {
            return (v == null || v.isBlank()) ? null : Integer.valueOf(v);
        } catch (Exception e) {
            return null;
        }
    }

    private static Date asDate(String v) {
        try {
            return (v == null || v.isBlank()) ? null : Date.valueOf(v);
        } catch (Exception e) {
            return null;
        }
    }

    private static BigDecimal nz(BigDecimal b) {
        return b == null ? BigDecimal.ZERO : b;
    }

    private static String cur(Object o) {
        if (o == null) {
            return "";
        }
        if (o instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) o;
            return "Q" + bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        if (o instanceof Number) {
            Number n = (Number) o;
            return "Q" + String.format(Locale.US, "%.2f", n.doubleValue());
        }
        return esc(String.valueOf(o));
    }

}
