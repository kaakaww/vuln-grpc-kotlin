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

## Testing

See [GrpcProxyClientTest.kt](src/test/kotlin/hawk/test/GrpcProxyClientTest.kt) 
for an example of proxying gRPC traffic through hawkscan/ZAP. 

