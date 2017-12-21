package services

import play.api.libs.json.{JsValue, Json}
import utils.Settings
import utils.Client.{get, post}

import scala.concurrent.Future

object AccountHistoryAPI extends Settings {
  private def route(r:String):String = eosSettings.uri(s"/account_history/$r")

  //TODO Result
  /*
  "results": {
      "transaction_id": "fixed_bytes32",
      "transaction": "Transaction"
    }
   */
  def getTransaction(transactionId:String): Future[Option[JsValue]] =
    post(route("get_transaction"), Json.obj("transaction_id" -> transactionId))

  //TODO Result
  /*
  "results": {
      "transactions": "vector<ordered_transaction_results>",
      "time_limit_exceeded_error": "optional<bool>"
    }
   */
  def getTransactions(accountName:String, skipSeq:Option[Int] = None, numSeq:Option[Int] = None): Future[Option[JsValue]] =
    post(route("get_transactions"), Json.obj("account_name" -> accountName, "skip_seq" -> skipSeq, "num_seq" -> numSeq))

  //TODO Result
  /*
  "results": {
      "account_names": "vector[name]"
    }
   */
  def getKeyAccounts(publicKey:String) =
    post(route("get_key_accounts"), Json.toJson("public_key" -> publicKey))

  //TODO Result
  /*
  "results": {
      "controlled_accounts": "vector[name]"
    }
   */
  def getControlledAccounts(accountName:String) =
    post(route("get_controlled_accounts"), Json.obj("controlling_account" -> accountName))

}
