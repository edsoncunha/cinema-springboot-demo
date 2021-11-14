import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.31"
}

group = "io.edsoncunha"
version = "0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

val testContainersVersion = "1.16.2"
val springDocVersion = "1.5.2"
val log4jApiKotlinVersion = "1.0.0"

dependencies {
	// kotlin setup
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// spring boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// open api documentation
	implementation("org.springdoc:springdoc-openapi:$springDocVersion")
	implementation("org.springdoc:springdoc-openapi-ui:$springDocVersion")
	implementation("org.springdoc:springdoc-openapi-kotlin:$springDocVersion")

	// test container
	testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
	testImplementation("org.testcontainers:postgresql:$testContainersVersion")

	// postgresql
	runtimeOnly("org.postgresql:postgresql")

	// database migrations with Flyway
	implementation("org.flywaydb:flyway-core")

	// fluent test assertions in kotlin
	testImplementation("io.strikt:strikt-core:0.32.0")

	// logging
	implementation("org.apache.logging.log4j:log4j-api-kotlin:$log4jApiKotlinVersion")
	configurations.all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}

	testImplementation("io.mockk:mockk:1.10.6")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
