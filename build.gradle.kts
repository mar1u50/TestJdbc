plugins {
    id("java")
    id("application")
}

group = "com.seeq.jdbctester"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("commons-cli:commons-cli:1.5.0")

    implementation("com.microsoft.sqlserver:mssql-jdbc:9.4.0.jre8")
    implementation("com.microsoft.azure:msal4j:1.11.0")

    // SAP Hana has a Developer License: https://tools.hana.ondemand.com/developer-license-3_1.txt
    implementation("com.sap.cloud.db.jdbc:ngdbc:2.7.9")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

application {
    mainClass.set("com.seeq.jdbctester.JdbcTester")
}