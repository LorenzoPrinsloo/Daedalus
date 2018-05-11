package handles.rabbit.messages

import com.rabbitmq.client.AMQP.BasicProperties
import handles.rabbit.encoder.ByteEncoder

case class Message(bytes: Array[Byte], encoding: String, routing: RoutingKey, options: Option[BasicProperties] = None) {
  self =>
  def encode: Message = {
    self.copy(bytes = ByteEncoder.encode(bytes, encoding))
  }
}
