package interface.messages

case class WorkNotification(isIV: Boolean,
                            isVS: Boolean,
                            isIS: Boolean,
                            title: String,
                            alternateTitle: String,
                            duration: String,
                            groupName: String,
                            rightHolders: Seq[RightHolder]
                           )
