import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory

@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    id("jacoco")
}

android {
    namespace = "com.centroi.alsuper.feature.contacts"
    compileSdk = 35

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "com.centroi.alsuper.core.testing.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
        aidl = false
        buildConfig = false
        renderScript = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        //freeCompilerArgs = listOf("-Xno-inline", "-Xdebug-info")
        jvmTarget = "17"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":core-database"))
    androidTestImplementation(project(":core-testing"))

    // Core Android dependencies
    implementation(libs.androidx.activity.compose)

    // Arch Components
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    // Tooling
    debugImplementation(libs.androidx.compose.ui.tooling)
    // Instrumented tests
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Hilt and instrumented tests.
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)
    // Hilt and Robolectric tests.
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.androidx.test.core)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.robolectric)

    // Mockito dependencies
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.inline) // Enables mocking final classes

    // Instrumented tests: jUnit rules and runners
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.hilt.navigation.testing)

}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "verification" // Ensures it appears under "Verification" in Android Studio
    description = "Generates Jacoco test coverage reports."

    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val fileFilter = listOf(
        "**/R.class", "**/R\$*.class", "**/BuildConfig.*", "**/Manifest*.*", // Common excludes
        "**/*HiltModules*", "**/hilt_aggregated_deps/**", // Hilt-generated files
        "**/*_Factory*", "**/*_ProvideFactory*", "**/*_MembersInjector*", // Dagger/Hilt Factories
        "**/ComposableSingletons**", // Jetpack Compose auto-generated classes
        "**/*Composable*lambda*", // Exclude Compose lambda classes
        "**/*Composable*Impl*", // Exclude Compose lambda implementations
        "**/*ComposableSingletons*", // Exclude Singleton objects for Composables
    )

    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }
    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    sourceDirectories.setFrom(files("src/main/java"))
    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    executionData.setFrom(files("${buildDir}/jacoco/testDebugUnitTest.exec"))
}

tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification") {
    group = "verification"
    description = "Verifies the test coverage against the minimum threshold."

    dependsOn("jacocoTestReport")
    mustRunAfter("jacocoTestReport")

    violationRules {
        rule {
            enabled = true
            element = "CLASS"

            // Check line coverage
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = BigDecimal("0.80") // 80% required
            }

            // Check branch coverage
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = BigDecimal("0.75")
            }
        }
    }

    doLast {
        val reportFile = file("$buildDir/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")

        if (!reportFile.exists()) {
            throw GradleException("🚨 Jacoco XML report not found! 🚨")
        }

        println("📢 Parsing Jacoco report: ${reportFile.absolutePath}")

        val factory = DocumentBuilderFactory.newInstance().apply {
            setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            setFeature("http://xml.org/sax/features/validation", false)
        }

        val builder = factory.newDocumentBuilder()
        val doc: Document = reportFile.inputStream().use { builder.parse(it) }

        val uncoveredLines = mutableMapOf<String, MutableList<String>>()
        val processedClasses = mutableListOf<String>()

        val classNodes = doc.getElementsByTagName("class")
        for (i in 0 until classNodes.length) {
            val classNode = classNodes.item(i)
            val className = classNode.attributes.getNamedItem("name").nodeValue
            processedClasses.add(className)

            val lineNodes = classNode.childNodes
            for (j in 0 until lineNodes.length) {
                val lineNode = lineNodes.item(j)
                if (lineNode.nodeName == "counter") {
                    val missed = lineNode.attributes.getNamedItem("missed").nodeValue.toInt()
                    val type = lineNode.attributes.getNamedItem("type").nodeValue

                    if (missed > 0 && type in listOf("LINE", "INSTRUCTION", "METHOD", "CLASS")) {
                        uncoveredLines.computeIfAbsent(className) { mutableListOf() }
                            .add("$type missed: $missed")
                    }
                }
            }
        }

        // Debug print to ensure classes are being processed
        println("📌 Jacoco Report Processed These Classes:")
        processedClasses.forEach { println("  ✅ $it") }

        if (uncoveredLines.isNotEmpty()) {
            println("🚨 Jacoco Coverage Check Failed! 🚨\n")
            println("📌 **Missing Coverage:**")
            uncoveredLines.forEach { (className, details) ->
                println("  ❌ $className: ${details.joinToString(", ")}")
            }
            throw GradleException("🚨 Code coverage is insufficient! See logs for uncovered lines. 🚨")
        } else {
            println("✅ All required lines are covered! ✅")
        }
    }


    executionData.setFrom(fileTree(buildDir) {
        include("**/jacoco/testDebugUnitTest.exec") // Ensure correct file
    })
}

detekt {
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    autoCorrect = true
}

tasks.named<io.gitlab.arturbosch.detekt.Detekt>("detekt").configure {
    group = "verification"
    description = "Runs Detekt only for feature-contacts"

    setSource(files("src/main/java", "src/main/kotlin"))
    include("**/*.kt", "**/*.kts")
    exclude("**/build/**")

    reports {
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/detekt.html")
        }
        xml.enabled = false
        txt.enabled = false
    }

    // Optional: disable compile classpath usage
    classpath = files()
}