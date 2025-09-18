/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.asistencias;

import com.mycompany.backend.db.ActividadDB;
import com.mycompany.backend.db.AsistenciaDB;
import com.mycompany.backend.db.CongresoDB;
import com.mycompany.backend.db.InscripcionDB;
import com.mycompany.backend.db.SalonDB;
import com.mycompany.backend.db.UsuarioDB;
import com.mycompany.backend.model.Actividad;
import com.mycompany.backend.model.Congreso;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author sofia
 */
@WebServlet("/Asistencias/registrar")
public class RegistrarAsistenciaServlet extends HttpServlet {

    private final AsistenciaDB asistenciaDB = new AsistenciaDB();
    private final ActividadDB actividadDB = new ActividadDB();
    private final CongresoDB congresoDB = new CongresoDB();
    private final SalonDB salonDB = new SalonDB();
    private final UsuarioDB usuarioDB = new UsuarioDB(); // Debe tener findByEmailOrIdentificacion
    private final InscripcionDB inscripcionDB = new InscripcionDB();

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
            out.println("<title>Servlet RegistrarAsistenciaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegistrarAsistenciaServlet at " + request.getContextPath() + "</h1>");
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

        HttpSession session = req.getSession(false);

        try {
            // Datos para selects en el formulario
            List<Congreso> congresos = congresoDB.findAll();
            List<Actividad> actividades = actividadDB.findAllJoinBasic();
            req.setAttribute("congresos", congresos);
            req.setAttribute("actividades", actividades);

            req.getRequestDispatcher("/Asistencias/registrarAsistencia.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar datos: " + e.getMessage());
            req.getRequestDispatcher("/Asistencias/registrarAsistencias.jsp").forward(req, resp);
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
            int actividadId = Integer.parseInt(req.getParameter("actividad_id"));
            String identificador = req.getParameter("usuario"); // correo o DPI/identificación

            // 1) Validar actividad existe
            Actividad act = actividadDB.findById(actividadId);
            if (act == null) {
                resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                        + URLEncoder.encode("La actividad no existe", StandardCharsets.UTF_8));
                return;
            }

            // 2) Validar que la marca sea en el día correcto y dentro de la ventana de horario
            LocalDate hoy = LocalDate.now();
            LocalTime ahora = LocalTime.now();
            // ventana permisiva: 15 min antes y 15 min después
            LocalTime iniVentana = act.getHoraInicio().minusMinutes(15);
            LocalTime finVentana = act.getHoraFin().plusMinutes(15);

            if (!hoy.equals(act.getFecha()) || ahora.isBefore(iniVentana) || ahora.isAfter(finVentana)) {
                resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                        + URLEncoder.encode("Fuera de la ventana de registro para esta actividad", StandardCharsets.UTF_8));
                return;
            }

            // 3) Buscar usuario por correo o identificación
            Usuario u = usuarioDB.findByEmailOrIdentificacion(identificador);
            if (u == null) {
                resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                        + URLEncoder.encode("Usuario no encontrado", StandardCharsets.UTF_8));
                return;
            }
            if (!u.isActivo()) {
                resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                        + URLEncoder.encode("Usuario inactivo", StandardCharsets.UTF_8));
                return;
            }

            // 4) Evitar duplicados
            if (asistenciaDB.existeAsistencia(actividadId, u.getId())) {
                resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                        + URLEncoder.encode("La asistencia ya fue registrada para este usuario", StandardCharsets.UTF_8));
                return;
            }

            // 5) Si es un taller, validar cupo y/o inscripción
            if ("TALLER".equalsIgnoreCase(act.getTipo())) {
                // Validar cupo
                int inscritos = asistenciaDB.contarAsistencias(actividadId);
                if (inscritos >= act.getCupo()) {
                    resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                            + URLEncoder.encode("El taller ya alcanzó su cupo máximo", StandardCharsets.UTF_8));
                    return;
                }

                // Validar inscripción (si tienes tabla inscripcion)
                boolean inscrito = inscripcionDB.existeInscripcion(u.getId(), actividadId);
                if (!inscrito) {
                    resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                            + URLEncoder.encode("El usuario no está inscrito en este taller", StandardCharsets.UTF_8));
                    return;
                }
            }

            // 6) Registrar
            asistenciaDB.registrar(actividadId, u.getId(), hoy);

            resp.sendRedirect(req.getContextPath() + "/Asistencias/listar?actividadId=" + actividadId
                    + "&ok=" + URLEncoder.encode("Asistencia registrada", StandardCharsets.UTF_8));

        } catch (SQLException e) {
            resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                    + URLEncoder.encode("Error al registrar asistencia: " + e.getMessage(), StandardCharsets.UTF_8));
        } catch (Exception ex) {
            resp.sendRedirect(req.getContextPath() + "/Asistencias/registrar?error="
                    + URLEncoder.encode("Datos inválidos", StandardCharsets.UTF_8));
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
