package org.CCristian.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.CCristian.apiservlet.webapp.headers.models.entities.Producto;
import org.CCristian.apiservlet.webapp.headers.services.ProductoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));    /*Obteniendo el 'id' del producto que se quiere eliminar*/
        } catch (NumberFormatException e) {
            id = 0L;
        }

        if (id > 0) {   /*Verifica que se haya enviado el parámetro 'id'*/
            Optional<Producto> o = service.porId(id);
            if (o.isPresent()) {    /*Verifica que ese 'id' exista en la Base de Datos*/
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/productos");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el producto en la Base de Datos!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ERROR el id es null, se debe enviar como parámetro en la url!");
        }
    }
}
