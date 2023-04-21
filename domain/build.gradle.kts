@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `java-library`
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.paging.common)
    implementation(libs.kotlinx.datetime)
    implementation(libs.javax.inject)
}