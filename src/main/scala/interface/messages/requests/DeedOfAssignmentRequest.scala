package interface.messages.requests

import interface.messages.DateInfo

case class DeedOfAssignmentRequest(date: DateInfo, fullName: String, idNum: String, signatureURI: String)
