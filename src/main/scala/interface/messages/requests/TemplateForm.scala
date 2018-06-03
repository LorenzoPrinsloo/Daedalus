package interface.messages.requests

import de.zalando.beard.ast.BeardTemplate
import de.zalando.beard.renderer.{CustomizableTemplateCompiler, TemplateName}
import interface.messages.ErrorPayload

abstract class TemplateForm(val templateName: String) {

  final def getTemplate()(implicit compiler: CustomizableTemplateCompiler): Either[ErrorPayload, BeardTemplate] = {
    val template = compiler.compile(TemplateName(templateName))
    Either.cond(template.isSuccess, template.get, ErrorPayload(404, s"Cannot find template $templateName"))
  }
}
