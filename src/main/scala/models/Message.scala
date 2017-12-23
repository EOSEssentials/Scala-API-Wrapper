package models

import play.api.libs.json.{JsValue, Json}

case class MessageAuthorization(account:String, permission:String)
object MessageAuthorization { implicit val format = Json.format[MessageAuthorization] }

/***
  * @param code - The contract name
  * @param `type` - The action
  * @param authorization - Instance of [[models.MessageAuthorization]]
  * @param data - FILLED FROM RESPONSE, DO NOT FILL
  */
case class Message(code:String, `type`:String, authorization:List[MessageAuthorization], data:Option[String] = None)
object Message { implicit val format = Json.format[Message] }

/***
  * @param message - The message to send
  * @param jsonData - The json data associated with the transaction/message
  * @param scope - The accounts associated with the transaction/message
  * @param publicKey - The public key used for signing the transaction
  */
case class MessageBuilder(message: Message, jsonData:JsValue, scope:List[String], publicKey:String)
object MessageBuilder { implicit val format = Json.format[MessageBuilder] }
