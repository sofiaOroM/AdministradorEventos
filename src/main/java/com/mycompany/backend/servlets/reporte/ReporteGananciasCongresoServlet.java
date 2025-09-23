/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.reporte;

import com.mycompany.backend.db.ReporteDB;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sofia
 */
@WebServlet("/Reportes/gananciasCongreso")
public class ReporteGananciasCongresoServlet extends HttpServlet {
    private final ReporteDB reporteDB = new ReporteDB();
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
            out.println("<title>Servlet ReporteGananciasCongresoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReporteGananciasCongresoServlet at " + request.getContextPath() + "</h1>");
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
            String inicio = req.getParameter("fechaInicio");
            String fin = req.getParameter("fechaFin");

            List<Map<String,Object>> lista = reporteDB.gananciasPorCongreso(
                congresoId != null && !congresoId.isBlank() ? Integer.parseInt(congresoId) : null,
                inicio != null && !inicio.isBlank() ? java.sql.Date.valueOf(inicio) : null,
                fin != null && !fin.isBlank() ? java.sql.Date.valueOf(fin) : null
            );

            req.setAttribute("ganancias", lista);
            req.getRequestDispatcher("/Reportes/gananciasCongreso.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Error en reporte de ganancias: " + e.getMessage());
            req.getRequestDispatcher("/Reportes/gananciasCongreso.jsp").forward(req, resp);
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
