----------------------------------------------------
###Technologies:
* Maven
* Hibernate
* Spring Boot
* Spring Security
* Docker
* Swagger - OpenApi
----------------------------------------------------
###To run locally (development environment):
Use following command in terminal: `mvn org.springframework.boot:spring-boot-maven-plugin:run -Pdev`

Requirements:
* PostgreSQL with a database
* Maven
* JDK 11

###To run locally (production environment):
Use following command in terminal:  `mvn org.springframework.boot:spring-boot-maven-plugin:run`

----------------------------------------------------
###Swagger UI : 

----------------------------------------------------
###GraphQL

----------------------------------------------------
###Javadoc comments: 
`mvn javadoc:javadoc`

----------------------------------------------------
###Getting Started
1. Installation process for development environment:
      How to create a PostgreSQL database i Docker:
      1. Have your Docker Desktop up and running 
      2. Open your terminal (for example command prompt in Windows)
      3. Type `docker run --name postgresqldb -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres`
      Press enter, make sure that you got a running container with the name "postgres" in your Docker Desktop
      4.Type `docker ps` you will see the container id of your Docker container, copy it
      5.Type `docker exec -it b3bee3c04a62 bin/bash`, but put your container id instead of b3bee3c04a62
      6. Type `psql -U postgres`
      7. Type `CREATE DATABASE employees;` Here it´s important to not forget `;`. You will see a message `CREATE DATABASE`
      8. Type `\c employees` you will see a message You are now connected to database `postgresdb` as user `postgres`.
      9. Run your challenge-api project in your IDE, Spring Boot will connect to you database and create a table `employees`
      Now we created our database called employees in a Docker container.
      