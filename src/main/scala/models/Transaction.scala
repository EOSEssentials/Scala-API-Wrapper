package models

import play.api.libs.json.{JsValue, Json}

case class Transaction(ref_block_num:Long,
                       ref_block_prefix:Long,
                       expiration:String,
                       scope:List[String],
                       signatures:List[String],
                       messages:List[JsValue],
                       output:List[JsValue])
object Transaction { implicit val format = Json.format[Transaction] }

case class GetTransactionWrapper(transaction_id:String, transaction: Transaction)
object GetTransactionWrapper { implicit val format = Json.format[GetTransactionWrapper] }

case class GetTransactionsWrapper(transactions:List[Transaction], time_limit_exceeded_error:Option[Boolean] = None)
object GetTransactionsWrapper { implicit val format = Json.format[GetTransactionsWrapper] }
