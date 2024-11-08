package org.CCristian.apiservlet.webapp.headers.configs;

import jakarta.inject.Qualifier;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Qualifier
@Retention(RetentionPolicy.RUNTIME) /*Se aplica en tiempo de ejecución*/
@Target({METHOD, FIELD, PARAMETER, TYPE, CONSTRUCTOR})   /*Donde se aplicará*/
public @interface ProductoServicePrincipal {
}
