# 📚 Challenge Alura - Literatura

Este proyecto forma parte del Challenge de Alura en colaboración con Oracle ONE, centrado en el desarrollo de una aplicación para gestionar y consultar información sobre libros y autores, utilizando una API externa.

---

## ✨ Características

La aplicación `challengue-literatura` permite a los usuarios:

* **Buscar libros** por título desde una API externa (Gutendex).
* **Listar todos los libros registrados** en la base de datos local.
* **Listar todos los autores registrados** en la base de datos local.
* **Listar autores vivos** en un determinado año.
* **Listar libros por idioma**.
* **Guardar los datos** de libros y autores en una base de datos local (PostgreSQL) para consultas rápidas.

---

## 🛠️ Tecnologías Utilizadas

El proyecto está desarrollado con las siguientes tecnologías:

* **Java 21:** Lenguaje de programación.
* **Spring Boot 3.x:** Framework para construir aplicaciones robustas y escalables.
* **Spring Data JPA:** Para la interacción con la base de datos de forma sencilla y eficiente.
* **Hibernate:** Implementación de JPA para mapeo objeto-relacional.
* **PostgreSQL:** Base de datos relacional para la persistencia de los datos.
* **Maven:** Herramienta para la gestión de dependencias y construcción del proyecto.
* **API Gutendex:** Utilizada para obtener información de libros y autores.

---

## 🚀 Cómo Empezar

Sigue estos pasos para levantar y ejecutar el proyecto en tu entorno local.

### Prerrequisitos

Asegúrate de tener instalado lo siguiente:

* **Java Development Kit (JDK) 17 o superior.**
* **Maven.**
* **PostgreSQL:** Asegúrate de tener una instancia de PostgreSQL ejecutándose.

### Configuración de la Base de Datos

1.  **Crea una base de datos** en PostgreSQL. Por ejemplo, `literatura_db`.
    ```sql
    CREATE DATABASE literatura_db;
    ```
2.  **Configura las credenciales de la base de datos** en el archivo `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/literatura_db
    spring.datasource.username=tu_usuario_postgres
    spring.datasource.password=tu_contraseña_postgres
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    ```
    * Asegúrate de reemplazar `tu_usuario_postgres` y `tu_contraseña_postgres` con tus credenciales.
    * `spring.jpa.hibernate.ddl-auto=update` se recomienda para que Hibernate cree o actualice automáticamente las tablas. Para producción, considera `none` y gestiona las migraciones manualmente.

### Ejecución Local

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/Jhowstin/challengue-literatura.git](https://github.com/Jhowstin/challengue-literatura.git)
    cd challengue-literatura
    ```
2.  **Compila el proyecto con Maven:**
    ```bash
    mvn clean install
    ```
3.  **Ejecuta la aplicación:**
    ```bash
    mvn spring-boot:run
    ```
    La aplicación se iniciará y estará lista para interactuar a través de la consola.

---

## 📖 Uso

Una vez que la aplicación esté en ejecución, se presentará un menú interactivo en la consola. Podrás seleccionar diferentes opciones para buscar, listar y gestionar libros y autores.

**Ejemplo de Interacción (en consola):**
