package org.CCristian.apiservlet.webapp.headers.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.CCristian.apiservlet.webapp.headers.configs.Repository;
import org.CCristian.apiservlet.webapp.headers.models.entities.Categoria;

import java.util.List;

@RepositoryJPA
@Repository
public class CategoriaRepositoryJpaImpl implements CrudRepository<Categoria>{

    @Inject
    private EntityManager em;

    @Override
    public List<Categoria> listar() throws Exception {
        return em.createQuery("FROM Categoria", Categoria.class).getResultList();
    }

    @Override
    public Categoria porId(Long id) throws Exception {
        return em.find(Categoria.class, id);
    }

    @Override
    public void guardar(Categoria categoria) throws Exception {
        if (categoria.getId() != null && categoria.getId() > 0){
            em.merge(categoria);    //Actualizar una entidad existente//
        } else {
            em.persist(categoria);  //Insertar una nueva entidad//
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }
}
