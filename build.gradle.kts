import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id(Plugins.SPRING_BOOT) version Versions.SPRING_BOOT
	id(Plugins.SPRING_DEPENDENCY_MANAGEMENT) version Versions.SPRING_DEPENDENCY_MANAGEMENT
	kotlin(Plugins.JVM) version Versions.KOTLIN
	kotlin(Plugins.SPRING) version Versions.KOTLIN
	kotlin(Plugins.JPA) version Versions.KOTLIN
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
	group = Projects.GROUP
	version = Projects.VERSIONS

	repositories {
		mavenCentral()
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf(CompilerOptions.NULL_SAFETY)
			jvmTarget = Versions.JVM
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}

subprojects {
	apply(plugin = Plugins.SPRING_BOOT)
	apply(plugin = Plugins.SPRING_DEPENDENCY_MANAGEMENT)
	apply(plugin = Plugins.JETBRAINS_KOTLIN_JVM)
	apply(plugin = Plugins.JETBRAINS_KOTLIN_SPRING)
	apply(plugin = Plugins.JETBRAINS_KOTLIN_JPA)

	allOpen {
		annotation(Annotations.ENTITY)
		annotation(Annotations.MAPPED_SUPER_CLASS)
		annotation(Annotations.EMBEDDEDABLE)
	}

	dependencies {
		implementation(Spring.WEBFLUX)
		implementation(Spring.CONFIGURATION_PROCESSOR)
		implementation(Spring.VALIDATION)
		implementation(Kotlin.JACKSON)
		implementation(Kotlin.REFLECT)
		implementation(Kotlin.EXTENSIONS)
		implementation(Kotlin.COROUTINE_REACTOR)

		implementation(Netty.DNS_RESOLVER_MACOS)

		addDatabaseDependencies()

		testImplementation(Kotest.RUNNER_JUNIT)
		testImplementation(Kotest.ASSERTIONS_CORE)
		testImplementation(Kotest.PROPERTY)
		testImplementation(Spring.TEST)
	}
}

project(Modules.CORE) {
	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

project(Modules.API) {
	dependencies {
		implementation(project(Modules.CORE))
		implementation(project(Modules.CLIENT))

		testImplementation(ProjectReactor.TEST)

		val jar: Jar by tasks
		val bootJar: BootJar by tasks

		bootJar.enabled = true
		jar.enabled = false
	}

	tasks.register<Copy>("copySecretYml") {
		from(Resources.SOURCE_PATH) {
			include("*.yml")
		}
		into(Resources.DESTINATION_PATH)
	}

	tasks.named("compileJava") {
		dependsOn("copySecretYml")
	}
}

project(Modules.CLIENT) {
	dependencies {
		implementation(Spring.WEBFLUX)
	}

	val jar: Jar by tasks
	val bootJar: BootJar by tasks

	bootJar.enabled = false
	jar.enabled = true
}

fun DependencyHandlerScope.addDatabaseDependencies() {
	implementation(Spring.JPA) {
		exclude(Hibernate.ORM, Hibernate.CORE)
	}
	implementation(Hibernate.REACTIVE_CORE_JAKARTA)
	implementation(Line.SPRING_DATA_KOTLIN_JDSL_HIBERNATE_REACTIVE_JAKARTA)
	implementation(Vertx.MYSQL_CLIENT)
	implementation(Database.MYSQL)
	implementation(Mutiny.Kotlin)
}