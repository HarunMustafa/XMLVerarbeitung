# cattle-backend

This project is a Quarkus-based microservice that manages cattle data and file uploads. It features REST APIs, Kafka integration for validation messaging, PostgreSQL persistence, and a File-Storage endpoint.

---

## Features

- ✅ REST API for managing cattle (create, list, list validated)
- ✅ Kafka integration for cattle validation via `validation-requests` and `validation-responses`
- ✅ PostgreSQL database via Hibernate ORM with Panache
- ✅ File upload and download via `/files/upload` and `/files/{filename}`
- ✅ OpenAPI documentation via Swagger UI (`/swagger-ui`)
- ✅ Dockerized setup with `docker-compose.yml` including:
  - Redpanda (Kafka-compatible broker)
  - PostgreSQL
  - `cattle-backend` service
  - `text-validation-service`

---

##  Getting Started

### Running the application in dev mode
```bash
./mvnw compile quarkus:dev
```

Visit the Dev UI at: [http://localhost:8080/q/dev](http://localhost:8080/q/dev)

### Swagger UI
```http
http://localhost:8080/swagger-ui
```

---

##  Packaging and running the application

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

To build an _über-jar_:
```bash
./mvnw package -Dquarkus.package.type=uber-jar
java -jar target/*-runner.jar
```

###  Native Build (Optional)
```bash
./mvnw package -Dnative
# or using a container:
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Run it:
```bash
./target/cattle-backend-1.0.0-SNAPSHOT-runner
```

---

##  Docker Compose Setup

Run all services:
```bash
docker compose up --build
```

Services started:
- Redpanda (Kafka): `localhost:9092`
- Redpanda UI: `localhost:8081`
- PostgreSQL: `localhost:5432`
- cattle-backend: `localhost:8080`
- text-validation-service: `localhost:8082`

---

##  File Upload & Download API

### Upload a File
`POST /files/upload`  
Form fields: `file`, `fileName`

Example (using curl):
```bash
curl -X POST http://localhost:8080/files/upload \
  -F file=@example.pdf \
  -F fileName=example.pdf
```

### Download a File
`GET /files/{filename}`

Example:
```bash
curl -O http://localhost:8080/files/example.pdf
```

---

## Related Guides

- JDBC Driver - PostgreSQL ([guide](https://quarkus.io/guides/datasource))
- RESTEasy Reactive ([guide](https://quarkus.io/guides/resteasy-reactive))
- SmallRye Reactive Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-reactive-getting-started))
- Hibernate ORM with Panache ([guide](https://quarkus.io/guides/hibernate-orm-panache))

---

## Additional Notes

- Upload directory is `/tmp/uploads` (or system temp folder)
- Validation via Kafka is asynchronous, updates `Cattle.validated`
- Swagger-UI includes all endpoints including `/cattle` and `/files`

---

> Built with using Quarkus
