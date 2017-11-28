import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-doma-sample1",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "org.seasar.doma" % "doma" % "2.19.0",
      "com.h2database" % "h2" % "1.4.193"
    ),
    // for Doma annotation processor
    compile in Compile := ((compile in Compile) dependsOn (copyResources in Compile)).value,
    compileOrder := CompileOrder.JavaThenScala
  )

