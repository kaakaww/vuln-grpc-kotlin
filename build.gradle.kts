import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.ofSourceSet
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    kotlin("jvm") version "1.7.0"
    id("com.google.protobuf") version "0.8.18"
    id("org.springframework.boot") version "2.7.1"
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

    implementation("io.grpc:grpc-bom:$grpcVersion")

    implementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.grpc:grpc-stub:$grpcVersion")
    implementation("io.grpc:grpc-protobuf:$grpcVersion")
    implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
    implementation("com.google.protobuf:protobuf-kotlin:$protobufVersion")
    implementation("io.grpc:grpc-kotlin-stub:$grpcKotlinVersion")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.1.51")
    // implementation("io.grpc:grpc-netty-shaded:$grpcVersion")

    implementation("io.github.lognet:grpc-spring-boot-starter:4.8.0") /*{
        exclude("io.grpc:grpc-netty-shaded")
    }*/
    // needed to help test protobuf decoding independent of HTTP/2 support in ZAP
    implementation("net.devh:grpc-client-spring-boot-starter:2.13.1.RELEASE")
    implementation("net.devh:grpc-server-spring-boot-starter:2.14.0.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

    runtimeOnly("org.postgresql:postgresql")
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

tasks.test {
    useJUnitPlatform()
}
tasks.bootJar {
    archiveBaseName.set("vuln-grpc-kotlin")
    archiveVersion.set("0.1.0")
}
