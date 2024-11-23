# E-Commerce Microservices

# Run Docker Services

```sh
docker compose up --build
```

```sh
docker ps
```

```sh
CONTAINER ID   IMAGE                              COMMAND                  CREATED         STATUS                     PORTS                                            NAMES
503ffcccdd23   confluentinc/cp-kafka:latest       "/etc/confluent/dock…"   5 minutes ago   Up 5 minutes               0.0.0.0:9092->9092/tcp                           ms-kafka
0f613fefbb0d   mongo-express                      "/sbin/tini -- /dock…"   5 minutes ago   Up 5 minutes               0.0.0.0:8081->8081/tcp                           ms-mongo-express
45ee238f10dc   maildev/maildev                    "bin/maildev"            5 minutes ago   Up 5 minutes (unhealthy)   0.0.0.0:1025->1025/tcp, 0.0.0.0:1080->1080/tcp   ms-mail-dev
64fbee7cebff   dpage/pgadmin4                     "/entrypoint.sh"         5 minutes ago   Up 5 minutes               443/tcp, 0.0.0.0:5050->80/tcp                    ms-pgadmin
a9242879b7eb   confluentinc/cp-zookeeper:latest   "/etc/confluent/dock…"   5 minutes ago   Up 5 minutes               2888/tcp, 3888/tcp, 0.0.0.0:22181->2181/tcp      ms-zookeeper
ce2a6a203da3   openzipkin/zipkin                  "start-zipkin"           5 minutes ago   Up 5 minutes (healthy)     9410/tcp, 0.0.0.0:9411->9411/tcp                 ms-zipkin
fd06d659a065   mongo                              "docker-entrypoint.s…"   5 minutes ago   Up 5 minutes (healthy)     0.0.0.0:27017->27017/tcp                         ms-mongo-db
cdd3a14fb0a5   postgres                           "docker-entrypoint.s…"   5 minutes ago   Up 5 minutes               0.0.0.0:5432->5432/tcp                           ms-postgres-db
```

### Run Microservices

- Config Server: Port `8888`
- Discovery Server Port `8761`
- Customer Service: Port `8090`
- Product Service: Port `8050`
- Order Service: Port `8070`
- Payment Service: Port `8060`
- Notification Service
- API Gateway Service: Port `8222`

### Communication Between Microservices

- Synchronous communication: `RestTemplate`
- Asynchronous communication: `Kafka`
