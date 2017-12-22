package requests

import play.api.libs.json.{JsValue, Json}

case class GetTableRowsRequest(scope:String, code:String, table:String, json:Boolean = true, lower_bound:Long = 0, upper_bound:Long = -1, limit:Long = 10)
object GetTableRowsRequest { implicit val format = Json.format[GetTableRowsRequest] }

case class AbiJsonToBinRequest(code:String, action:String, args:JsValue)
object AbiJsonToBinRequest { implicit val format = Json.format[AbiJsonToBinRequest] }

case class AbiBinToJsonRequest(code:String, action:String, binargs:String)
object AbiBinToJsonRequest { implicit val format = Json.format[AbiBinToJsonRequest] }


