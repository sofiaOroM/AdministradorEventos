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
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author sofia
 */
@WebServlet("/Actividades/editar")
public class EditarActividadServlet extends HttpServlet {
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
            out.println("<title>Servlet EditarActividadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditarActividadServlet at " + request.getContextPath() + "</h1>");
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
            int id = Integer.parseInt(req.getParameter("id"));
            String nombre = req.getParameter("nombre");
            String descripcion = req.getParameter("descripcion");
            String tipo = req.getParameter("tipo"); // no modificable
            int salonId = Integer.parseInt(req.getParameter("salon_id"));
            String congresoId = req.getParameter("congreso_id");
            
            LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
            Time inicio = Time.valueOf(LocalTime.parse(req.getParameter("hora_inicio")));
            Time fin = Time.valueOf(LocalTime.parse(req.getParameter("hora_fin")));

            // Validación: hora de inicio < hora de fin
            if (inicio.after(fin)) {
                req.setAttribute("error", "La hora de inicio no puede ser posterior a la de fin.");
                req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
                return;
            }

            // Validación: actividad dentro de rango de fechas del congreso
            Congreso congreso = congresoDB.encontrarPorId(congresoId);
            if (congreso == null) {
                req.setAttribute("error", "El congreso especificado no existe.");
                req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
                return;
            }
            
            if (fecha.isBefore(congreso.getFechaInicio()) || fecha.isAfter(congreso.getFechaFin())) {
                req.setAttribute("error", "La fecha de la actividad debe estar entre "
                        + congreso.getFechaInicio() + " y " + congreso.getFechaFin());
                req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
                return;
            }

            // Validación: no solapamiento (excluyendo la propia actividad)
            if (actividadDB.existeConflictoHorario(salonId, inicio, fin, fecha, id)) {
                req.setAttribute("error", "El horario entra en conflicto con otra actividad de este salón.");
                req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
                return;
            }

            // Validación: cupo en talleres
            int cupo = 0;
            if ("TALLER".equalsIgnoreCase(tipo)) {
                cupo = Integer.parseInt(req.getParameter("cupo"));
                if (cupo <= 0) {
                    req.setAttribute("error", "El cupo de un taller debe ser mayor que 0.");
                    req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
                    return;
                }
            }

            // Guardar cambios
            Actividad a = new Actividad();
            a.setId(id);
            a.setNombre(nombre);
            a.setDescripcion(descripcion);
            a.setTipo(tipo);
            a.setFecha(fecha);
            a.setHoraInicio(inicio.toLocalTime());
            a.setHoraFin(fin.toLocalTime());
            a.setSalonId(salonId);
            a.setCongresoId(congresoId);
            a.setCupo(cupo);

            actividadDB.update(a);

            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?congresoId=" + congresoId);

        } catch (SQLException e) {
            req.setAttribute("error", "Error al editar actividad: " + e.getMessage());
            req.getRequestDispatcher("/actividades/editar.jsp").forward(req, resp);
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
