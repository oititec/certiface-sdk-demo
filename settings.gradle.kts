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
        mavenLocal()
        google()
        mavenCentral()
        maven("https://raw.githubusercontent.com/oititec/android-oiti-sdk-versions/main")
        maven("https://raw.githubusercontent.com/oititec/certiface-doc-versions/main")
    }
}

rootProject.name = "certiface-sdk-demo"
include(":app")