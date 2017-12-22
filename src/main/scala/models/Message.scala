package models

import play.api.libs.json.Json

case class MessageAuthorization(account:String, permission:String)
object MessageAuthorization { implicit val format = Json.format[MessageAuthorization] }

case class Message(code:String, `type`:String, authorization:List[MessageAuthorization], data:Option[String] = None)
object Message { implicit val format = Json.format[Message] }
