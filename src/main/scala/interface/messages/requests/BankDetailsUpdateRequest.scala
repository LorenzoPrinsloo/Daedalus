package interface.messages.requests

import interface.messages.{BillingInfo, DateInfo, WorkInfo}


case class BankDetailsUpdateRequest(surname: String,
                                    fullName: String,
                                    mobileNum: String,
                                    email: String,
                                    idNum: String,
                                    signatureURI: String,
                                    company: WorkInfo,
                                    billing: BillingInfo,
                                    date: DateInfo)

