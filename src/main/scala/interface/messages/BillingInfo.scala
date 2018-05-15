package interface.messages

case class BillingInfo(bankName: String, accNum: String, branchCode: String, isCheque: Boolean, isTransmission: Boolean, isSavings: Boolean)
