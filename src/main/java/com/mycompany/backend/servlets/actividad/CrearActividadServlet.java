/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.actividad;

import com.mycompany.backend.db.ActividadDB;
import com.mycompany.backend.db.CongresoDB;
import com.mycompany.backend.model.Actividad;
import com.mycompany.backend.model.Congreso;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author sofia
 */
@WebServlet("/Actividades/crear")
public class CrearActividadServlet extends HttpServlet {

    private final ActividadDB actividadDB = new ActividadDB();
    private final CongresoDB congresoDB = new CongresoDB();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrearActividadServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearActividadServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");
            String tipo = req.getParameter("tipo");
            int salonId = Integer.parseInt(req.getParameter("salon_id"));
            String congresoId = req.getParameter("congreso_id");

            LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
            Time inicio = Time.valueOf(LocalTime.parse(req.getParameter("hora_inicio")));
            Time fin = Time.valueOf(LocalTime.parse(req.getParameter("hora_fin")));

            // Validación: hora de inicio < hora de fin
            if (inicio.after(fin)) {
                String msg = URLEncoder.encode("La hora de inicio no puede ser posterior a la de fin", StandardCharsets.UTF_8.toString());

                resp.sendRedirect(req.getContextPath() + "/Actividades/nuevo?error=" + msg);
                return;
            }

            // Validación: la actividad debe estar dentro de las fechas del congreso
            Congreso congreso = congresoDB.encontrarPorId(congresoId);
            if (congreso == null) {
                String msg = URLEncoder.encode("El congreso no existe", StandardCharsets.UTF_8.toString());
                resp.sendRedirect(req.getContextPath() + "/Actividades/nuevo?error=" + msg);
                return;
            }

            if (fecha.isBefore(congreso.getFechaInicio()) || fecha.isAfter(congreso.getFechaFin())) {
                String msg = URLEncoder.encode("La fecha es inválida", StandardCharsets.UTF_8.toString());
                resp.sendRedirect(req.getContextPath() + "/Actividades/nuevo?error=" + msg);
                return;
            }

            // Validación: no solapamiento en el mismo salón
            if (actividadDB.existeConflictoHorario(salonId, inicio, fin, fecha, null)) {
                String msg = URLEncoder.encode("El salón ya tiene una actividad en ese horario", StandardCharsets.UTF_8.toString());
                resp.sendRedirect(req.getContextPath() + "/Actividades/nuevo?error=" + msg);
                return;
            }

            // Validación: si es TALLER debe tener cupo > 0
            int cupo = 0;
            if ("TALLER".equalsIgnoreCase(tipo)) {
                cupo = Integer.parseInt(req.getParameter("cupo"));
                if (cupo <= 0) {
                    resp.sendRedirect(req.getContextPath() + "/Actividades/nuevo?error=El+cupo+para+talleres+debe+ser+mayor+a+0");
                    return;
                }
            }

            // Guardar la actividad
            Actividad a = new Actividad();
            a.setNombre(nombre);
            a.setDescripcion(descripcion);
            a.setTipo(tipo);
            a.setFecha(fecha);
            a.setHoraInicio(inicio.toLocalTime());
            a.setHoraFin(fin.toLocalTime());
            a.setSalonId(salonId);
            a.setCongresoId(congresoId);
            a.setCupo(cupo);

            actividadDB.insert(a);

            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?congresoId=" + congresoId);

        } catch (SQLException e) {
            req.setAttribute("error", "Error al crear actividad: " + e.getMessage());
            req.getRequestDispatcher("/Actividades/nuevo").forward(req, resp);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
