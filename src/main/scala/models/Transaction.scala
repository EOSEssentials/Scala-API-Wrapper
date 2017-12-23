package models

import play.api.libs.json.{JsValue, Json}

case class Transaction(ref_block_num:Long,
                       ref_block_prefix:Long,
                       expiration:String,
                       scope:List[String],
                       read_scope:Option[List[String]],
                       messages:List[JsValue],
                       signatures:List[String],
                       output:Option[List[JsValue]] = None){
  def isSigned:Boolean = ref_block_num != -1 && ref_block_prefix != -1 && signatures.nonEmpty
}
object Transaction {
  implicit val format = Json.format[Transaction]
  def create(expiration:String, scope:List[String], binaryMessage:Message, readScope:Option[List[String]] = Some(List())) =
    Transaction(-1, -1, expiration, scope, readScope, List(Json.toJson(binaryMessage)), List())
}

case class PushedTransaction(transaction_id:String, processed:Transaction)
object PushedTransaction { implicit val format = Json.format[PushedTransaction] }

//case class PushedTransactions(transaction_id:String, processed:Transaction)
//object PushedTransaction { implicit val format = Json.format[PushedTransaction] }

case class GetTransactionWrapper(transaction_id:String, transaction: Transaction, seq_num:Option[Long] = None)
object GetTransactionWrapper { implicit val format = Json.format[GetTransactionWrapper] }

case class GetTransactionsWrapper(transactions:List[GetTransactionWrapper], time_limit_exceeded_error:Option[Boolean] = None)
object GetTransactionsWrapper { implicit val format = Json.format[GetTransactionsWrapper] }
