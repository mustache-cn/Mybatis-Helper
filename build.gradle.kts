plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.5.2"
}

group = "cn.com.mustache.mybatis"
version = "0.1.201"

repositories {
    maven(url = "https://maven.aliyun.com/nexus/content/groups/public/")
    maven(url = "https://maven.aliyun.com/repository/gradle-plugin/")
    maven(url = "https://maven.aliyun.com/repository/jcenter/")
    maven(url = "https://maven.aliyun.com/repository/central/")
    maven(url = "https://maven.aliyun.com/repository/google/")
    mavenCentral()
}

dependencies {
    implementation(fileTree("lib") { include("*.jar") })
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

// Configure Gradle IntelliJ Plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    version.set("2021.2")
    type.set("IU") // Target IDE Platform

    plugins.set(listOf("java", "com.intellij.database", "com.intellij.spring"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("212")
        untilBuild.set("222.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
