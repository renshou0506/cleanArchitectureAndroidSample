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

rootProject.name = "LearningCleanArchitecture"
// 依存関係に他のモジュールを加える記述で使用.
// implementation(projects.core.database)のような書き方をするときに必要な記述
enableFeaturePreview(("TYPESAFE_PROJECT_ACCESSORS"))
include(":app")
include(":core:database")
