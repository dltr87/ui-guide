name := "ui-guide"

lazy val commonSettings = Seq(
  organization := "co.anduin",
  version := "0.1",
  scalaVersion := "2.12.5",
  libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.2.0",
  scalacOptions += "-Yrangepos"
)

lazy val mcro = (project in file("mcro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val core = (project in file("core"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.scala-js" %%% "scalajs-dom" % "0.9.5",
      "com.github.japgolly.scalajs-react" %%% "extra" % "1.2.0",
      "com.github.karasiq" %%% "scalajs-marked" % "1.0.2"
    ),
    jsDependencies ++= Seq(
      "org.webjars.npm" % "react" % "16.3.1"
        / "umd/react.development.js"
        minified "umd/react.production.min.js"
        commonJSName "React",
      "org.webjars.npm" % "react-dom" % "16.3.1"
        / "umd/react-dom.development.js"
        minified "umd/react-dom.production.min.js"
        dependsOn "umd/react.development.js"
        commonJSName "ReactDOM",
      "org.webjars.npm" % "react-dom" % "16.3.1"
        / "umd/react-dom-server.browser.development.js"
        minified "umd/react-dom-server.browser.production.min.js"
        dependsOn "umd/react-dom.development.js"
        commonJSName "ReactDOMServer",
      ProvidedJS / "prism.js",
      "org.webjars.npm" % "marked" % "0.3.19"
        / "lib/marked.js"
        minified "marked.min.js"
        commonJSName "Marked"
    )
  )
  .dependsOn(mcro)

lazy val root = (project in file(".")).aggregate(core)
