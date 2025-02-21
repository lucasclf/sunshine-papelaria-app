buildscript {
	repositories {
		mavenCentral()
	}
}

repositories {
	mavenCentral()
}

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	application
}

group = "br.com.sunshine"

subprojects {
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "kotlin")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependecies:${property("springCloudVersion")}")
		}
	}

	kotlin {
		compilerOptions {
			freeCompilerArgs.addAll("-Xjsr305=strict")
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

dependencies {
	testImplementation(kotlin("test"))
}

kotlin {
	jvmToolchain(21)
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
	mainClass.set("br.com.sunshine.SunshineApiApplication")
}
