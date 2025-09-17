/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.participantes;

import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.db.UsuarioDB;

import java.io.*;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.InputStream;
/**
 *
 * @author sofia
 */
@WebServlet("/Participantes/register")
@MultipartConfig( maxFileSize = 5 * 1024 * 1024 ) // 5 MB
public class RegisterServlet extends HttpServlet {
    
    private UsuarioDB usuarioDB = new UsuarioDB();
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
            out.println("<title>Servlet RegisterServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
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
        req.getRequestDispatcher("/Participantes/register.jsp").forward(req, resp);
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
        req.setCharacterEncoding("UTF-8");

        String nombre = req.getParameter("nombre");
        String organizacion = req.getParameter("organizacion");
        String correo = req.getParameter("correo");
        String telefono = req.getParameter("telefono");
        String identificacion = req.getParameter("identificacion");
        String password = req.getParameter("password");

        // Foto (opcional)
        Part fotoPart = req.getPart("foto");
        byte[] fotoBytes = null;
        if (fotoPart != null && fotoPart.getSize() > 0) {
            try (InputStream in = fotoPart.getInputStream()) {
                fotoBytes = in.readAllBytes();
            }
        }

        // Validaciones mínimas
        if (nombre == null || nombre.isBlank() ||
            correo == null || correo.isBlank() ||
            identificacion == null || identificacion.isBlank() ||
            password == null || password.isBlank()) {

            req.setAttribute("error", "Complete los campos obligatorios (*)");
            req.getRequestDispatcher("/Participantes/register.jsp").forward(req, resp);
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(nombre.trim());
        u.setOrganizacion(organizacion != null ? organizacion.trim() : null);
        u.setCorreo(correo.trim());
        u.setTelefono(telefono != null ? telefono.trim() : null);
        u.setIdentificacion(identificacion.trim());
        u.setPassword(password);
        u.setRol("participante");
        u.setActivo(true);
        u.setFoto(fotoBytes);

        try {
            usuarioDB.crear(u);
            // Redirige a login con mensaje
            resp.sendRedirect(req.getContextPath() + "/Participantes/login?msg=Registrado");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "No se pudo registrar: " + e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
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
