import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.5"
	id("io.spring.dependency-management") version "1.1.0"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("plugin.jpa") version "1.7.22"
}

val kotestVersion = "5.5.5"

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
	group = "team.backend"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

	allOpen {
		annotation("javax.persistence.Entity")
		annotation("javax.persistence.MappedSuperclass")
		annotation("javax.persistence.Embeddable")
	}

	dependencies {
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")

		// kotest
		// https://kotest.io/docs/quickstart
		testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
		testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
		testImplementation("io.kotest:kotest-property:${kotestVersion}")

		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}
}

project(":core") {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project(":api") {
	dependencies {
		implementation(project(":core"))
		implementation(project(":client"))

		// Webflux
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		testImplementation("io.projectreactor:reactor-test")

		// Database
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		// https://mvnrepository.com/artifact/mysql/mysql-connector-java
		runtimeOnly("mysql:mysql-connector-java:8.0.32")
		// Kotlin JDSL: https://github.com/line/kotlin-jdsl
		implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:2.0.4.RELEASE")
		implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:2.0.4.RELEASE")
		implementation("org.hibernate.reactive:hibernate-reactive-core:1.1.9.Final")

		val jar: Jar by tasks
		val bootJar: BootJar by tasks

		bootJar.enabled = true
		jar.enabled = false
	}

	tasks.register<Copy>("copySecretYml") {
		from("../chaterview-private") {
			include("*.yml")
		}
		into("./src/main/resources")
	}

	tasks.named("compileJava") {
		dependsOn("copySecretYml")
	}
}

project(":client") {
	dependencies {
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}