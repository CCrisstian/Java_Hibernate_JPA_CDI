package org.CCristian.apiservlet.webapp.headers.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.CCristian.apiservlet.webapp.headers.services.ServiceJdbcException;

import java.io.IOException;

@WebFilter("/*")
public class ConexionFilter implements Filter {

//    @Inject
//    @MySQLConn
//    private Connection conn;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("No se pudo cargar el controlador JDBC", e);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

//        try{
//            Connection connRequest = this.conn;
//
//            if (connRequest.getAutoCommit()){
//                connRequest.setAutoCommit(false);
//            }
            try {
                chain.doFilter(request, response);
//                connRequest.commit();
            } catch (ServiceJdbcException e){
//                connRequest.rollback();
                ((HttpServletResponse)response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
