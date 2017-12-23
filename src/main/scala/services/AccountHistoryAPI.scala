package services

import models.{GetTransactionWrapper, GetTransactionsWrapper}
import play.api.libs.json.{JsValue, Json}
import utils.{Logger, Settings}
import utils.Client.{get, post}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AccountHistoryAPI extends Settings {
  private def route(r:String):String = eosSettings.uri(s"/account_history/$r")

  /***
    *
    * @param transactionId - The transaction_id to get
    * @return - Instance of [[models.GetTransactionWrapper]]
    */
  def getTransaction(transactionId:String): Future[GetTransactionWrapper] =
    post(route("get_transaction"), Json.obj("transaction_id" -> transactionId)).map(_.as[GetTransactionWrapper])

  /***
    *
    * @param accountName - The name of the account to get transactions for
    * @param skipSeq - [OPTIONAL]
    * @param numSeq - [OPTIONAL]
    * @return - Instance of [[models.GetTransactionsWrapper]]
    */
  def getTransactions(accountName:String, skipSeq:Option[Int] = None, numSeq:Option[Int] = None):Future[GetTransactionsWrapper] =
    post(route("get_transactions"), Json.obj("account_name" -> accountName, "skip_seq" -> skipSeq, "num_seq" -> numSeq))
      .map(_.as[GetTransactionsWrapper])

  /***
    * @param publicKey - The public get to get accounts for
    * @return - A list of account names
    */
  def getKeyAccounts(publicKey:String): Future[List[String]] =
    post(route("get_key_accounts"), Json.obj("public_key" -> publicKey))
      .map(x => (x \ "account_names").as[List[String]])

  /***
    * @param accountName - The account name to get controlled accounts for
    * @return - A list of account names
    */
  def getControlledAccounts(accountName:String): Future[List[String]] =
    post(route("get_controlled_accounts"), Json.obj("controlling_account" -> accountName))
      .map(x => (x \ "controlled_accounts").as[List[String]])

}
