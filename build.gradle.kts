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
	// https://mvnrepository.com/artifact/mysql/mysql-connector-java
	runtimeOnly("mysql:mysql-connector-java:8.0.32")

	/**
	 * Kotlin JDSL with SpringBoot 2.x version
	 *   - https://github.com/line/kotlin-jdsl
	 *   - implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-starter:2.0.4.RELEASE")
	 *   - implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive:2.0.4.RELEASE")
	 */

	/**
	 * Kotlin JDSL with Spring Boot 3.x version
	 *   - https://github.com/line/kotlin-jdsl/blob/main/spring/data-reactive-core/README.md
	 */
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
		exclude(group = "org.hibernate", module = "hibernate-core")
	}
	implementation("com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:2.2.1.RELEASE")
//	implementation("org.springframework.data:spring-data-commons:x.y.z")
	implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:1.1.9.Final") {
		exclude(module = "hibernate-commons-annotations")
	}
	implementation("io.smallrye.reactive:mutiny-kotlin:2.2.0")

	/**
	 * Low level Dependencies with Spring Boot 3.x version
	 *   - LINE Lib versions - https://github.com/line/kotlin-jdsl/blob/main/libs.versions.toml
	 *   - implementation("org.hibernate.reactive:hibernate-reactive-core-jakarta:1.1.9.Final")
	 *   - implementation("org.hibernate.reactive:hibernate-reactive-core:1.1.9.Final")
	 *   - compileOnly("jakarta.persistence:jakarta.persistence-api:3.1.0")
	 *   - compileOnly("org.hibernate:hibernate-core:6.2.2.Final")
	 *   - implementation("io.smallrye.reactive:mutiny:2.2.0")
	 *   - implementation("io.smallrye.reactive:mutiny-kotlin:2.2.0")
	 */
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
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
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

		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
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