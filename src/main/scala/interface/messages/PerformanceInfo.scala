package interface.messages

case class PerformanceInfo(title: String,
                           composer: String,
                           author: String,
                           performer: String,
                           timesPerformed: Int,
                           duration: String)
