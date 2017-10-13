package middleware

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.google.inject.{Inject, Singleton}

@Singleton
class KafkaApi @Inject()(){



  def source[T](topic:String):Source[T,NotUsed]={
    Source.empty
  }
}
