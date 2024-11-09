<h1 align="center">Se agrego JPA al proyecto</h1>
<h2>persistence.xml</h2>
<p>Este archivo define la configuración de persistencia para el proyecto, donde se especifica la unidad de persistencia, las entidades que se manejarán y las propiedades de conexión a la base de datos. Es un archivo clave en proyectos que utilizan JPA (Java Persistence API), ya que permite a Hibernate (el proveedor de JPA en este caso) gestionar las entidades y sus operaciones de persistencia.</p>

```xml
<class>org.CCristian.apiservlet.webapp.headers.models.entities.Producto</class>
<class>org.CCristian.apiservlet.webapp.headers.models.entities.Categoria</class>
<class>org.CCristian.apiservlet.webapp.headers.models.entities.Usuario</class>
```
- Lista las clases de entidad que se utilizarán en esta unidad de persistencia:
  - `Producto`: representa la entidad de `productos`.
  - `Categoria`: representa la entidad de `categorías`.
  - `Usuario`: representa la entidad de `usuarios`.
- `exclude-unlisted-classes`:
  - Establecido en `true`, lo que significa que solo se incluirán las clases de entidad especificadas explícitamente. Esto evita que se incluyan automáticamente otras clases en el paquete.
- `properties`:Contiene las propiedades de configuración de la base de datos y de `Hibernate`
  - `jakarta.persistence.jdbc.url:` define la URL de conexión a la base de datos (en este caso, MySQL).
  - `jakarta.persistence.jdbc.driver`: especifica el controlador JDBC para conectar con MySQL.
  - `jakarta.persistence.jdbc.user` y `jakarta.persistence.jdbc.password`: usuario y contraseña de la base de datos.
  - `hibernate.show_sql`: muestra las consultas SQL generadas por `Hibernate` en la consola (útil para depuración).
  - `hibernate.dialect`: define el dialecto de `Hibernate` para MySQL 8, optimizando la generación de consultas para esta versión de base de datos.
 
<h2>Producto</h2>
La clase `Producto` representa la entidad de `productos` en el sistema y define cómo se mapea cada atributo de producto a columnas en la tabla productos de la base de datos.

```java
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categoria categoria;

    private int precio;
    private String sku;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
}
```

- Anotaciones de `JPA`:
  - `@Entity`: indica que esta clase es una entidad `JPA` y se mapea a una tabla en la base de datos.
  - `@Table(name = "productos")`: especifica que esta entidad se mapea a la tabla llamada `productos`.
- Atributos:
  - `id`:
    - Anotaciones: `@Id` y `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
    - Descripción: representa la clave primaria de la entidad `Producto`, generada automáticamente por la base de datos mediante el uso de la estrategia `IDENTITY`.
  - `categoria`
    - Anotaciones: `@ManyToOne(fetch = FetchType.LAZY)`.
    - Descripción: establece una relación de muchos-a-uno con la entidad `Categoria`. La propiedad `fetch = FetchType.LAZY` indica que esta relación se cargará de forma diferida, es decir, la información de la categoría solo se cargará cuando sea accedida explícitamente, lo cual mejora el rendimiento en consultas donde no se necesita esta información de inmediato.
  - `fechaRegistro`
    - Anotaciones: `@Column(name = "fecha_registro")`.
    - Descripción: almacena la fecha en que el producto fue registrado en el sistema. El atributo `name = "fecha_registro"` establece que esta columna en la base de datos se llamará `fecha_registro` en lugar de `fechaRegistro`.

<h2>Categoria</h2>

La clase `Categoria` representa la entidad de categorías en el sistema y define cómo se mapea cada atributo a columnas en la tabla `categorias` de la base de datos.

```java
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
}
```

- Anotaciones de `JPA`:
  - `@Entity`: indica que esta clase es una entidad `JPA` y se mapea a una tabla en la base de datos.
  - `@Table(name = "categorias")`: especifica que esta entidad se mapea a la tabla `categorias`.
- Atributos:
  - `id`:
    - Anotaciones: `@Id` y `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
    - Descripción: representa la clave primaria de la entidad `Categoria`, generada automáticamente por la base de datos utilizando la estrategia `IDENTITY`. Esto permite que cada categoría tenga un identificador único y auto-incrementado.

<h2>Usuario</h2>

La clase `Usuario` representa la entidad de usuarios en el sistema y define cómo se mapea cada atributo a columnas en la tabla `usuarios` de la base de datos.

```java
@Entity
@Table(name = "usuarios")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
}
```

- Anotaciones de `JPA`:
  - `@Entity`: indica que esta clase es una entidad `JPA` y se mapea a una tabla en la base de datos.
  - `@Table(name = "usuarios")`: especifica que esta entidad se mapea a la tabla llamada `usuarios`.
- Atributos:
  - `id`:
    - Anotaciones: `@Id` y `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
    - Descripción: representa la clave primaria de la entidad `Usuario`, generada automáticamente en la base de datos mediante la estrategia `IDENTITY`. Este atributo permite que cada usuario tenga un identificador único y auto-incrementado.
   
<h2>JpaUtil</h2>

La clase `JpaUtil` es una clase utilitaria en el proyecto que se encarga de administrar el ciclo de vida del `EntityManagerFactory`, un componente esencial en `JPA` para crear y gestionar instancias de `EntityManager`. La clase `JpaUtil` es clave para el acceso centralizado a la unidad de persistencia configurada en el archivo `persistence.xml`.

`EntityManager` es una interfaz de `JPA` (Java Persistence API) que proporciona métodos para gestionar el ciclo de vida de las entidades, realizar consultas, y ejecutar transacciones en el contexto de una unidad de persistencia. En términos sencillos, es el objeto que te permite interactuar con la base de datos a través de las entidades definidas en el modelo de datos de tu aplicación.

```java
public class JpaUtil {
    private static final EntityManagerFactory entityManagerFactory = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("ejemploJpa");
    }

    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
