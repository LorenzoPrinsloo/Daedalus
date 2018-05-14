import interface.messages.BankDetailsUpdate._
import services.TemplateFactory
import handles.Beard.templateCompiler
import monix.execution.Scheduler.Implicits.global

object Main extends App {

  val bankInfo = BankDetailsUpdateRequest(
    personal = PersonalInfo("Prinsloo","Carel Magnus Lorenzo Prinsloo","0607812240","cmlprinsloo@gmail.com"),
    identification = IdentificationInfo("9506235180087","/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg"),
    company = WorkInfo("Full Facing","45546967983143","0657438798","ff@email.com"),
    billing = BillingInfo("FNB","9876543123","250054", false, false, true)
  )

  val res = TemplateFactory.renderTemplate(bankInfo, "BankingDetailsUpdateForm").runAsync.value.get
  print(s"RESULT $res")
}
