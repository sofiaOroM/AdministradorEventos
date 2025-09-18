/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.asistencias;

import com.mycompany.backend.db.AsistenciaDB;
import com.mycompany.backend.db.ActividadDB;
import com.mycompany.backend.model.Asistencia;
import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.model.Actividad;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author sofia
 */
@WebServlet("/Asistencias/listar")
public class ListarAsistenciasServlet extends HttpServlet {
    private final AsistenciaDB asistenciaDB = new AsistenciaDB();
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
            out.println("<title>Servlet ListarAsistenciasServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListarAsistenciasServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int actividadId = Integer.parseInt(req.getParameter("actividadId"));
            Actividad act = actividadDB.findById(actividadId);
            if (act == null) {
                resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=" +
                        URLEncoder.encode("Actividad no encontrada", StandardCharsets.UTF_8));
                return;
            }
            List<Asistencia> lista = asistenciaDB.listarPorActividad(actividadId);
            req.setAttribute("actividad", act);
            req.setAttribute("asistencias", lista);
            req.getRequestDispatcher("/Asistencias/listadoAsistencia.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar asistencias: " + e.getMessage());
            req.getRequestDispatcher("/Asistencias/listadoAsistencia.jsp").forward(req, resp);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
