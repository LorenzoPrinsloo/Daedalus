package interface.messages

import java.time.LocalDateTime

object BankDetailsUpdate {
  case class BankDetailsUpdateRequest(personal: PersonalInfo,
                                      identification: IdentificationInfo,
                                      company: WorkInfo,
                                      billing: BillingInfo,
                                      createdDate: String = LocalDateTime.now().toString)

  case class PersonalInfo(surname: String, fullName: String, mobileNum: String, email: String)
  case class IdentificationInfo(idNum: String, signatureURI: String)
  case class WorkInfo(name: String, regNum: String, mobileNum: String, email: String)
  case class BillingInfo(bankName: String, accNum: String, branchCode: String, isCheque: Boolean, isTransmission: Boolean, isSavings: Boolean)
}
