import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.google.protobuf") version "0.8.18"
    kotlin("jvm") version "1.7.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
}

val grpcVersion = extra["grpcVersion"]
val protobufVersion = extra["protobufVersion"]
val grpcKotlinVersion = extra["grpcKotlinVersion"]
val coroutinesVersion = extra["coroutinesVersion"]
val springBootVersion = extra["org.springframework.boot.version"]

dependencies {

    api("io.grpc:grpc-bom:$grpcVersion")

    api(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    api(kotlin("stdlib"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    api("io.grpc:grpc-stub:$grpcVersion")
    api("io.grpc:grpc-protobuf:$grpcVersion")
    api("com.google.protobuf:protobuf-java-util:$protobufVersion")
    api("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    api("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")

    api("io.grpc:grpc-netty-shaded:$grpcVersion")

    api("io.github.lognet:grpc-spring-boot-starter:4.8.0")

    // need to help test protobuf decoding independent of HTTP/2 support in ZAP
    api("net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE")
    api("org.springframework.boot:spring-boot-starter-web")

    // api("org.springframework.boot:spring-boot-starter-data-jpa")
}

ktlint {
    filter {
        exclude { it.file.absolutePath.contains("generated") }
    }
}

sourceSets {
    val main by getting { }
    main.java.srcDirs("build/generated/source/proto/main/java")
    main.java.srcDirs("build/generated/source/proto/main/grpc")
    main.java.srcDirs("build/generated/source/proto/main/kotlin")
    main.java.srcDirs("build/generated/source/proto/main/grpckt")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

sourceSets {
    main {
        proto {
            srcDir("protos")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc") { artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion" }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:$grpcKotlinVersion:jdk8@jar"
        }
    }
    generateProtoTasks {
        ofSourceSet("main")
            .forEach {
                it.plugins {
                    id("grpc")
                    id("grpckt")
                }
                it.builtins {
                    id("kotlin")
                }
                it.generateDescriptorSet = true
                it.descriptorSetOptions.path = "${buildDir.path}/resources/main/descriptor_set.pb"
                it.descriptorSetOptions.includeSourceInfo = true
            }
    }
}
