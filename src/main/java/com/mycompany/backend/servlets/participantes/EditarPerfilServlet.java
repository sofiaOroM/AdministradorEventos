/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.participantes;

import com.mycompany.backend.db.UsuarioDB;
import com.mycompany.backend.model.Usuario;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
@WebServlet("/Participantes/editarPerfil")
public class EditarPerfilServlet extends HttpServlet {

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
            out.println("<title>Servlet EditarPerfil</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditarPerfil at " + request.getContextPath() + "</h1>");
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
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debes+iniciar+sesion");
                return;
            }

            // Recargar desde BD para mostrar datos actualizados
            Usuario u = usuarioDB.encontrarPorId(usuario.getId());
            req.setAttribute("usuario", u);
            req.getRequestDispatcher("/Participantes/editarPerfil.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar perfil: " + e.getMessage());
            req.getRequestDispatcher("/Participantes/menu.jsp").forward(req, resp);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debes+iniciar+sesion");
                return;
            }

            int id = usuario.getId();
            String nombre = req.getParameter("nombre");
            String correo = req.getParameter("correo");
            String telefono = req.getParameter("telefono");
            String organizacion = req.getParameter("organizacion");
            String identificacion = req.getParameter("identificacion");
            String password = req.getParameter("password");

            // Validación básica
            if (nombre == null || nombre.trim().isEmpty()) {
                req.setAttribute("error", "El nombre no puede estar vacío.");
                doGet(req, resp); // recarga el formulario con los datos
                return;
            }

            // Mantener el rol original (no editable)
            String rol = usuario.getRol();

            Usuario actualizado = new Usuario();
            actualizado.setId(id);
            actualizado.setNombre(nombre);
            actualizado.setCorreo(correo);
            actualizado.setTelefono(telefono);
            actualizado.setOrganizacion(organizacion);
            actualizado.setIdentificacion(identificacion);
            actualizado.setPassword(password);
            actualizado.setRol(rol);
            actualizado.setActivo(true);

            usuarioDB.updatePerfil(actualizado);

            // Actualizar datos en sesión
            session.setAttribute("usuario", actualizado);

            resp.sendRedirect(req.getContextPath() + "/Participantes/menu.jsp?ok=Perfil+actualizado");

        } catch (SQLException e) {
            req.setAttribute("error", "Error al actualizar perfil: " + e.getMessage());
            doGet(req, resp);
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
