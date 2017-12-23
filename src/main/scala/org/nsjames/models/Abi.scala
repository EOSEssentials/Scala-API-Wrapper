package org.nsjames.models

import org.nsjames.contracts.{Action, Struct, Table, Type}
import play.api.libs.json.{JsValue, Json}

case class Abi(types:Option[List[Type]] = None,
               structs:Option[List[Struct]] = None,
               actions:Option[List[Action]] = None,
               tables:Option[List[Table]] = None)
object Abi { implicit val format = Json.format[Abi] }

case class AbiJsonToBin(binargs:String, required_scope:List[String], required_auth:List[String])
object AbiJsonToBin { implicit val format = Json.format[AbiJsonToBin] }

case class AbiBinToJson(args:JsValue, required_scope:List[String], required_auth:List[String])
object AbiBinToJson { implicit val format = Json.format[AbiBinToJson] }
