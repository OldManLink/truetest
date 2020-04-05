name := "truetest"

version := "0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  guice,
  specs2 % Test,
  "org.mockito" % "mockito-core" % "2.7.22" % Test
)

coverageExcludedPackages := ".*Reverse.*;.*Routes.*;"

scalacOptions ++= Seq("-deprecation", "-feature", "-language:postfixOps")
