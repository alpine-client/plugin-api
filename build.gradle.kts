import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("java-library")
    id("maven-publish")
    id("net.kyori.blossom") version "1.3.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    maven("https://repo.panda-lang.org/releases")
}

dependencies {
    compileOnly("org.apache.logging.log4j:log4j-core:2.0-beta9")
    compileOnly("org.spigotmc:spigot-api:${project.properties["spigot_version"]}")

    depend(this, "org.jetbrains:annotations:24.1.0", true)
    depend(this, "dev.tomwmth:configlib-spigot:4.5.0")
    depend(this, "org.msgpack:msgpack-core:0.9.8")

    val liteCommands = "3.4.2"
    depend(this, "dev.rollczi:litecommands-bukkit:${liteCommands}")
    depend(this, "dev.rollczi:litecommands-adventure-platform:${liteCommands}")

    val adventure = "4.17.0"
    depend(this, "net.kyori:adventure-platform-bukkit:4.3.2")
    depend(this, "net.kyori:adventure-api:${adventure}")
    depend(this, "net.kyori:adventure-text-minimessage:${adventure}")
    depend(this, "net.kyori:adventure-text-serializer-legacy:${adventure}")

    val lombok = "org.projectlombok:lombok:1.18.34"
    compileOnly(lombok)
    annotationProcessor(lombok)
}

java {
    withSourcesJar()
    withJavadocJar()
}

blossom {
    properties.forEach {
        replaceToken("\${${it.key}}", it.value)
    }
}

base {
    archivesName.set(project.properties["plugin_name"] as String)
}

tasks.jar {
    // Add exclusions
    exclude("META-INF/versions/")
    exclude("META-INF/maven/")

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

    // Add license
    from("LICENSE") {
        into("META-INF/")
    }
}

tasks.shadowJar {
    dependsOn("jar")
    outputs.upToDateWhen { false }

    // Relocate dependencies
    val root = "com.alpineclient.dependencies"
    relocate("de.exlll", "$root.de.exlll")
    relocate("dev.rollczi", "$root.dev.rollczi")
    relocate("net.jodah", "$root.net.jodah")
    relocate("net.kyori", "$root.net.kyori")
    relocate("org.intellij", "$root.org.intellij")
    relocate("org.jetbrains", "$root.org.jetbrains")
    relocate("org.msgpack", "$root.org.msgpack")
    relocate("org.snakeyaml", "$root.org.snakeyaml")
    relocate("panda", "$root.panda")

    // Add shaded dependencies
    configurations.clear()
    configurations.add(project.configurations.getByName("shadow"))

    // Set classifier
    archiveClassifier.set("shaded")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    inputs.properties(properties)

    filesMatching("plugin.yml") {
        expand(properties)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifact(tasks["jar"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set(project.properties["plugin_name"] as String)
                description.set("Plugin implementation of the Alpine Client API.")
                url.set("https://alpineclient.com/")

                groupId = project.properties["maven_group"] as String
                artifactId = "api-plugin"
                packaging = "jar"

                licenses {
                    license {
                        name = "Mozilla Public License, version 2.0"
                        url = "https://www.mozilla.org/en-US/MPL/2.0/"
                    }
                }

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
            name = "AlpnCloud"
            url = uri("https://lib.alpn.cloud/alpine-public")
            credentials {
                username = System.getenv("ALPINE_MAVEN_NAME")
                password = System.getenv("ALPINE_MAVEN_SECRET")
            }
        }
    }
}

tasks.javadoc {
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
