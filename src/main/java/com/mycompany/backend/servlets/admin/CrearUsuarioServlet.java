/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.admin;

import com.mycompany.backend.db.UsuarioDB;
import com.mycompany.backend.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
@WebServlet("/Admin/usuarios/crear")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5 MB
public class CrearUsuarioServlet extends HttpServlet {

    private final UsuarioDB usuarioDB = new UsuarioDB();

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
            out.println("<title>Servlet CrearUsuarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearUsuarioServlet at " + request.getContextPath() + "</h1>");
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
        req.getRequestDispatcher("/Admin/Usuarios/crearUsuario.jsp").forward(req, resp);
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
        String rol = req.getParameter("rol"); // Aquí el admin elige el rol
        boolean activo = "on".equals(req.getParameter("activo"));

        Part fotoPart = req.getPart("foto");
        byte[] fotoBytes = null;
        if (fotoPart != null && fotoPart.getSize() > 0) {
            try (InputStream in = fotoPart.getInputStream()) {
                fotoBytes = in.readAllBytes();
            }
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setOrganizacion(organizacion);
        u.setCorreo(correo);
        u.setTelefono(telefono);
        u.setIdentificacion(identificacion);
        u.setPassword(password);
        u.setRol(rol);
        u.setActivo(activo);
        u.setFoto(fotoBytes);

        try {
            usuarioDB.crear(u);
            resp.sendRedirect(req.getContextPath() + "/Admin/Usuarios/listar?ok=Usuario+creado");
        } catch (SQLException e) {
            req.setAttribute("error", "Error al crear usuario: " + e.getMessage());
            req.getRequestDispatcher("/Admin/Usuarios/crearUsuario.jsp").forward(req, resp);
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
