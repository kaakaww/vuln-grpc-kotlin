# A gRPC API vulnerable app

For testing hawkscan GRPC and protobuf capabilities.


## Development

This is a gradle base project, use `./gradlew tasks` command to see all available options.

### Lint check code
```shell
./gradlew ktlintCheck
```

### Format code
```shell
./gradlew ktlintFormat
```

### Build jar
```shell
./gradlew bootJar 
```
Run the jar you just built 👆
```shell
java -jar build/libs/vuln-grpc-kotlin.jar
```

### Spring run
```shell
./gradlew bootRun
```

## Building and Running in Docker

### Build
```shell script
docker-compose build
```

### Run docker
```shell script
docker-compose up -d
```

### Run

```shell script
.\gradlew.bat --no-daemon bootRun
```

## Using the Application

### Reaching the App

Once the app starts up, you can reach it at [https://localhost:9001](https://localhost:9001).

A good tool for gRPC testing locally is https://kreya.app/

PS: You'll need Postgres for local development. 

## Testing

See [GrpcProxyClientTest.kt](src/test/kotlin/hawk/test/GrpcProxyClientTest.kt) 
for an example of proxying gRPC traffic through hawkscan/ZAP. 


