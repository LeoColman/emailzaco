import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.0.RC1"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
}

group = "br.org.institutoops"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	
	// Spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	
	// Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	
	// Testing
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
	testImplementation("io.kotlintest:kotlintest-extensions-spring:3.3.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
