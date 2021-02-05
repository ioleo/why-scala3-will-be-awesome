import sbt.Keys._ 
import sbt._ 

lazy val root = project
  .in(file(""))
  .settings(
    name := "why-scala3-will-be-awesome",
    version := "0.1.0"
  )
  .aggregate(scala2, scala3)

lazy val scala2 = project
  .in(file("scala2"))
  .settings(
    scalaVersion := "2.13.4",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-unchecked",
      "-language:higherKinds",
      "-language:existentials",
      "-language:implicitConversions",
      "-explaintypes",
      "-Yrangepos",
      "-Xlint:_,-missing-interpolator,-type-parameter-shadow",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings",
      "-Ymacro-annotations"
    ),
    libraryDependencies ++= Seq(
      "tech.units" % "indriya" % "2.1.1"
    )
  )

lazy val scala3 = project
  .in(file("scala3"))
  .settings(
    scalaVersion := "3.0.0-M3",
    scalacOptions := Seq(
      "-language:postfixOps",
      "-language:implicitConversions",
      "-Ykind-projector",
      "-Yexplicit-nulls",
      "-source", "3.1",
      "-Xfatal-warnings"
    ),
    libraryDependencies ++= Seq(
      "tech.units" % "indriya" % "2.1.1"
    )
  )