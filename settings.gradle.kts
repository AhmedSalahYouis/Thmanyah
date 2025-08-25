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
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:ui")
include(":core:testing")
include(":benchmarks")
include(":core:error")
include(":core:logger")
