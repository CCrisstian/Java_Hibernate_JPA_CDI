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

<h2>Diferencias entre JDBC y JPA</h2>

- Diferencias entre `ProductoRepositoryJdbcImpl` y `ProductoRepositoryJpaImpl`
- Diferencias entre `CategoriaRepositoryJdbcImpl` y `CategoriaRepositoryJpaImpl`
- Diferencias entre `UsuarioRepositoryJdbcImpl` y `UsuarioRepositoryJpaImpl`

| Aspecto                        | ProductoRepositoryJdbcImpl / JPA               | CategoriaRepositoryJdbcImpl / JPA               | UsuarioRepositoryJdbcImpl / JPA                 |
|--------------------------------|------------------------------------------------|------------------------------------------------|------------------------------------------------|
| **Nivel de abstracción**       | JDBC: Bajo, SQL directo                        | JDBC: Bajo, SQL directo                         | JDBC: Bajo, SQL directo                         |
|                                | JPA: Alto, mapeo ORM                           | JPA: Alto, mapeo ORM                            | JPA: Alto, mapeo ORM                            |
| **Conexión**                   | JDBC: Manual (`Connection` inyectado)          | JDBC: Manual (`Connection` inyectado)           | JDBC: Manual (`Connection` inyectado)           |
|                                | JPA: Automática (manejada por `EntityManager`) | JPA: Automática (manejada por `EntityManager`)  | JPA: Automática (manejada por `EntityManager`)  |
| **Consultas de listado**       | JDBC: SQL directo (`SELECT * ...`)             | JDBC: SQL directo (`SELECT * FROM categorias`)  | JDBC: SQL directo (`SELECT * FROM usuarios`)    |
|                                | JPA: JPQL (`SELECT p FROM Producto p ...`)     | JPA: JPQL (`FROM Categoria`)                    | JPA: JPQL (`FROM Usuario`)                      |
| **Consultas por ID**           | JDBC: SQL directo con `PreparedStatement`      | JDBC: SQL directo con `PreparedStatement`       | JDBC: SQL directo con `PreparedStatement`       |
|                                | JPA: `em.find(Entity.class, id)`               | JPA: `em.find(Entity.class, id)`                | JPA: `em.find(Entity.class, id)`                |
| **Consultas específicas**      | JDBC: `SELECT` con SQL directo para join       | JDBC: `SELECT` sin join                         | JDBC: `SELECT * WHERE username = ?`             |
|                                | JPA: JPQL join en consulta                     | JPA: No se necesita join                        | JPA: JPQL `WHERE u.username = :username`        |
| **Mapeo de resultados**        | JDBC: Manual, convierte `ResultSet` a `Producto`| JDBC: Manual, convierte `ResultSet` a `Categoria`| JDBC: Manual, convierte `ResultSet` a `Usuario` |
|                                | JPA: Automático a entidades JPA                | JPA: Automático a entidades JPA                 | JPA: Automático a entidades JPA                 |
| **Persistencia de entidades**  | JDBC: SQL (`INSERT` / `UPDATE`)                | JDBC: Sin implementar                           | JDBC: SQL (`INSERT` / `UPDATE`)                 |
|                                | JPA: Automática (`persist` / `merge`)          | JPA: Automática (`persist` / `merge`)           | JPA: Automática (`persist` / `merge`)           |
| **Transacciones**              | JDBC: Explicitas, control manual               | JDBC: Explicitas, control manual                | JDBC: Explicitas, control manual                |
|                                | JPA: Automáticas dentro del contexto JPA       | JPA: Automáticas dentro del contexto JPA        | JPA: Automáticas dentro del contexto JPA        |
| **Eliminación de entidades**   | JDBC: SQL (`DELETE ... WHERE id=?`)            | JDBC: Sin implementar                           | JDBC: SQL (`DELETE ... WHERE id=?`)             |
|                                | JPA: `em.remove(entidad)`                      | JPA: `em.remove(entidad)`                       | JPA: `em.remove(entidad)`                       |
| **Manejo de ID**               | JDBC: Control manual (autoincremental en BD)   | JDBC: Control manual (autoincremental en BD)    | JDBC: Control manual (autoincremental en BD)    |
|                                | JPA: Automático en `persist`                   | JPA: Automático en `persist`                    | JPA: Automático en `persist`                    |

<h2>Service</h2>

Esta anotación `@Service` está diseñada como un estereotipo personalizado que centraliza varias funcionalidades y cualidades que se aplican a los servicios en la aplicación.

- `@Logging`: Esta anotación agrega capacidades de registro (logging) para los servicios que la utilizan. Esto puede incluir la captura automática de mensajes de log al iniciar y finalizar métodos o manejar excepciones dentro de la clase.
- `@ApplicationScoped`: Este alcance garantiza que el servicio anotado con `@Service` sea único y compartido en toda la aplicación, actuando como un singleton. De esta forma, los recursos de la clase se instancian una sola vez durante el ciclo de vida de la aplicación.
- `@Stereotype`: Marca esta anotación como un "estereotipo", lo que significa que es un "meta-componente" o un agrupador de otras anotaciones. Esto permite reutilizar combinaciones de funcionalidades en varias clases, simplificando la configuración y manteniéndola en un solo lugar.
- `@Named`: Hace que los servicios anotados con `@Service` sean detectables por el contexto de inyección de dependencias de CDI (Contexts and Dependency Injection) de Java, lo cual facilita la inyección en otras clases, especialmente en componentes de front-end como las vistas JSF.
- `@Target(ElementType.TYPE)` y `@Retention(RetentionPolicy.RUNTIME)`:
  - `@Target(ElementType.TYPE)` especifica que esta anotación solo puede aplicarse a clases, interfaces o tipos.
  - `@Retention(RetentionPolicy.RUNTIME)` define que la anotación estará disponible en tiempo de ejecución, lo cual es necesario para que el CDI y otros marcos puedan detectar y procesar la anotación en tiempo real.
