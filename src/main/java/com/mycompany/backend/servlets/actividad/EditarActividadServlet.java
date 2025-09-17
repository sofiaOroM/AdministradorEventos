/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.actividad;

import com.mycompany.backend.db.ActividadDB;
import com.mycompany.backend.model.Actividad;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author sofia
 */
@WebServlet("/Actividades/editar")
public class EditarActividadServlet extends HttpServlet {
    private final ActividadDB actividadDB = new ActividadDB();

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
            Time inicio = Time.valueOf(LocalTime.parse(req.getParameter("hora_inicio")));
            Time fin = Time.valueOf(LocalTime.parse(req.getParameter("hora_fin")));
            int salonId = Integer.parseInt(req.getParameter("salon_id"));

            if (actividadDB.existeConflictoHorario(salonId, inicio, fin, id)) {
                req.setAttribute("error", "El horario entra en conflicto con otra actividad de este salón.");
                req.getRequestDispatcher("/Actividades/editarActividad.jsp").forward(req, resp);
                return;
            }

            Actividad a = new Actividad();
            a.setId(Integer.parseInt(req.getParameter("id")));
            a.setNombre(req.getParameter("nombre"));
            a.setDescripcion(req.getParameter("descripcion"));
            a.setHoraInicio(LocalTime.parse(req.getParameter("hora_inicio")));
            a.setHoraFin(LocalTime.parse(req.getParameter("hora_fin")));
            a.setSalonId(Integer.parseInt(req.getParameter("salon_id")));
            a.setCongresoId(req.getParameter("congreso_id"));

            if ("TALLER".equalsIgnoreCase(req.getParameter("tipo"))) {
                a.setCupo(Integer.parseInt(req.getParameter("cupo")));
            }

            actividadDB.update(a);
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?congresoId=" + a.getCongresoId());
        } catch (SQLException e) {
            req.setAttribute("error", "Error al editar actividad: " + e.getMessage());
            req.getRequestDispatcher("/Actividades/editarActividad.jsp").forward(req, resp);
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
