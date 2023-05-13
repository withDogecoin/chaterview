object Kotlin {
    const val JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val EXTENSIONS = "io.projectreactor.kotlin:reactor-kotlin-extensions"
    const val COROUTINE_REACTOR = "org.jetbrains.kotlinx:kotlinx-coroutines-reactor"
}

object Spring {
    const val WEBFLUX = "org.springframework.boot:spring-boot-starter-webflux"
    const val JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val TEST = "org.springframework.boot:spring-boot-starter-test"
    const val CONFIGURATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"
}

object Netty {
    // https://mvnrepository.com/artifact/io.netty/netty-resolver-dns-native-macos/4.1.92.Final
    const val DNS_RESOLVER_MACOS = "io.netty:netty-resolver-dns-native-macos:${Versions.NETTY_DNS_RESOLVER_MACOS}:osx-aarch_64"
}


object Kotest {
    // https://kotest.io/docs/quickstart
    const val RUNNER_JUNIT = "io.kotest:kotest-runner-junit5:${Versions.KOTEST}"
    const val ASSERTIONS_CORE = "io.kotest:kotest-assertions-core:${Versions.KOTEST}"
    const val PROPERTY = "io.kotest:kotest-property:${Versions.KOTEST}"
}

object Hibernate {
    const val ORM = "org.hibernate.orm"
    const val CORE = "hibernate-core"
    const val REACTIVE_CORE_JAKARTA = "org.hibernate.reactive:hibernate-reactive-core-jakarta:${Versions.HIBERNATE_REACTIVE_CORE_JAKARTA}"
}

object Line {
    /*
        LINE Lib versions - https://github.com/line/kotlin-jdsl/blob/main/libs.versions.toml
        Kotlin JDSL with Spring Boot 3.x version
             https://github.com/line/kotlin-jdsl/blob/main/spring/data-reactive-core/README.md
         Kotlin JDSL Examples
             https://github.com/cj848/kotlin-jdsl-example
    */
    const val SPRING_DATA_KOTLIN_JDSL_HIBERNATE_REACTIVE_JAKARTA = "com.linecorp.kotlin-jdsl:spring-data-kotlin-jdsl-hibernate-reactive-jakarta:${Versions.SPRING_DATA_KOTLIN_JDSL_HIBERNATE_REACTIVE_JAKARTA}"
}

object Vertx {
    const val MYSQL_CLIENT = "io.vertx:vertx-mysql-client:${Versions.VERTX_MYSQL_CLIENT}"
}

object Database {
    const val MYSQL = "mysql:mysql-connector-java:${Versions.MYSQL_CONNECTOR}"
}

object Mutiny {
    const val Kotlin = "io.smallrye.reactive:mutiny-kotlin:${Versions.MUTINY_KOTLIN}"
}

object ProjectReactor {
    const val TEST = "io.projectreactor:reactor-test"
}