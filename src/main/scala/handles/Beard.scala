package handles

import de.zalando.beard.renderer.{ClasspathTemplateLoader, CustomizableTemplateCompiler}

object Beard {
  val loader = new ClasspathTemplateLoader(templatePrefix = "/template/", templateSuffix = ".html")
  implicit val templateCompiler: CustomizableTemplateCompiler = new CustomizableTemplateCompiler(loader)
}
