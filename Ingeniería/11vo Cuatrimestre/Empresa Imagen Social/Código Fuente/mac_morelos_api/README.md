# mac_morelos_api

## Proyecto de Estadías UTEZ 2024
Sistema de Módulo de Atención Ciudadana del Estado de Morelos (SIMAC-MOR)

**Estudiante:** Luis Eduardo Bahena Castillo

## Descripción

Este proyecto es una API REST construida con Spring Boot para proveer servicios del Sistema de Módulo de Atención Ciudadana del Estado de Morelos (SIMAC-MOR). Su objetivo principal es facilitar la gestión de información y procesos relacionados con la atención al ciudadano en esta región, formando parte del proyecto de Estadías de la UTEZ 2024.

## Características Principales

*   **API REST:** Expone endpoints para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre las entidades principales del Módulo de Atención Ciudadana.
*   **Spring Boot:** Utiliza el framework Spring Boot para el desarrollo de microservicios robustos y escalables.
*   **Microservicios:** Implementación de una arquitectura de microservicios para una mayor flexibilidad y escalabilidad.
*   **Persistencia de Datos:** Se integra con una base de datos PostgreSQL para el almacenamiento persistente de la información.
*   **Documentación API:** Generación de la documentación interactiva de la API mediante Swagger UI.
*   **Manejo de Errores:** Implementación de mecanismos para el manejo adecuado de errores y excepciones.
*  **Autenticación y Autorización:** Se implementará un sistema de seguridad basado en roles para proteger los endpoints de la API y controlar el acceso a los recursos.
*   **Arquitectura en Capas:** El proyecto se estructura siguiendo una arquitectura en capas (controladores, servicios, repositorios) para facilitar el mantenimiento y la escalabilidad.

## Tecnologías Utilizadas

*   **Java:** Lenguaje de programación principal.
*   **Spring Boot:** Framework para el desarrollo de microservicios.
*   **Spring Data JPA:** Para la interacción con la base de datos.
*   **PostgreSQL:** Base de datos relacional.
*   **Maven:** Herramienta de construcción y gestión de dependencias.
*   **Swagger UI:** Para la documentación de la API.

## Requisitos

Antes de comenzar, asegúrate de tener instalado lo siguiente:

*   **JDK (Java Development Kit):** Versión 17 o superior.
*   **Maven:** Herramienta de construcción de proyectos Java.
*   **PostgreSQL:** Base de datos PostgreSQL.
*   **IDE (Entorno de Desarrollo Integrado):** Visual Studio Code con extensiones Java Development Kit (JDK), Extension Pack for Java y Spring Boot Tools.

## Configuración

1.  **Clonar el Repositorio:**
    ```bash
    git clone https://github.com/luisvad07/mac_morelos_api.git
    ```

2.  **Configurar la Base de Datos:**
    *   Asegúrate de tener una base de datos PostgreSQL configurada.
    *   Las credenciales y la configuración se encuentran en `application.properties`. 
    *   Asegúrate de usar las siguientes propiedades:

        ```properties
        # Nombre de la aplicación
        spring.application.name=mac_morelos_api

        # URL de la base de datos PostgreSQL
        spring.datasource.url=jdbc:postgresql://localhost:5432/smacmor_api?ssl=false

        # Usuario y contraseña para la base de datos PostgreSQL (Aqui tendras que poner tus propias credenciales para el acceso a tu base de datos)
        spring.datasource.username=luisvad
        spring.datasource.password=luisvad

        # Mostrar las sentencias SQL en el log (Opcional)
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true

        # Configuración de Hibernate (actualización automática del esquema)
        spring.jpa.hibernate.ddl-auto=create

        # Dialecto de Hibernate para PostgreSQL (Opcional)
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
        spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

        # Tiempo máximo de espera para la conexión (en milisegundos)
        spring.datasource.hikari.connection-timeout=30000

        # Número máximo de conexiones en el pool
        spring.datasource.hikari.maximum-pool-size=10

        # Validar conexiones automáticamente
        spring.datasource.hikari.validation-timeout=5000



        # Configuración de la seguridad

        # Clave secreta para firmar los tokens (Aquí puedes generar uno propio utilizando la clase JwtSecretGenerator.java ubicada en la carpeta de utils)
        jwt.secret=ZRECxEotNxb/DDlRQG+8mYMtGvIwAABgtfO7asbF0yE=

        # Tiempo de expiración del token en segundos (por ejemplo, 1 hora = 3600)
        jwt.expiration=3600

        # Tiempo de expiración para tokens de restablecimiento de contraseña
        jwt.expiration.restore=900



        #Servicio de correo electrónico
        spring.mail.host=smtp.gmail.com
        spring.mail.port=587
        spring.mail.username=20213tn002@utez.edu.mx
        spring.mail.password=Valladolid07$
        spring.mail.properties.mail.smtp.auth=true
        spring.mail.properties.mail.smtp.starttls.enable=true

        # Cambiar el puerto en el que se ejecuta la aplicación
        server.port=8081

        # Prefijo para las API
        apiPrefix=/mac-morelos-api
        
        # Configuración de Swagger
        openapi.dev-url=http://localhost:8081



        #Depuración Adicional (Opcional)
        debug=true
        logging.level.org.springframework.security=DEBUG
        logging.level.org.springframework.web=DEBUG
        logging.level.org.springframework.context=DEBUG
        ```

3.  **Compilar el Proyecto:**
    ```bash
    ./mvnw clean install #Para Maven
    ```
4.  **Ejecutar la Aplicación:**
    ```bash
    ./mvnw spring-boot:run  #Para Maven
    ```

## Endpoints de la API

Aquí se listarán los endpoints principales de la API (se agregarán a medida que se desarrollen):

*   `GET /api/entidad`: Obtiene una lista de entidades.
*   `GET /api/entidad/{id}`: Obtiene una entidad por su ID.
*   `POST /api/entidad`: Crea una nueva entidad.
*   `PUT /api/entidad/{id}`: Actualiza una entidad existente.
*   `DELETE /api/entidad/{id}`: Elimina una entidad.

    *Nota: Reemplaza `entidad` con las entidades reales de tu modelo de dominio.*

## Documentación

API con Swagger-OpenAPI

Información del Proyecto: http://localhost:8081/v3/api-docs

Documentación: https://swagger.io/solutions/api-documentation/

## Contribución

Si deseas contribuir al proyecto, sigue estos pasos:

1.  Haz un fork del repositorio.
2.  Crea una nueva rama para tu contribución.
3.  Realiza los cambios y pruebas.
4.  Envía un pull request con una descripción clara de tus cambios.

## Licencia

Apache 2.0 -> https://www.apache.org/licenses/LICENSE-2.0

## Autor

Luis Eduardo Bahena Castillo
