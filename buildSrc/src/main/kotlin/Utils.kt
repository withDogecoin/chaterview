object Projects {
    const val GROUP = "team.backend"
    const val VERSIONS = "0.0.1-SNAPSHOT"
}

object Modules {
    const val CORE = ":core"
    const val API = ":api"
    const val CLIENT = ":client"
}

object CompilerOptions {
    const val NULL_SAFETY = "-Xjsr305=strict"
}

object Resources {
    const val YML_SOURCE_PATH = "../chaterview-private"
    const val YML_DESTINATION_PATH = "./src/main/resources"
    const val SWAGGER_SOURCE_PATH = "../chaterview-private/swagger"
    const val SWAGGER_DESTINATION_PATH = "./src/main/resources/static"
}