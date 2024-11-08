package org.CCristian.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.CCristian.apiservlet.webapp.headers.models.entities.Producto;
import org.CCristian.apiservlet.webapp.headers.services.LoginService;
import org.CCristian.apiservlet.webapp.headers.services.ProductoService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Inject
    private LoginService auth;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Producto> productos = null;   /*Obtiene una lista con los Productos*/
        try {
            productos = service.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Optional<String> usernameOptional = auth.getUsername(req);  /*Obtiene en nombre de Usuario*/

        /*Pasando parámetros*/
        req.setAttribute("productos", productos);
        req.setAttribute("username", usernameOptional);
        req.setAttribute("title", req.getAttribute("title") + ": Listado de Productos");
        getServletContext().getRequestDispatcher("/listar.jsp").forward(req, resp);
    }
}
