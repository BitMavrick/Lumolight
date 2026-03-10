pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Lumolight"
include(":app")
include(":core:theme")
include(":core:ui")
include(":core:data")
include(":feature:screen")
include(":feature:flash")
include(":feature:settings")
include(":core:store")
include(":feature:shortcuts")
include(":core:locales")
include(":feature:both")
include(":feature:lumoFlash")