```

- Atributos:
 - `entityManagerFactory`:
     - Es una constante de tipo `EntityManagerFactory` inicializada mediante el método `buildEntityManagerFactory()`.
     - Esta instancia es esencial para crear y gestionar `EntityManager` y se configura usando la unidad de persistencia llamada `"ejemploJpa"` (definida en el archivo `persistence.xml`).
 - Métodos:
  - `buildEntityManagerFactory()`:
      - Visibilidad: privado (solo accesible dentro de `JpaUtil`).
      - Función: inicializa y devuelve una instancia de `EntityManagerFactory`, configurándola con el nombre de la unidad de persistencia `"ejemploJpa"`.
      - Descripción: Este método es invocado una vez cuando se carga la clase, asegurando que solo exista una instancia de `EntityManagerFactory` en toda la aplicación.
  - `getEntityManager()`:
      - Visibilidad: público (accesible desde otras partes de la aplicación).
      - Función: crea y devuelve una nueva instancia de `EntityManager` utilizando el `EntityManagerFactory`.
      - Descripción: este método permite obtener un `EntityManager` cada vez que la aplicación necesita interactuar con la base de datos, asegurando que se respete el ciclo de vida de `EntityManager` de acuerdo con las buenas prácticas en `JPA`.

<h2>ProducerResources</h2>

La clase `ProducerResources` proporciona métodos para producir y gestionar instancias de conexión a la base de datos y de `EntityManager`, aprovechando CDI para inyectar y controlar el ciclo de vida de estos objetos. Incluye métodos para obtener la conexión a la base de datos, cerrar la conexión una vez finalizada su utilización, y para manejar un `EntityManager`, que es la interfaz de `JPA` para la administración de entidades en la base de datos.

```java
@ApplicationScoped
public class ProducerResources {

    @Resource(name = "jdbc/mysqlDB")
    private DataSource ds;

    @Inject
    private Logger log;

    @Produces
    @RequestScoped
    @MySQLConn
    private Connection beanConnection() throws NamingException, SQLException {
        return ds.getConnection();
    }

    public void close(@Disposes @MySQLConn Connection connection) throws SQLException {
        connection.close();
        log.info("Cerrando la conexión a la BD MySQL");
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint){
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    @RequestScoped
    private EntityManager beanEntityManager(){
        return JpaUtil.getEntityManager();
    }

    public void close(@Disposes EntityManager entityManager){
        if(entityManager.isOpen()){
            entityManager.close();
            log.info("Cerrando la conexión del EntityManager!");
        }
    }
}
```

- `@ApplicationScoped`:
  - Propósito: La anotación `@ApplicationScoped` indica que la instancia de `ProducerResources` será única y compartida dentro del contexto de toda la aplicación.
  - Función: Esto significa que la clase `ProducerResources` se crea una sola vez cuando la aplicación comienza y se mantiene activa mientras la aplicación está en ejecución. Al ser `ApplicationScoped`, todos los componentes que requieran un recurso de esta clase (como conexiones a la base de datos o `EntityManager`) podrán usar esta instancia única, lo que optimiza el uso de memoria y facilita la gestión de recursos de manera centralizada.
- Inyección de `DataSource`:
  - Propósito: Inyecta una referencia al `DataSource` configurado en el servidor de aplicaciones, que proporciona una conexión a la base de datos MySQL especificada en el nombre del recurso (`jdbc/mysqlDB`).
  - Función: Actúa como una fuente de conexiones para acceder a la base de datos sin tener que crear una nueva conexión manualmente cada vez.
```java
@Resource(name = "jdbc/mysqlDB")
private DataSource ds;
```
- Producción de una conexión a la base de datos:
  - Propósito: Proporciona una instancia de `Connection` específica para la base de datos.
  - Función: Utiliza la anotación `@Produces` para hacer que el `Connection` esté disponible para inyección. La anotación personalizada `@MySQLConn` ayuda a identificar esta conexión específica.
```java
@Produces
@RequestScoped
@MySQLConn
private Connection beanConnection() throws NamingException, SQLException {
    return ds.getConnection();
}
```

- Cierre de la conexión a la base de datos:
  - Propósito: Libera recursos cerrando la conexión de base de datos cuando ya no se necesita.
  - Función: La anotación `@Disposes` indica a CDI que este método debe ser llamado cuando el `Connection` inyectado se está descartando, asegurando una liberación adecuada de los recursos.
```java
public void close(@Disposes @MySQLConn Connection connection) throws SQLException {
    connection.close();
    log.info("Cerrando la conexión a la BD MySQL");
}
```

- Producción de un `EntityManager`
  - Propósito: Proporciona un `EntityManager` para la gestión de las entidades en `JPA`.
  - Función: Facilita la administración de transacciones `JPA` para operaciones CRUD sobre entidades. Utiliza `JpaUtil.getEntityManager()` para obtener el `EntityManager` configurado.
```java
@Produces
@RequestScoped
private EntityManager beanEntityManager(){
    return JpaUtil.getEntityManager();
}
```

- Cierre del `EntityManager`:
  - Propósito: Libera el `EntityManager` al finalizar su uso.
  - Función: Cierra el `EntityManager` si está abierto, asegurando la correcta liberación de recursos `JPA`. También registra la acción de cierre en la consola.
```java
public void close(@Disposes EntityManager entityManager){
    if(entityManager.isOpen()){
        entityManager.close();
        log.info("Cerrando la conexión del EntityManager!");
    }
}
```

