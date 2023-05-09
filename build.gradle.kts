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

fun DependencyHandlerScope.addDatabaseDependencies() {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
		exclude("org.hibernate.orm", "hibernate-core")
	}
	/*
		LINE Lib versions - https://github.com/line/kotlin-jdsl/blob/main/libs.versions.toml
		Kotlin JDSL with Spring Boot 3.x version
	 	    https://github.com/line/kotlin-jdsl/blob/main/spring/data-reactive-core/README.md
	 	Kotlin JDSL Examples
	 	    https://github.com/cj848/kotlin-jdsl-example
	 */
	implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:1.1.9.Final")
	implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:2.2.1.RELEASE")

	implementation("io.vertx:vertx-mysql-client:4.4.1")
	implementation("mysql:mysql-connector-java:8.0.33")

	implementation("io.smallrye.reactive:mutiny-kotlin:2.2.0")
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

	allOpen {
		annotation("jakarta.persistence.Entity")
		annotation("jakarta.persistence.MappedSuperclass")
		annotation("jakarta.persistence.Embeddable")
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		implementation("org.springframework.boot:spring-boot-configuration-processor")

		// https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos/4.1.92.Final
		implementation("io.netty:netty-resolver-dns-native-macos:4.1.92.Final:osx-aarch_64")

		addDatabaseDependencies()

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

		testImplementation("io.projectreactor:reactor-test")


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
		implementation("org.springframework.boot:spring-boot-starter-webflux")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}