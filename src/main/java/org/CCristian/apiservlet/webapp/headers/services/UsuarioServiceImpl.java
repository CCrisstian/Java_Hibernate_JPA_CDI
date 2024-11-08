package org.CCristian.apiservlet.webapp.headers.services;


import jakarta.inject.Inject;
import org.CCristian.apiservlet.webapp.headers.configs.Service;
import org.CCristian.apiservlet.webapp.headers.interceptors.TransactionalJPA;
import org.CCristian.apiservlet.webapp.headers.models.entities.Usuario;
import org.CCristian.apiservlet.webapp.headers.repositories.RepositoryJPA;
import org.CCristian.apiservlet.webapp.headers.repositories.UsuarioRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@TransactionalJPA
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Inject
    public UsuarioServiceImpl(@RepositoryJPA UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional
                    .ofNullable(usuarioRepository.porUsername(username))
                    .filter(u -> u.getPassword().equals(password));
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        try {
            return usuarioRepository.listar();
        } catch (Exception e){
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Usuario> porId(Long id) {
        try {
        return Optional.ofNullable(usuarioRepository.porId(id));
        } catch (Exception e){
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Usuario usuario) {
        try {
            usuarioRepository.guardar(usuario);
        }catch (Exception e){
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            usuarioRepository.eliminar(id);
        } catch (Exception e){
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}