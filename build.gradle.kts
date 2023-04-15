import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "3.0.5" apply false
	id("io.spring.dependency-management") version "1.1.0" apply false
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22" apply false
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
	dependencies {
		// r2dbc
		// https://mvnrepository.com/artifact/org.springframework.data/spring-data-r2dbc
		// TODO please uncomment after setting up r2dbc properties
		// implementation("org.springframework.data:spring-data-r2dbc:3.0.4")
		// implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project(":api") {
	dependencies {
		implementation(project(":core"))
		implementation(project(":client"))

		// webflux
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		testImplementation("io.projectreactor:reactor-test")

		// r2dbc
		// https://mvnrepository.com/artifact/org.springframework.data/spring-data-r2dbc
		// TODO please uncomment after setting up r2dbc properties
		// implementation("org.springframework.data:spring-data-r2dbc:3.0.4")
		// implementation("dev.miku:r2dbc-mysql:0.8.2.RELEASE")
	}
}

project(":client") {
	dependencies {
	}
}