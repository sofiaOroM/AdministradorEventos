/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.admin.instituciones;

import com.mycompany.backend.db.InstitucionDB;
import com.mycompany.backend.model.Institucion;
import com.mycompany.backend.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sofia
 */
@WebServlet("/Admin/Instituciones/listar")
public class ListarInstitucionesServlet extends HttpServlet {

    private final InstitucionDB institucionDB = new InstitucionDB();

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
            out.println("<title>Servlet ListarInstitucionesServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListarInstitucionesServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession s = req.getSession(false);
        Usuario u = (s != null) ? (Usuario) s.getAttribute("usuario") : null;
        if (u == null || !"admin_sistema".equalsIgnoreCase(u.getRol())) {
            String msg = URLEncoder.encode("Acceso restringido a administradores de sistema", StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/Participantes/menu?error=" + msg);
            return;
        }

        try {
            List<Institucion> instituciones = institucionDB.listar();
            req.setAttribute("instituciones", instituciones);
            req.getRequestDispatcher("/Admin/Instituciones/listadoInstituciones.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar instituciones: " + e.getMessage());
            req.getRequestDispatcher("/Admin/Instituciones/listadoInstituciones.jsp").forward(req, resp);
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
