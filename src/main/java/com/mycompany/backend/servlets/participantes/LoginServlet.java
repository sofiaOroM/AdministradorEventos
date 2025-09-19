package com.mycompany.backend.servlets.participantes;

import com.mycompany.backend.db.UsuarioDB;
import com.mycompany.backend.model.Usuario;
import com.mycompany.backend.util.PasswordUtil;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Participantes/login")
public class LoginServlet extends HttpServlet {
    private final UsuarioDB usuarioDB = new UsuarioDB();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/Participantes/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String pass = req.getParameter("password");

        try {
            Usuario u = usuarioDB.encontrarPorNombre(nombre);
            if (u != null) {
                String dec = PasswordUtil.decodeBase64(u.getPassword());
                if (dec.equals(pass) && u.isActivo()) {
                    HttpSession s = req.getSession(true);
                    s.setAttribute("usuario", u);
                    resp.sendRedirect(req.getContextPath() + "/Participantes/menu");
                    return;
                }
            }
            req.setAttribute("error", "Usuario o contraseña inválidos (o usuario inactivo).");
            req.getRequestDispatcher("/Participantes/login.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Error en autenticación: " + e.getMessage());
            req.getRequestDispatcher("/Participantes/login.jsp").forward(req, resp);
        }
    }
}
