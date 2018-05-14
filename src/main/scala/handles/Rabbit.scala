package handles

import handles.rabbit.messages.Configuration
import handles.rabbit.{RabbitConnector, RabbitPublisher}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global

object Rabbit {
  private val config = Configuration(
    host        = sys.env.getOrElse("QUEUE_IP", "chimaera.lxcff1.fullfacing.com"),
    port        = sys.env.getOrElse("QUEUE_PORT", "5672").toInt,
    username    = sys.env.getOrElse("QUEUE_USERNAME", "gkt-dev"),
    password    = sys.env.getOrElse("QUEUE_PASSWORD", "gkt-dev"),
    virtualHost = sys.env.getOrElse("QUEUE_VIRTUALHOST", "gkt-dev")
  )

  // RabbitMQ Connection object, handles connection and channel creation.
  final val connection: RabbitConnector = RabbitConnector.create(config)
  implicit val publisher: RabbitPublisher = new RabbitPublisher(connection)

  /**
    * Provides an interface to initialize a connection to a an external resource.
    * This allows for controlled initialization of resources like database, message queue and cache connections.
    *
    * An object that represents a resource that should have controlled initialization should implement
    * this trait and provide an implementation.
    *
    * @return Unit
    */
  def connect(): Task[Unit] = Task.eval {
    publisher.connect
  }
}
