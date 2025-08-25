// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.detekt) apply false
}

// Configure Detekt to exclude test files using Gradle's SourceTask
subprojects {
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        // Exclude test directories and files
        exclude("**/test/**")
        exclude("**/androidTest/**")
        exclude("**/commonTest/**")
        exclude("**/jvmTest/**")
        exclude("**/androidUnitTest/**")
        exclude("**/androidInstrumentedTest/**")
        exclude("**/jsTest/**")
        exclude("**/iosTest/**")
        exclude("**/*Test.kt")
        exclude("**/*Tests.kt")
        exclude("**/testFixtures/**")
        exclude("**/build/**")
        exclude("**/.gradle/**")
    }
    
    tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
        // Exclude test directories and files for baseline tasks too
        exclude("**/test/**")
        exclude("**/androidTest/**")
        exclude("**/commonTest/**")
        exclude("**/jvmTest/**")
        exclude("**/androidUnitTest/**")
        exclude("**/androidInstrumentedTest/**")
        exclude("**/jsTest/**")
        exclude("**/iosTest/**")
        exclude("**/*Test.kt")
        exclude("**/*Tests.kt")
        exclude("**/testFixtures/**")
        exclude("**/build/**")
        exclude("**/.gradle/**")
    }
}
