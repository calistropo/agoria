package models

import play.api.libs.json.Json

case class CountPerTile(tile: String,
                        value: Long,
                        timestamp: Long)


object CountPerTile {
  implicit def reads = Json.reads[CountPerTile]

  implicit def writes = Json.writes[CountPerTile]
}