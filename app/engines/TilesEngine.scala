package engines

import akka.actor.{Actor, Props}
import com.auditest.models.CountPerTile
import com.google.inject.{Inject, Singleton}
import engines.LastCountsActor.{Get, Push}
import middleware.KafkaApi
import models.VehicleEvent
import util.RottingList

import scala.compat.Platform
import scala.concurrent.duration._

@Singleton
class TilesEngine @Inject()(kafkaApi: KafkaApi) {


  kafkaApi.source[CountPerTile]("alsjdh")
    .groupedWithin(200, 1.minute)

  //.filter{
  // count=>
  //because of the properties of the queadkeys
  // prefixes.exists(count.tile.startsWith)
  //}
}


class LastCountsActor extends Actor {
  val queue = RottingList[CountPerTile](_.timestamp < Platform.currentTime)

  override def receive = {
    case Push(data) =>
      queue ++= data
    case Get =>
      sender() ! queue.toList
  }
}

object LastCountsActor {

  case class Push(data: Seq[CountPerTile])

  case object Get

  def props = Props(new LastCountsActor())
}