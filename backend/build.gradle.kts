plugins {
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "br.com.sunshine"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
}

val springBootVersion = "3.4.2"
val kotlinVersion = "1.9.25"
val flywayVersion = "9.22.3"
val jacksonKotlinVersion = "2.16.1"
val sqliteVersion = "3.45.1.0"
val junitVersion = "5.10.2"

dependencies {
	// Spring Boot
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
	implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

	// Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

	// Flyway para migrações
	implementation("org.flywaydb:flyway-core:$flywayVersion")

	// Banco de dados SQLite
	implementation("org.xerial:sqlite-jdbc:$sqliteVersion")

	// Ferramentas de desenvolvimento
	developmentOnly("org.springframework.boot:spring-boot-devtools:$springBootVersion")

	// Testes
	testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVersion")
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
