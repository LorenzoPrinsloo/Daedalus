package interface.messages.requests

import interface.messages.{DateInfo, PerformanceInfo}

case class LivePerformancesRequest(fullName: String,
                                   role: String,
                                   eventName: String,
                                   venueName: String,
                                   venueAddress: String,
                                   eventStartDate: DateInfo,
                                   eventEndDate: DateInfo,
                                   performances: Seq[PerformanceInfo],
                                   signatureURI: String,
                                   date: DateInfo
                                  ) extends TemplateForm("LivePerformancesform")

