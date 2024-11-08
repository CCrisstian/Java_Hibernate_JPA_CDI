package org.CCristian.apiservlet.webapp.headers.services;

import jakarta.inject.Inject;
import org.CCristian.apiservlet.webapp.headers.configs.ProductoServicePrincipal;
import org.CCristian.apiservlet.webapp.headers.configs.Service;
import org.CCristian.apiservlet.webapp.headers.interceptors.TransactionalJPA;
import org.CCristian.apiservlet.webapp.headers.models.entities.Categoria;
import org.CCristian.apiservlet.webapp.headers.models.entities.Producto;
import org.CCristian.apiservlet.webapp.headers.repositories.CrudRepository;
import org.CCristian.apiservlet.webapp.headers.repositories.RepositoryJPA;

import java.util.List;
import java.util.Optional;

@Service
@ProductoServicePrincipal
@TransactionalJPA
public class ProductosServiceImpl implements ProductoService {

    @Inject
    @RepositoryJPA
    private CrudRepository<Producto> repository;

    @Inject
    @RepositoryJPA
    private CrudRepository<Categoria> repositoryCategoria;

    @Override
    public List<Producto> listar() {
        try {
            return repository.listar();
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repository.porId(id));
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repository.guardar(producto);
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repository.eliminar(id);
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategoria() {
        try {
            return repositoryCategoria.listar();
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoria.porId(id));
        } catch (Exception throwables) {
            throw new ServiceJdbcException(throwables.getMessage(), throwables.getCause());
        }
    }
}