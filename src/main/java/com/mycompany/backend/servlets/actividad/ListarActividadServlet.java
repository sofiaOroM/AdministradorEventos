package com.mycompany.backend.servlets.actividad;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
import java.util.List;

/**
 *
 * @author sofia
 */
@WebServlet("/Actividades/listar")
public class ListarActividadServlet extends HttpServlet {
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
            out.println("<title>Servlet ListarActividadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListarActividadServlet at " + request.getContextPath() + "</h1>");
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
            String congresoId = req.getParameter("congresoId");
            List<Actividad> actividades;

            if (congresoId != null && !congresoId.isEmpty()) {
                actividades = actividadDB.findAllByCongreso(congresoId);
                req.setAttribute("congresoSeleccionado", congresoId);
            } else {
                actividades = actividadDB.findAllJoinBasic();
            }

            // 🔹 aquí se cargan los congresos para el combo
            List<Congreso> congresos = congresoDB.findAll();
            req.setAttribute("congresos", congresos);

            req.setAttribute("actividades", actividades);
            req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);

        } catch (SQLException e) {
            req.setAttribute("error", "Error al cargar actividades: " + e.getMessage());
            req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);
        }
    }
        /*String congresoId = req.getParameter("congresoId");
        try {
            List<Actividad> lista = actividadDB.findAllByCongreso(congresoId);
            req.setAttribute("actividades", lista);
            req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/Actividades/listadoActividad.jsp").forward(req, resp);
        }*/
    

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
