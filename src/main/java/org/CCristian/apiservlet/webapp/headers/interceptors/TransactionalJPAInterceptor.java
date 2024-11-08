package org.CCristian.apiservlet.webapp.headers.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;

import org.CCristian.apiservlet.webapp.headers.services.ServiceJdbcException;

import java.util.logging.Logger;

@TransactionalJPA
@Interceptor
public class TransactionalJPAInterceptor {

    @Inject
    private EntityManager em;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception {

        try {
            log.info(" ------> Iniciando Transacción " + invocation.getMethod().getName()
                    + " de la clase " + invocation.getMethod().getDeclaringClass());
            em.getTransaction().begin();
            Object resultado = invocation.proceed();
            em.getTransaction().commit();
            log.info(" ------> Realizando 'commit' y Finalizando Transacción " + invocation.getMethod().getName()
                    + " de la clase " + invocation.getMethod().getDeclaringClass());
            return resultado;
        } catch (ServiceJdbcException e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
}
