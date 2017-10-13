package controllers


import com.google.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}


@Singleton
class TilesCtrl @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def vehicleCount(tileIds: String) = play.mvc.Results.TODO

  def filled = play.mvc.Results.TODO
}
