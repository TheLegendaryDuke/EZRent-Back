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
  * Run `docker build -t ezrent-back .` in the root directory
  * After the build is finished, execute the following command to run the image (remember to fill in the missing fields):
    ```
        docker run --name ezrent-back -p 8080:8080 \
         -e redis_host=localhost \
         -e redis_port=6379 \
         -e redis_password= \
         -e sql_host=jdbc:postgresql://localhost:5432/ezrent
         -e sql_username= \
         -e sql_password= \
         -e facebook_id= \
         -e facebook_secret = \
         -e twitter_id= \
         -e twitter_secret= \
         -e google_id= \
         -e google_secret= \
         -e google_api_key= \
         -e frontend=localhost:3000 \
         ezrent-back
    ```
    This mimics the production behavior but takes longer. Alternatively, the application can also be started by setting the corresponding environment variables in your IDE and run using `gradle bootRun` command.
    When running on MAC, replace `localhost` by `host.docker.internal`
  * Check that the application is started by going to `http://localhost:8080/graphiql`
