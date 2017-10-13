package engines

import akka.NotUsed
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.google.inject.{Inject, Singleton}
import engines.LastVehiclesActor.{Push, SetVehicles, VehicleById, VehiclesByTileId}
import middleware.KafkaApi
import models.VehicleEvent
import util.RottingList

import scala.compat.Platform
import scala.concurrent.Future
import scala.concurrent.duration._

@Singleton
class VehiclesEngine @Inject()(kafkaApi: KafkaApi)(implicit actorSystem: ActorSystem, materializer: ActorMaterializer) {


  private val lastVehicles = actorSystem.actorOf(LastVehiclesActor.props)
  private val control = kafkaApi.source("vehicles")
    .groupedWithin(500, 5.seconds)
    .map(SetVehicles)
    .to(Sink.actorRef[SetVehicles](lastVehicles, NotUsed))
    .run()

  def byTileId(tileId: String): Future[List[VehicleEvent]] = {
    (lastVehicles ? VehiclesByTileId(tileId)).mapTo[List[VehicleEvent]]
  }

  def byId(vehicleId: String): Future[Option[VehicleEvent]] = {
    (lastVehicles ? VehicleById(vehicleId)).mapTo[Option[VehicleEvent]]
  }


}


class LastVehiclesActor extends Actor {
  val queue = RottingList[VehicleEvent](
    _.timestamp < Platform.currentTime - 30000
  )


  override def receive = {
    case Push(newVE) =>
      queue ++= newVE
    case Get =>
      sender() ! queue.toList
  }
}

object LastVehiclesActor {

  case class Push(data: Seq[VehicleEvent])

  case object Get


  def props = Props(new LastVehiclesActor())
}