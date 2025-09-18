/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.eventos;

import com.mycompany.backend.db.CarteraDB;
import com.mycompany.backend.db.CongresoDB;
import com.mycompany.backend.db.PagoDB;
import com.mycompany.backend.model.Congreso;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author sofia
 */
@WebServlet("/Pagos/pagarCongreso")
public class PagoCongresoServlet extends HttpServlet {
    private final CarteraDB carteraDB = new CarteraDB();
    private final PagoDB pagoDB = new PagoDB();
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
            out.println("<title>Servlet PagoCongresoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PagoCongresoServlet at " + request.getContextPath() + "</h1>");
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
        try {
            int usuarioId = Integer.parseInt(req.getParameter("usuario_id"));
            String congresoId = req.getParameter("congreso_id");

            Congreso c = congresoDB.encontrarPorId(congresoId);
            if (c == null) {
                resp.sendRedirect(req.getContextPath() + "/Congresos/listar?error=Congreso+no+existe");
                return;
            }

            // verificar saldo
            var cartera = carteraDB.obtenerPorUsuario(usuarioId);
            if (cartera == null || cartera.getSaldo().compareTo(BigDecimal.valueOf(c.getPrecio())) < 0) {
                resp.sendRedirect(req.getContextPath() + "/Cartera/panel?error=Saldo+insuficiente");
                return;
            }

            // descontar y registrar pago
            carteraDB.descontar(usuarioId, BigDecimal.valueOf(c.getPrecio()));
            pagoDB.registrarPago(usuarioId, congresoId, BigDecimal.valueOf(c.getPrecio()));

            resp.sendRedirect(req.getContextPath() + "/Congresos/listar?ok=Pago+registrado");
        } catch (SQLException e) {
            req.setAttribute("error", "Error al pagar: " + e.getMessage());
            req.getRequestDispatcher("/Cartera/panel.jsp").forward(req, resp);
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
