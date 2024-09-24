// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    // Unable to find method ''java.lang.String com.squareup.javapoet.ClassName.canonicalName()
    //https://github.com/google/dagger/issues/4048#issuecomment-2151459326
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.android.library) apply false
}