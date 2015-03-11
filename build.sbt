name := "auction-app"

version := "1.0.0"

scalaVersion := "2.11.4"

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Yresolve-term-conflict:package"
)

val junitVersion = "4.11"
val mockitoVersion = "1.10.0"
val scalaTestVersion = "2.2.4"
val scalaArmVersion = "1.4"

libraryDependencies ++= Seq(
        "junit" % "junit" % junitVersion % "test",
        "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
        "org.mockito" % "mockito-core" % mockitoVersion % "test",
        "com.jsuereth" %% "scala-arm" % scalaArmVersion % "test"
)
