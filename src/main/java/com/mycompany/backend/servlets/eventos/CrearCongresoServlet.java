/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.backend.servlets.eventos;

import com.mycompany.backend.db.CongresoDB;
import com.mycompany.backend.model.Congreso;
import com.mycompany.backend.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.time.LocalDate;

/**
 *
 * @author sofia
 */

@WebServlet("/Eventos/crear")
public class CrearCongresoServlet extends HttpServlet {
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
            out.println("<title>Servlet CrearCongresoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrearCongresoServlet at " + request.getContextPath() + "</h1>");
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
        Usuario u = (Usuario) req.getSession().getAttribute("usuario");
        if (u == null || !(u.getRol().equalsIgnoreCase("admin_congreso") || u.getRol().equalsIgnoreCase("admin_sistema"))) {
            resp.sendRedirect(req.getContextPath() + "/Participantes/menu?msg=No+autorizado");
            return;
        }

        String id = req.getParameter("id");
        String titulo = req.getParameter("titulo");
        String descripcion = req.getParameter("descripcion");
        String fechaInicio = req.getParameter("fecha_inicio");
        String fechaFin = req.getParameter("fecha_fin");
        String precioStr = req.getParameter("precio");
        String institucionIdStr = req.getParameter("institucion_id");

        try {
            double precio = Double.parseDouble(precioStr);
            if (precio < 35) {
                req.setAttribute("error", "El precio mínimo es 35");
                req.getRequestDispatcher("/Eventos/crearEvento.jsp").forward(req, resp);
                return;
            }

            Congreso c = new Congreso();
            if (id == null || !id.startsWith("EVT-")) {
                req.setAttribute("error", "El ID debe iniciar con EVT-");
                req.getRequestDispatcher("/Eventos/crearEvento.jsp").forward(req, resp);
                return;
            }
            c.setId(id);
            c.setTitulo(titulo);
            c.setDescripcion(descripcion);
            c.setFechaInicio(LocalDate.parse(fechaInicio));
            c.setFechaFin(LocalDate.parse(fechaFin));
            c.setPrecio(precio);
            c.setInstitucionId(Integer.parseInt(institucionIdStr));

            congresoDB.insert(c);
            resp.sendRedirect(req.getContextPath() + "/Eventos/listar?ok=Congreso+creado");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error al crear congreso: " + e.getMessage());
            req.getRequestDispatcher("/Eventos/crearEvento.jsp").forward(req, resp);
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
