package models

import play.api.libs.json.Json

case class VehicleEvent(
                         id: String,
                         runId: Option[String],
                         latitude: Double,
                         longitude: Double,
                         heading: Double,
                         //This doesn't make sense
                         //seconds_since_report: Int,
                         predictable: Boolean,
                         routeId: String,
                         timestamp: Long,
                         quadKey: String
                       )

object VehicleEvent {
  implicit def reads = Json.reads[VehicleEvent]

  implicit def writes = Json.writes[VehicleEvent]
}
