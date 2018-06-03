package services

import java.io.{IOException, _}
import java.nio.ByteBuffer
import java.nio.channels.{AsynchronousFileChannel, CompletionHandler}
import java.nio.file.Paths
import java.nio.file.StandardOpenOption._

import de.zalando.beard.ast.BeardTemplate
import de.zalando.beard.renderer._
import interface.messages.ErrorPayload
import io.github.cloudify.scala.spdf._
import monix.eval.Task
import org.json4s.native.Serialization.{read, write}
import org.json4s.{DefaultFormats, Formats}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.util.Try
import handles.Beard.pdf
import interface.messages.requests._

trait TemplateFactory {
  implicit val jsonFormats: Formats = DefaultFormats

  def renderTemplate[A <: TemplateForm](data: A)(implicit compiler: CustomizableTemplateCompiler): Task[Either[ErrorPayload, Unit]] =
    Task.eval {
      data.getTemplate()
        .map(template => renderWithContext(data, template))
        .map(writer => writeAsyncPdf(writer.toString, data.templateName))
    }


  private def renderWithContext[A](data: A, template: BeardTemplate)(implicit compiler: CustomizableTemplateCompiler): StringWriter = {
    val context: Map[String, Any] = read[Map[String, Any]](write(data))

    val renderer = new BeardTemplateRenderer(compiler)
    renderer.render(template,
      StringWriterRenderResult(),
      context)
  }

  private def writeHtml(html: String, fileName: String): Unit = {
    val path = new File("./assets/")
    path.mkdir()
    val writer: Writer = new FileWriter(s"$path/$fileName.html")

    try {
      writer.write(html)
      writer.close()
    }
    catch {
      case e: IOException => e.printStackTrace()
    }
  }

  private def writeAsyncHtml(html: String, fileName: String): Task[Unit] = {
    val t = Promise[Array[Byte]]

    try {
      val channel = AsynchronousFileChannel.open(Paths.get(s"./assets/$fileName.html"), CREATE, WRITE)
      val buffer = ByteBuffer.wrap(html.getBytes)
      channel.write(buffer, 0L, buffer, onComplete(channel, t))
    }
    catch {
      case p: Throwable => t.failure(p)
    }

    Task.deferFuture(t.future.map(_ => {}))
  }

  private def onComplete(channel: AsynchronousFileChannel, p: Promise[Array[Byte]]) = {
    new CompletionHandler[Integer, ByteBuffer]() {
      def completed(res: Integer, buffer: ByteBuffer): Unit = {
        p.complete(Try {
          buffer.array()
        })
        closeSafely(channel)
      }

      def failed(t: Throwable, buffer: ByteBuffer): Unit = {
        p.failure(t)
        closeSafely(channel)
      }
    }
  }

  private def closeSafely(channel: AsynchronousFileChannel) =
    try {
      channel.close()
    } catch {
      case e: IOException =>
    }

  private def writeAsyncPdf(html: String, fileName: String)(implicit pdf: Pdf): Unit = pdf.run(html, new File(s"./assets/$fileName.pdf"))
}

object TemplateFactory extends TemplateFactory
