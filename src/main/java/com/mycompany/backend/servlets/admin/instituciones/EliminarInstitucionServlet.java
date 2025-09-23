/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.admin.instituciones;

import com.mycompany.backend.db.InstitucionDB;
import com.mycompany.backend.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
@WebServlet("/Admin/Instituciones/eliminar")
public class EliminarInstitucionServlet extends HttpServlet {
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
            out.println("<title>Servlet EliminarInstitucionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EliminarInstitucionServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession s = req.getSession(false);
        Usuario u = (s != null) ? (Usuario) s.getAttribute("usuario") : null;
        if (u == null || !"admin_sistema".equalsIgnoreCase(u.getRol())) {
            String msg = URLEncoder.encode("Acceso restringido", StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/Participantes/menu?error=" + msg);
            return;
        }

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            institucionDB.eliminar(id);
            String ok = URLEncoder.encode("Institución eliminada", StandardCharsets.UTF_8.toString());
            resp.sendRedirect(req.getContextPath() + "/Admin/Instituciones/listar?ok=" + ok);
        } catch (SQLException e) {
            // Error común: clave foránea en congreso
            String msg = URLEncoder.encode(
                "No se pudo eliminar. Hay congresos asociados a esta institución.",
                StandardCharsets.UTF_8.toString()
            );
            resp.sendRedirect(req.getContextPath() + "/Admin/Instituciones/listar?error=" + msg);
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
