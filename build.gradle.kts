val springBootVersion = "3.4.4"
val springDocOpenApiVersion = "2.8.6"
val testContainersVersion = "1.20.6"
val springModulithVersion = "1.3.4"
val awaitilityVersion = "4.3.0"

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.jetbrains.kotlin.plugin.jpa") version "2.1.20"
}

group = "com.cdbfkk"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configureRepositories()
configureDependencies()

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.register<Test>("unitTest") {
    description = "Executa apenas os testes de unidade"
    group = "verification"
    useJUnitPlatform {
        includeTags("unit")
    }
}

tasks.register<Test>("integrationTest") {
    description = "Executa apenas os testes de integração"
    group = "verification"
    useJUnitPlatform {
        includeTags("integration")
    }
}

fun configureRepositories() {
    repositories {
        mavenCentral()
    }
}

fun configureDependencies() {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.modulith:spring-modulith-starter-jpa")
        implementation("org.projectlombok:lombok")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocOpenApiVersion")

        runtimeOnly("org.postgresql:postgresql")

        developmentOnly("org.springframework.boot:spring-boot-devtools")

        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("org.springframework.modulith:spring-modulith-starter-test")
        testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
        testImplementation("org.testcontainers:postgresql:$testContainersVersion")
        testImplementation("org.springframework.boot:spring-boot-testcontainers")
        testImplementation("org.awaitility:awaitility-kotlin:$awaitilityVersion")

        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
}