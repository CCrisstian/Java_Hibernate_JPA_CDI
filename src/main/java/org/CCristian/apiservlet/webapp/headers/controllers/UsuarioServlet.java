package org.CCristian.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.models.entities.Usuario;
import org.CCristian.apiservlet.webapp.headers.services.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    @Inject
    private UsuarioService service;

    @Inject
    private LoginService auth;  /*Obtiene en nombre de Usuario*/

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Usuario> usuarios = null;  /*Obtiene una lista con los Usuarios*/
        try {
            usuarios = service.listar();
        } catch (SQLException e){
            throw  new ServiceJdbcException(e.getMessage(), e.getCause());
        }

        Optional<String> usernameOptional = auth.getUsername(req);

        /*Pasando parámetros*/
        req.setAttribute("usuarios", usuarios);
        req.setAttribute("username", usernameOptional);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de Usuarios");
        getServletContext().getRequestDispatcher("/listarUsuarios.jsp").forward(req, resp);
    }
}