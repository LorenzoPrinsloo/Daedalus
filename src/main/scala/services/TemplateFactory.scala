package services

import java.io._

import de.zalando.beard.ast.BeardTemplate
import de.zalando.beard.renderer._
import interface.messages.ErrorPayload
import monix.eval.Task
import org.json4s.native.Serialization.{read, write}
import org.json4s.{DefaultFormats, Formats}

trait TemplateFactory {
  implicit val jsonFormats: Formats = DefaultFormats

  def renderTemplate[A](data: A, templateName: String)(implicit compiler: CustomizableTemplateCompiler): Task[Either[ErrorPayload, Unit]] =
    Task.eval(getTemplate(templateName)
      .map(template => renderWithContext(data, template))
      .map(writer => writeHtml(writer.toString)))


  private def getTemplate(name: String)(implicit compiler: CustomizableTemplateCompiler): Either[ErrorPayload, BeardTemplate] = {
    val template = compiler.compile(TemplateName(name))
    Either.cond(template.isSuccess, template.get, ErrorPayload(404, s"Cannot find template $name"))
  }

  private def renderWithContext[A](data: A, template: BeardTemplate)(implicit compiler: CustomizableTemplateCompiler): StringWriter = {
    val context: Map[String, Any] = read[Map[String, Any]](write(data))

    val renderer = new BeardTemplateRenderer(compiler)
    renderer.render(template,
      StringWriterRenderResult(),
      context)
  }

  private def writeHtml(html: String): Unit = {
    val path = new File("./assets/")
    path.mkdir()
    val writer: Writer = new FileWriter(s"$path/test.html")

    try {
      writer.write(html)
      writer.close()
    }
    catch {
      case e: IOException => e.printStackTrace()
    }
  }
}

object TemplateFactory extends TemplateFactory
