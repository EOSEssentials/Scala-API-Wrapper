package requests

import play.api.libs.json.Json

case class GetTableRowsRequest(scope:String, code:String, table:String, json:Boolean = true, lower_bound:Long = 0, upper_bound:Long = -1, limit:Long = 10)
object GetTableRowsRequest {
  implicit val format = Json.format[GetTableRowsRequest]
}
