# A gRPC API vulnerable app

For testing hawkscan GRPC and protobuf capabilities.

‼️**NOTE** ‼️
**There are no vulnerabilities in this project yet** only a basic gRPC service 
springboot setup. 

Things to be added
1. Postgresql backend for the [ItemsService](src/main/kotlin/hawk/grpckt/ItemsService.kt)
2. docker-compose setup for build and running the postgres DB
3. Add a Search method to the ItemService with and SQLi vulnerability.


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

## Testing

See [GrpcProxyClientTest.kt](src/test/kotlin/hawk/test/GrpcProxyClientTest.kt) 
for an example of proxying gRPC traffic through hawkscan/ZAP. 

