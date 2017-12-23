package org.nsjames.contracts

import org.nsjames.models.{Message, MessageAuthorization}
import play.api.libs.json.Json

object CurrencyContract {
  case class Transfer(from:String, to:String, quantity:Long) {
    def message:Message = Message("currency", "transfer", List(MessageAuthorization(from, "active")))
    def scopes:List[String] = List(from,to)
  }
  object Transfer { implicit val format = Json.format[Transfer] }

}

