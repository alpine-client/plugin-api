import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java-library")
    id("maven-publish")
    id("net.kyori.blossom") version "1.2.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = this.compileGroup()
version = this.compileVersion()

val properties = mapOf(
        "pluginVersion" to version,
        "pluginName" to project.properties["plugin_name"],
        "pluginId" to project.properties["plugin_id"]
)

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://lib.alpn.cloud/alpine-public/")
    maven("https://repo.aikar.co/content/groups/aikar/")
}

dependencies {
    compileOnly("io.netty:netty-all:4.0.23.Final")
    compileOnly("org.apache.logging.log4j:log4j-core:2.0-beta9")
    compileOnly("org.spigotmc:spigot-api:${project.properties["spigot_version"]}")

    depend(this, "org.jetbrains:annotations:24.0.1", true)
    depend(this, "co.aikar:acf-paper:0.5.1-SNAPSHOT")
    depend(this, "de.exlll:configlib-spigot:4.2.0")
    depend(this, "org.msgpack:msgpack-core:0.9.8")

    val adventureVer = "4.14.0"
    depend(this, "net.kyori:adventure-platform-bukkit:4.3.0")
    depend(this, "net.kyori:adventure-api:${adventureVer}")
    depend(this, "net.kyori:adventure-text-minimessage:${adventureVer}")
    depend(this, "net.kyori:adventure-text-serializer-legacy:${adventureVer}")

    val lombok = "org.projectlombok:lombok:1.18.30"
    compileOnly(lombok)
    annotationProcessor(lombok)
}

blossom {
    properties.forEach {
        replaceToken("\${${it.key}}", it.value)
    }
}

tasks.withType<Jar> {
    // Rename jar
    archiveFileName.set("${project.properties["plugin_name"]}-$version.jar")

    // Add exclusions
    exclude("META-INF/versions/")
    exclude("META-INF/maven/")
    exclude("javassist/**/*.html")

    // Fill out manifest
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Created-By" to "Gradle",
            "Built-JDK" to "${System.getProperty("java.version")} (${System.getProperty("java.vendor")})",
            "Built-By" to System.getProperty("user.name"),
            "Built-Date" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
            "Name" to project.group.toString().replace(".", "/"),
            "Implementation-Title" to project.properties["plugin_name"],
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "Crystal Development, LLC.",
            "Specification-Title" to project.properties["plugin_name"],
            "Specification-Version" to project.version,
            "Specification-Vendor" to "Crystal Development, LLC.",
        )
    }
}

tasks.withType<ShadowJar> {
    dependsOn("jar")
    outputs.upToDateWhen { false }

    // Relocate dependencies
    val root = "com.alpineclient.dependencies"
    relocate("co.aikar", "$root.co.aikar")
    relocate("de.exlll", "$root.de.exlll")
    relocate("net.md_5", "$root.net.md_5")

    // Add shaded dependencies
    configurations.clear()
    configurations.add(project.configurations.getByName("shadow"))

    // Rename shaded jar
    archiveFileName.set("${project.properties["plugin_name"]}-$version-shaded.jar")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    inputs.properties(properties)

    filesMatching("plugin.yml") {
        expand(properties)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks["jar"])

            pom {
                name.set(project.properties["plugin_name"] as String)
                description.set("Plugin implementation of the Alpine Client API.")
                url.set("https://alpineclient.com/")

                groupId = project.properties["maven_group"] as String
                artifactId = "api-plugin"
                version = compileVersion()
                packaging = "jar"

                withXml {
                    val dependenciesNode = asNode().appendNode("dependencies")

                    configurations["api"].allDependencies.forEach {
                        val dependencyNode = dependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("groupId", it.group)
                        dependencyNode.appendNode("artifactId", it.name)
                        dependencyNode.appendNode("version", it.version)
                        dependencyNode.appendNode("scope", "compile")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            name = "alpine-cloud"
            url = uri("https://lib.alpn.cloud/alpine-public")
            credentials {
                username = System.getenv("ALPINE_MAVEN_NAME")
                password = System.getenv("ALPINE_MAVEN_SECRET")
            }
        }
    }
}

tasks.withType<Javadoc> {
    options {
        this as StandardJavadocDocletOptions
        stylesheetFile = File(projectDir, "/dracula-stylesheet.css")
    }
    setDestinationDir(File(projectDir, "/docs/"))
    include(project.group.toString().replace(".", "/") + "/api/**/*.java")
}

fun compileGroup(): String {
    return "${project.properties["maven_group"]}.${project.properties["maven_artifact"]}"
}

fun compileVersion(): String {
    val major = project.properties["version_major"]
    val minor = project.properties["version_minor"]
    val patch = project.properties["version_patch"]
    val preRelease = project.properties["version_pre_release"]
    return "${major}.${minor}.${patch}${if (preRelease == "none") "" else preRelease}"
}

fun depend(scope: DependencyHandlerScope, dependency: String, api: Boolean = false) {
    scope.implementation(dependency)
    scope.shadow(dependency)
    if (api)
        scope.api(dependency)
}
