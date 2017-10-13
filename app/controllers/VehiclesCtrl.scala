package controllers

import com.google.inject.{Inject, Singleton}
import engines.VehiclesEngine
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.Future


@Singleton
class VehiclesCtrl @Inject()(
                              vehiclesEngine: VehiclesEngine,
                              cc: ControllerComponents) extends AbstractController(cc) {

  def vehicleCount(tileIds: String) = Action.async {
    req =>
      ???
  }

  def byTileId(tileId: String) = Action.async{
    req=>
      vehiclesEngine.byTileId(tileId)
          .map{
            case Nil=> NoContent
            case vehicles=> Ok(Json.toJson(vehicles))
          }
  }

  def byId(vehicleId: String) =  Action.async{
    req=>
      vehiclesEngine.byId(vehicleId)
        .flatMap{
          case None => Future.failed(new Exception("Fix this"))
          case Some(vehicle)=> Ok(Json.toJson(vehicle))
        }
  }

  def list = play.mvc.Results.TODO
}
