import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = URI("https://www.jitpack.io") }
        google()
        mavenCentral()
    }
}

rootProject.name = "GameClient"
include(":app")
 