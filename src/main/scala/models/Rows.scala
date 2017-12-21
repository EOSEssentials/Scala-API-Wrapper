package models

import play.api.libs.json.{JsValue, Json}

case class Rows(rows:List[JsValue], more:Boolean)
object Rows {
  implicit val format = Json.format[Rows]
}
