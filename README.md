# RecetaYa - Microservicio de Dispensacion

Microservicio desarrollado con Java 17 y Spring Boot
para la Evaluacion Parcial 2.

## Funcionalidades

- Registrar dispensaciones de recetas medicas.
- Consultar todas las dispensaciones.
- Buscar dispensaciones por ID.
- Consultar dispensaciones asociadas a una receta.
- Actualizar dispensaciones.
- Eliminar dispensaciones.
- Registrar los medicamentos y cantidades dispensadas.
- Persistir los datos mediante Spring Data JPA y H2.

## Tecnologias

- Java 17.
- Spring Boot.
- Spring Data JPA e Hibernate.
- Base de datos H2.
- Maven Wrapper.

## Requisitos

- JDK 17 o compatible.
- Git.
- Puerto 8084 disponible.
- Postman para realizar pruebas.

## Clonar el repositorio

git clone https://github.com/CatrinaMedina/recetayadispensacion.git

cd recetayadispensacion

## Compilar y generar el JAR

Desde Git Bash:

./mvnw clean package

Si la compilacion es correcta, aparecera BUILD SUCCESS.
El archivo JAR se genera dentro de la carpeta target.

## Ejecutar localmente

./mvnw spring-boot:run

El microservicio se ejecuta en:

http://localhost:8084

## Base de datos

Se utiliza H2 con persistencia en archivo.

Configuracion en application.properties:

spring.datasource.url=jdbc:h2:file:./data/dispensaciondb

Los datos se almacenan localmente en la carpeta data.

## Arquitectura

- controller: endpoints REST.
- model: entidades Dispensacion y DetalleDispensacion.
- repository: acceso a datos con Spring Data JPA.
- service: interfaz y logica de negocio.

## Endpoints REST

- POST /dispensaciones
- GET /dispensaciones
- GET /dispensaciones/{id}
- GET /dispensaciones/receta/{recetaId}
- PUT /dispensaciones/{id}
- DELETE /dispensaciones/{id}

## Ejemplo de creacion

POST http://localhost:8084/dispensaciones

Body JSON:

{
  "recetaId": 33,
  "farmaceuticoId": 2,
  "sucursalId": 1,
  "detalles": [
    {
      "medicamentoId": 1,
      "cantidad": 1
    }
  ]
}

Una dispensacion nueva comienza en estado
PENDIENTE_CONFIRMACION.

## Pruebas con Postman

Se recomienda comprobar:

- Creacion de una dispensacion.
- Consulta de dispensaciones.
- Busqueda por ID y por receta.
- Actualizacion y eliminacion.
- Validacion de datos incorrectos.
- Rechazo de una dispensacion duplicada para la misma receta.
- Persistencia de datos despues de reiniciar el servicio.

## Integracion con API Gateway

El API Gateway de RecetaYa funciona en el puerto 8080.

Al acceder mediante el Gateway se requiere un token JWT.
Las operaciones de dispensacion estan restringidas
al rol FARMACEUTICO.

Para realizar pruebas integradas se deben ejecutar
los microservicios correspondientes y el API Gateway.

## Proyecto

RecetaYa - Java: Diseno y Construccion de Soluciones Nativas en Nube.
