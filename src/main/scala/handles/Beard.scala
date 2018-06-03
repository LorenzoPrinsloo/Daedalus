package handles

import de.zalando.beard.renderer.{ClasspathTemplateLoader, CustomizableTemplateCompiler}
import io.github.cloudify.scala.spdf.{Pdf, PdfConfig, Portrait}

object Beard {
  val loader = new ClasspathTemplateLoader(templatePrefix = "/template/", templateSuffix = ".html")
  implicit val templateCompiler: CustomizableTemplateCompiler = new CustomizableTemplateCompiler(loader)

  implicit val pdf: Pdf = Pdf("C://Vars/wkhtmltopdf/bin/wkhtmltopdf.exe",new PdfConfig {
    orientation := Portrait
    pageSize := "A4"
    marginTop := "1in"
    marginBottom := "1in"
    marginLeft := "1in"
    marginRight := "1in"
  })
}
