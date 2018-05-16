import java.time.{Duration, LocalDateTime}
import java.time.format._
import java.util.Locale._

import handles.Beard.templateCompiler
import interface.messages._
import interface.messages.requests.{BankDetailsUpdateRequest, DeedOfAssignmentRequest, LivePerformancesRequest, NotificationOfWorksRequest}
import monix.execution.Scheduler.Implicits.global
import services.TemplateFactory

object Main extends App {

  val currentTime = LocalDateTime.now()

  val bankInfo = BankDetailsUpdateRequest(
    "Prinsloo",
    "Carel Magnus Lorenzo Prinsloo",
    "0607812240",
    "cmlprinsloo@gmail.com",
    "9506235180087",
    "/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg",
    company = WorkInfo("Full Facing","45546967983143","0657438798","ff@email.com"),
    billing = BillingInfo("FNB","9876543123","250054", false, false, true),
    date = DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear)
  )

  val deedOfAssignment = DeedOfAssignmentRequest(
    date = DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear),
    fullName = "Carel Magnus Lorenzo Prinsloo",
    idNum = "9506235180087",
    signatureURI = "/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg"
  )

  val performance = LivePerformancesRequest(
    "Carel Magnus Lorenzo Prinsloo",
    "Developer",
    "Into The Wild",
    "Stellenbosch Farm",
    "102 wyn straat, Stellenbosch, 7600",
    DateInfo(LocalDateTime.MIN.getDayOfMonth, LocalDateTime.MIN.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear),
    DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear),
    Seq(
      PerformanceInfo("Sic Trance Mix", "Astrix feat Lorenzo", "Astrix", "Me", 1, "1 Hour"),
      PerformanceInfo("Darkpsy Jungle Mix", "Sphongle feat Lorenzo", "Sphongle", "Me", 2, "30 Minutes")
    ),
    signatureURI = "/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg",
    DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear)
  )

  val NoW = NotificationOfWorksRequest(
    "Carel Magnus Lorenzo Prinsloo",
    "9506235180087",
    "Inc Audio",
    "REGNUM001",
    true,
    false,
    false,
    false,
    "0607812240",
    "cmlprinsloo@gmail.com",
    DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear),
    "/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg",
    "/home/lorenzo/Documents/Daedalus/src/main/scala/services/assets/signature.jpg",
    Seq(
      WorkNotification(
        true,
        false,
        false,
        "ICARUS",
        "RM",
        "1 Hour",
        "Icarus",
        Seq(
          RightHolder(
            "Lorenzo Prinsloo",
            "Developer",
            "65"
          ),
          RightHolder(
            "Lindsay",
            "CEO",
            "25"
          )
        )
      ),
      WorkNotification(
        true,
        false,
        false,
        "ICARUS",
        "RM",
        "1 Hour",
        "Icarus",
        Seq(
          RightHolder(
            "Lorenzo Prinsloo",
            "Developer",
            "65"
          ),
          RightHolder(
            "Lindsay",
            "CEO",
            "25"
          )
        )
      )
    )
  )

  val beforeOp = LocalDateTime.now()
  val res = TemplateFactory.renderTemplate(NoW, "NotificationOfWorksForm", "NoW_Lorenzo").runAsync.value.get.get
  val afterOp = LocalDateTime.now()
  println(s"RESULT ${if(res.isRight) "SUCCESS" else "FAILURE"} TIME: ${Duration.between(beforeOp, afterOp)}")
}
