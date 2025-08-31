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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Thmanyah"
include(":app")
include(":feature:home")
include(":feature:search")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:design")
include(":core:ui")
include(":core:testing")
include(":benchmark")
include(":core:error")
include(":core:logger")
include(":benchmark")
