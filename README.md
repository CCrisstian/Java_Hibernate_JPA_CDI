<h1 align="center">Fetch LAZY y Fetch EAGER</h1>
<p>En Hibernate, la forma en que se cargan las asociaciones de entidades puede controlarse con las estrategias de <b>Fetch LAZY</b> y <b>Fetch EAGER</b></p>

<h2>Fetch LAZY y Fetch EAGER</h2>

- `Fetch LAZY` (carga perezosa): Hibernate carga la entidad principal inmediatamente, pero las asociaciones relacionadas se cargan solo cuando se accede explícitamente a ellas. Esto ahorra recursos al no cargar datos innecesarios.
- `Fetch EAGER` (carga ansiosa): Hibernate carga tanto la entidad principal como sus asociaciones relacionadas inmediatamente, lo que puede ser útil cuando se necesita acceder a todos los datos relacionados de manera inmediata. Sin embargo, puede afectar el rendimiento si se cargan muchos datos innecesarios.

<h2>Fetch LAZY en @OneToMany y @ManyToMany</h2>

- `@OneToMany` y `@ManyToMany` (`LAZY`): Estas asociaciones suelen ser `LAZY` por defecto. Hibernate solo cargará la colección de entidades relacionadas cuando sea necesario. Es útil para evitar cargar colecciones grandes si no se van a usar.

<h2>Fetch EAGER en @ManyToOne y @OneToOne</h2>

- `@ManyToOne` y `@OneToOne` (`EAGER`): Estas asociaciones suelen ser `EAGER` por defecto. Hibernate cargará la entidad relacionada junto con la entidad principal. Es adecuado cuando se necesita la información relacionada inmediatamente para evitar una segunda consulta. Sin embargo, hay que ser cuidadoso en relaciones que pueden cargar muchos datos de forma innecesaria.


En resumen, `LAZY` mejora el rendimiento cargando datos solo cuando son necesarios, mientras que `EAGER` garantiza que los datos estén disponibles de inmediato pero puede impactar negativamente en el rendimiento si se usan mal.
