/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.reporte;

import com.mycompany.backend.db.ReporteDB;
import com.mycompany.backend.model.Congreso;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author sofia
 */
@WebServlet("/Reportes/congresosInstitucion")
public class ReporteCongresosInstitucionServlet extends HttpServlet {
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
            out.println("<title>Servlet ReporteCongresosInstitucionServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReporteCongresosInstitucionServlet at " + request.getContextPath() + "</h1>");
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
            String inicioStr = req.getParameter("inicio");
            String finStr = req.getParameter("fin");
            String institucionIdStr = req.getParameter("institucionId");

            Date inicio = (inicioStr != null && !inicioStr.isEmpty()) ? Date.valueOf(inicioStr) : null;
            Date fin = (finStr != null && !finStr.isEmpty()) ? Date.valueOf(finStr) : null;
            Integer institucionId = (institucionIdStr != null && !institucionIdStr.isEmpty())
                    ? Integer.parseInt(institucionIdStr) : null;

            List<Congreso> lista = reporteDB.congresosPorInstitucion(inicio, fin, institucionId);

            req.setAttribute("reporte", lista);
            req.getRequestDispatcher("/Reportes/congresosInstitucion.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Error al generar el reporte: " + e.getMessage());
            req.getRequestDispatcher("/Reportes/congresosInstitucion.jsp").forward(req, resp);
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
