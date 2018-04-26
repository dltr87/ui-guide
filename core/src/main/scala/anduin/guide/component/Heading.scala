package anduin.guide.component

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import anduin.component.util.ComponentUtils
import anduin.style.Style

final case class Heading(
    content: String,
    level: Int,
) {
  def apply(): VdomElement = {
    Heading.component(this)
  }
}

object Heading {

  def getId(text: String): String =
    text
      .replaceAll("<.*?>", "") // HTML tags like <code>Foo</code>
      .toLowerCase()
      .replaceAll("[^a-z]+", "-") // symbols like "," "." "("
      .replaceAll("-$", "") // trailing "-" (originally ".")

  private final val ComponentName = ComponentUtils.name(this)

  private case class Backend(scope: BackendScope[Heading, _]) {
    def render(props: Heading): VdomElement = {
      val id = getId(props.content)
      val tag = props.level match {
        case 1 => <.h2
        case 2 => <.h3
        case 3 => <.h4
      }
      val hash = <.a(Style.position.absolute, ^.right := "calc(100% + 0.5em)", ^.href := s"#$id", "#")
      val content = <.span(^.dangerouslySetInnerHtml := props.content)
      tag(Style.position.relative, ^.id := id, content, hash)
    }
  }

  private val component = ScalaComponent
    .builder[Heading](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build
}