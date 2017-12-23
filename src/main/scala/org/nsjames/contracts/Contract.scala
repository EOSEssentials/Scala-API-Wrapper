package org.nsjames.contracts

import org.nsjames.models.Abi
import play.api.libs.json.{JsValue, Json}

case class Type(new_type_name:String, `type`:String)
object Type { implicit val format = Json.format[Type] }

case class Struct(name:String, base:Option[String], fields:JsValue)
object Struct { implicit val format = Json.format[Struct] }

case class Action(action_name:String, `type`:String)
object Action { implicit val format = Json.format[Action] }

case class Table(table_name:String, `type`:String, index_type:String, key_names:List[String], key_types:List[String])
object Table { implicit val format = Json.format[Table] }

case class Contract(account_name:String, code_hash:String, wast:String, abi: Abi)
object Contract { implicit val format = Json.format[Contract] }





