# A demo project about Redis integrated with SpringMVC and with 2 microservices 

1. First you should install Redis locally or you can run it also in docker container - for Windows OS better use the docker container. I have used the docker-compose option.
- https://redis.io/docs/latest/operate/oss_and_stack/install/install-redis/
- https://hub.docker.com/_/redis
- https://redis.io/learn/operate/orchestration/docker

- **docker-compose up -d**  - in the main folder of the project run the container in a detached state
- **docker exec -it <redis_container_id> sh** - execute interactively the container shell
- **redis-cli** - load the Redis command line interface
- **check the connection**
`127.0.0.1:6379> ping -> PONG`
`127.0.0.1:6379> set Pesho 5 -> OK`
`127.0.0.1:6379> get Pesho -> "5"`
- you can stop the container with **docker-compose down**

2. Then run RedisApp1 (on port 8080) and RedisApp2 (on port 8081)
3. On RedisApp1 automatically this PersonDTO object is created 
`{
   "id": "123",
   "name": "Svilen",
   "age": 17
}`
4. You can run on Postman GET http request `localhost:8080/redis1/123`
5. You can create another cache pair (key, value) value on Postman POST `localhost:8080/redis1` with JSON body
`{
   "id": "456",
   "name": "Atanas",
   "age": 16
}`
6. You can run GET `localhost:8080/redis1/456`
7. POST `localhost:8080/redis1` with JSON body
`{
"id": "456",
"name": "Atanas Varbanov",
"age": 20
}`
8. You can run GET `localhost:8080/redis1/456` to see the changes about Person with id 456.
9. Run DELETE `localhost:8080/redis1/456`
10. Run GET `localhost:8080/redis1/456` to see the cache pair is missing