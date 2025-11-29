// build.gradle.kts (RAIZ do projeto)

plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
}

// opcional, mas costuma vir no template
tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}