# A demo project about Redis integrated with SpringMVC and with 2 microservices 

### I. First you should install Redis locally or you can run it also in docker container - for Windows OS better use the docker container. I have used the docker-compose option.
- https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/
- https://hub.docker.com/_/redis
- https://redis.io/learn/operate/orchestration/docker

- **docker-compose up -d**  - in the main folder of the project run the container in a detached state
- **docker ps** - see the container running
- **docker exec -it <redis_container_id> sh** - execute interactively the container shell
- **redis-cli** - load the Redis command line interface
- **check the connection**
`127.0.0.1:6379> ping -> PONG`
`127.0.0.1:6379> set Pesho 5 -> OK`
`127.0.0.1:6379> get Pesho -> "5"`
- you can stop the container with **docker-compose down --volumes**


### II. When on sharedlibrary dir, Run `gradle clean build` so that we install the sharedlibrary library jar file - **sharedlibrary-1.0-SNAPSHOT.jar**
- The **PersonDTO** should be shared in a common library in order to escape the java.lang.ClassNotFoundException:
- The Redis object classes (e.g., **PersonDTO**) need to be defined in a shared module or library that is accessible to both microservices. This ensures that the class definitions are available during deserialization.


### III. Check if PersonDTO is imported correctly into RedisApp1 and RedisApp2. Then run RedisApp1 (on port 8080) and RedisApp2 (on port 8081)


### IV. Redis Value Cache Service on both RedisApp1 (on port 8080) and RedisApp2 (on port 8081)
1. On **RedisApp1** automatically this PersonDTO object is created 
`{
"id": "123",
"name": "Svilen",
"age": 17
}`
2. You can run on Postman GET http request `localhost:8080/redis1/123`


3. You can create another cache pair (key, value) value on Postman POST `localhost:8080/redis1` with JSON body
`{
"id": "456",
"name": "Atanas",
"age": 16
}`
4. You can run GET `localhost:8080/redis1/456`


5. POST `localhost:8080/redis1` with JSON body
`{
"id": "456",
"name": "Atanas Varbanov",
"age": 20
}`
6. You can run GET `localhost:8080/redis1/456` to see the changes about Person with id 456.


7. Run DELETE `localhost:8080/redis1/456`
8. Run GET `localhost:8080/redis1/456` to see the cache pair is missing


9. Run GET on the **RedisApp2** - `localhost:8081/redis2/123`

10. Run GET on the **RedisApp2** - `localhost:8081/redis2` with JSON body
 `{
 "id": "123",
 "name": "Svilen Velikov",
 "age": 23
 }`
11. Run GET on the **RedisApp2** - `localhost:8081/redis2/123` to see the changes about Person with id 123. 
12. Run GET on the RedisApp1 - `localhost:8080/redis1/123` to see the changes about Person with id 123.


### V. Redis List Cache Service on both RedisApp1 (on port 8080) and RedisApp2 (on port 8081)
