package interface.messages.requests

import interface.messages.{DateInfo, WorkNotification}

case class NotificationOfWorksRequest(fullName: String,
                                      idNumber: String,
                                      publisher: String,
                                      registrationNumber: String,
                                      isComposer: Boolean,
                                      isPublisher: Boolean,
                                      isCatalogue: Boolean,
                                      isSubPublisher: Boolean,
                                      contactNumber: String,
                                      email: String,
                                      date: DateInfo,
                                      signatureComposerURI: String,
                                      signaturePublisherURI: String,
                                      workNotifications: Seq[WorkNotification]
                                     ) extends TemplateForm("NotificationOfWorksForm")
