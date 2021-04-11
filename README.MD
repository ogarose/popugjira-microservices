**Run Project in Docker-compose**
-
add to /etc/hosts "auth-server 127.0.0.1"

```
cp .env.dist .env

cd authServer  
./gradlew build

cd taskTraker  
./gradlew build

docker-compose -f docker/docker-compose-full.yaml up
```

Open http://localhost:8080/todo

TODO
- 
- Add SuperTypes - VO, Aggregate, DomainEvent ()
- use value objects (Money, ID, Email and so on)