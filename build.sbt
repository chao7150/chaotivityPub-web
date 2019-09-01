lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  jdbc,
  guice,
  "com.h2database" % "h2" % "1.4.199",
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.5",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.5",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.1-scalikejdbc-3.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % "test"
)
