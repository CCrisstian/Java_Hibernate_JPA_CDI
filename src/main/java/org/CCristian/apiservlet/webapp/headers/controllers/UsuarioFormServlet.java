package org.CCristian.apiservlet.webapp.headers.controllers;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.models.entities.Usuario;
import org.CCristian.apiservlet.webapp.headers.services.UsuarioService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/usuarios/form")
public class UsuarioFormServlet extends HttpServlet {

    @Inject
    @Named("usuarioServiceImpl")
    private UsuarioService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long id;
        try {
            id = Long.parseLong(req.getParameter("id")); /*Obtiene el 'id' del usuario que se quiere Editar*/
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Usuario usuario = new Usuario();
        if (id > 0){
            Optional<Usuario> usuarioOptional = service.porId(id);  /*Obtiene el usuario al que le corresponde ese 'id'*/
            if (usuarioOptional.isPresent()){
                usuario = usuarioOptional.get();
            }
        }
        req.setAttribute("usuario", usuario);
        req.setAttribute("title", req.getAttribute("title") + ": Formulario de Usuarios");
        getServletContext().getRequestDispatcher("/formUsuarios.jsp").forward(req, resp);   /*Pasar los usuarios a formUsuarios.jsp*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*Obteniendo los valores desde el request*/
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        /*Validando los valores obtenidos*/
        Map<String, String> erroresUsuario = new HashMap<>();
        if (username == null || username.isBlank()) {
            erroresUsuario.put("username", "El 'username' es requerido!");
        }
        if (password == null || password.isBlank()) {
            erroresUsuario.put("password", "El 'password' es requerido!");
        }
        if (email == null || email.isBlank()) {
            erroresUsuario.put("email", "El 'email' es requerido!");
        }
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = 0L;
        }

        /*Asignando los valores obtenidos al usuario*/
        Usuario usuario = new Usuario(id, username, password, email);

        /*Cargar en la Base de Datos o Devolver el usuario con Errores*/
        if (erroresUsuario.isEmpty()) {
            service.guardar(usuario);  /*Cargando el producto a la Base de Datos*/
            resp.sendRedirect(req.getContextPath() + "/usuarios");   /*Reenviando los valores al Servlet /usuarios*/
        } else {
            req.setAttribute("erroresUsuario", erroresUsuario);
            req.setAttribute("usuario", usuario); /*Pasar el usuario con Errores y asignarlo como atributo del request */
            req.setAttribute("title", req.getAttribute("title") + ": Formulario de Usuarios");
            getServletContext().getRequestDispatcher("/formUsuarios.jsp").forward(req, resp);   /*Redireccionar usuario con Errores a formUsuarios.jsp*/
        }
    }
}