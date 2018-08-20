# EZRent-Back

Environment setup:

1. Install the following softwares:
  * Redis: https://redis.io
  * PostgreSQL: https://www.postgresql.org
  * JDK (>= 8): http://www.oracle.com/technetwork/java/javase/downloads/index.html
  * Gradle: https://gradle.org
  * Intellij idea (recommended, but not necessary): https://www.jetbrains.com/idea/
2. Configure PostgreSQL:
  * After installation, open PgAdmin and create a local database named "ezrent"
  * Create a role with the username and password same as the one in [The configuration file](./src/main/resources/application-development.properties) by right-clicking "Login/Group Roles" > Create > "Create Login/Group Role..."
  * Grant the role privileges by right-clicking the role just created > Properties... > Privileges > Set fields to "Yes"
3. Compile and run the application:
  * Start redis-server (instructions available at https://redis.io)
  * Import the program by opening pom.xml and then intellij should be smart enough to figure out how to run it
  * Or, in the root directory, run the following command (change the name of the jar file to match the version in pom.xml):
  ```
  gradle bootRun
  ```
  * Check that the application is started by going to http://localhost:8080/graphiql
