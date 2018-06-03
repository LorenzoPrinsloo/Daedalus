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

  val signature = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4QBoRXhpZgAASUkqAAgAAAAEABoBBQABAAAAPgAAABsBBQABAAAARgAAACgBAwABAAAAAgAAADEBAgASAAAATgAAAAAAAABgAAAAAQAAAGAAAAABAAAAUGFpbnQuTkVUIHYzLjUuMTAA/9sAQwACAQEBAQECAQEBAgICAgIEAwICAgIFBAQDBAYFBgYGBQYGBgcJCAYHCQcGBggLCAkKCgoKCgYICwwLCgwJCgoK/9sAQwECAgICAgIFAwMFCgcGBwoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoK/8AAEQgAoAEsAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A/fyiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACi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n7PPbEs1tLG0iNDczxtG5aN1kYMprsaKAMzwd4L8H/DvwzZ+C/AHhTTdD0fT4vKsNK0ixjtra2TOdsccYCoMk8ADrV+7u7WwtZb6+uY4YIY2kmmlcKkaAZLMTwABySakrxz9oq11P44eM9N/ZN0y2nXQ9Tsl1X4m34QhBoYkZI9MVuhe/ljkicDOLWC85R2hJAD9mK1ufitrus/te+ILaRP+Ewgjs/AltOhDWXhmFma2k2nlZLyRnvX4VvLltYnG62FWf26T9j/ZS8X+KSfk8M29t4jl/wBzTLuHUG/8dtjXrMcccMaxRIFVQAqqMAAdABWP8RvA2ifFD4e698NPE0RfTfEWjXWmagg6tBcRNFIP++XNAGzRXnf7JvjzW/iP+zh4P8S+LJQ2vR6Olh4oUf8ALPV7Qm0v4+f7l3BOn/Aa9EoAigsbK1nlubazijkuGDTyRxgNIQMAsR1OOOaloooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDxjwreL+z3+0dqfw81g+T4U+KWpSav4QumOIrTXvK3ahpnohuFiN/F1Mkh1DONqBvZ6wPih8MfBnxi8D3vw98faW11p16EY+TcPDNbzRuskNxDLGQ8E8UiJJHKhV43RWUgqDXmWifGbxx+zpf23w//AGrtVW70SSZLbw/8XFgSK0uyxCxwasiAJp92SQomAW0nfG0wSSJbUAe2UUAgjIOQehooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAqDU9M03WtOuNH1jT4Lu0u4WhurW5iEkc0bAhkdWBDKQSCDwQanooA8c0z9nX4i/BS5T/AIZd+J0Vh4eWUFvhz4vt5L3SrePPMenzqwudNGOFjzPbRKAsdsgr2OiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooA//9k="

  val bankInfo = BankDetailsUpdateRequest(
    "Prinsloo",
    "Carel Magnus Lorenzo Prinsloo",
    "0607812240",
    "cmlprinsloo@gmail.com",
    "9506235180087",
    signature,
    company = WorkInfo("Full Facing","45546967983143","0657438798","ff@email.com"),
    billing = BillingInfo("FNB","9876543123","250054", false, false, true),
    date = DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear)
  )

  val deedOfAssignment = DeedOfAssignmentRequest(
    date = DateInfo(currentTime.getDayOfMonth, currentTime.getMonth.getDisplayName(TextStyle.FULL, ENGLISH), currentTime.getYear),
    fullName = "Carel Magnus Lorenzo Prinsloo",
    idNum = "9506235180087",
    signatureURI = signature
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
    signatureURI = signature,
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
    signature,
    signature,
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
