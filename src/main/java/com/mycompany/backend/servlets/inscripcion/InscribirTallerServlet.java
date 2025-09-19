/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.inscripcion;

import com.mycompany.backend.db.ActividadDB;
import com.mycompany.backend.db.InscripcionDB;
import com.mycompany.backend.db.PagoDB;
import com.mycompany.backend.model.Actividad;
import com.mycompany.backend.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
@WebServlet("/Talleres/inscribirse")
public class InscribirTallerServlet extends HttpServlet {

    private final InscripcionDB inscripcionDB = new InscripcionDB();
    private final ActividadDB actividadDB = new ActividadDB();
    private final PagoDB pagoDB = new PagoDB();

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
            out.println("<title>Servlet InscribirTallerServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InscribirTallerServlet at " + request.getContextPath() + "</h1>");
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
    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if (usuario == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Debes+iniciar+sesion");
                return;
            }
            int usuarioId = usuario.getId();
            int actividadId = Integer.parseInt(req.getParameter("actividad_id"));

            Actividad act = actividadDB.findById(actividadId);

            if (!"TALLER".equalsIgnoreCase(act.getTipo())) {
                resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=Solo+se+puede+inscribir+en+talleres");
                return;
            }

            int inscritos = inscripcionDB.contarInscritos(actividadId);
            if (inscritos >= act.getCupo()) {
                resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=Taller+lleno");
                return;
            }

            if (inscripcionDB.existeInscripcion(usuarioId, actividadId)) {
                resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=Ya+estas+inscrito+en+este+taller");
                return;
            }

            inscripcionDB.inscribir(usuarioId, actividadId);
            String mensaje = "Inscripción exitosa";
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?ok="
                    + URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString()));
            //resp.sendRedirect(req.getContextPath() + "/Actividades/listar?ok=Inscripción+exitosa");

        } catch (SQLException e) {
            req.setAttribute("error", "Error al inscribirse: " + e.getMessage());
            req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);
        }
    }*/
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    try {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=" + 
                    URLEncoder.encode("Debes iniciar sesión", StandardCharsets.UTF_8));
            return;
        }

        int usuarioId = usuario.getId();
        int actividadId = Integer.parseInt(req.getParameter("actividad_id"));

        // Buscar la actividad
        Actividad act = actividadDB.findById(actividadId);

        // Validar que sea taller
        if (!"TALLER".equalsIgnoreCase(act.getTipo())) {
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=" +
                    URLEncoder.encode("Solo te puedes inscribir en talleres", StandardCharsets.UTF_8));
            return;
        }

        // 🚨 Validar que el congreso de esta actividad esté pagado
        String congresoId = act.getCongresoId();
        if (!pagoDB.existePagoConfirmado(usuarioId, congresoId)) {
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=" +
                    URLEncoder.encode("Debes pagar el congreso antes de inscribirte en sus talleres", StandardCharsets.UTF_8));
            return;
        }

        // Validar cupo
        int inscritos = inscripcionDB.contarInscritos(actividadId);
        if (inscritos >= act.getCupo()) {
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=" +
                    URLEncoder.encode("Taller lleno", StandardCharsets.UTF_8));
            return;
        }

        // Validar si ya estaba inscrito
        if (inscripcionDB.existeInscripcion(usuarioId, actividadId)) {
            resp.sendRedirect(req.getContextPath() + "/Actividades/listar?error=" +
                    URLEncoder.encode("Ya estás inscrito en este taller", StandardCharsets.UTF_8));
            return;
        }

        // Registrar inscripción
        inscripcionDB.inscribir(usuarioId, actividadId);

        resp.sendRedirect(req.getContextPath() + "/Actividades/listar?ok=" +
                URLEncoder.encode("Inscripción exitosa", StandardCharsets.UTF_8));

    } catch (SQLException e) {
        req.setAttribute("error", "Error al inscribirse: " + e.getMessage());
        req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);
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
