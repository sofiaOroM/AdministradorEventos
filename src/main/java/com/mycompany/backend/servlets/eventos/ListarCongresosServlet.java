/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.eventos;

import com.mycompany.backend.db.CongresoDB;
import com.mycompany.backend.db.PagoDB;
import com.mycompany.backend.model.Congreso;
import com.mycompany.backend.model.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author sofia
 */
@WebServlet("/Eventos/listar")
public class ListarCongresosServlet extends HttpServlet {

    private final CongresoDB congresoDB = new CongresoDB();
    private final PagoDB pagoDB = new PagoDB();

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
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("usuario") == null) {
                resp.sendRedirect(req.getContextPath() + "/Participantes/login?error=Debes+iniciar+sesion");
                return;
            }
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            List<Congreso> lista = congresoDB.findAll();

            //verifiacr los que ya se pagaron por el ususario
            for (Congreso c : lista) {
                boolean pagado = pagoDB.existePagoConfirmado(usuario.getId(), c.getId());
                c.setPagado(pagado); // necesitas agregar este campo en el modelo
            }

            req.setAttribute("congresos", lista);
            req.getRequestDispatcher("/Eventos/listadoEvento.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al cargar congresos: " + e.getMessage());
            req.getRequestDispatcher("/Eventos/listadoEvento.jsp").forward(req, resp);
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
        //processRequest(request, response);
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
