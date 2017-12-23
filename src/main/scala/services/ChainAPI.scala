package services

import contracts.Contract
import models._
import play.api.libs.json.{JsValue, Json}
import requests.{AbiBinToJsonRequest, AbiJsonToBinRequest, GetTableRowsRequest}
import utils.{Client, EOSApiException, Logger, Settings}
import utils.Client.{get, post}
import utils.Errors.{eitherError, futureEitherError}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChainAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/chain/$r")

  /***
    *
    * @return - Instance of [[models.GetInfo]]
    */
  def getInfo: Future[GetInfo] =
    get(route("get_info")).map(_.as[GetInfo])

  def getLastIrreversibleBlock:Future[Block] = {
    getInfo flatMap { info =>
      getBlock(info.last_irreversible_block_num.toString)
    }
  }

  /***
    *
    * @param blockNumOrId - The block number or ID
    * @return - Instance of [[models.Block]]
    */
  def getBlock(blockNumOrId:String): Future[Block] =
    post(route("get_block"), Json.obj("block_num_or_id" -> blockNumOrId)).map(_.as[Block])

  /***
    *
    * @param accountName - The name of the account
    * @return - Instance of [[models.Account]]
    */
  def getAccount(accountName:String): Future[Account] =
    post(route("get_account"), Json.obj("account_name" -> accountName)).map(_.as[Account])

  /***
    *
    * @param accountName - The name of the contract to get the code for
    * @return - Instance of [[Contract]]
    */
  def getCode(accountName:String): Future[Contract] =
    post(route("get_code"), Json.obj("account_name" -> accountName)).map(_.as[Contract])

  /***
    *
    * @param scope - The owner scope
    * @param code - The contract name
    * @param table - The table name
    * @param lowerBound
    * @param upperBound
    * @param limit
    * @return - Instance of [[models.Rows]]
    */
  def getTableRows(scope:String, code:String, table:String, lowerBound:Long = 0, upperBound:Long = -1, limit:Long = 10):Future[Rows] =
    getTableRows(GetTableRowsRequest(scope, code, table, true, lowerBound, upperBound, limit))

  def getTableRows(request:GetTableRowsRequest):Future[Rows] =
    post(route("get_table_rows"), Json.toJson(request)).map(_.as[Rows])

  /***
    *
    * @param code - Contract name
    * @param action - Action name
    * @param args - Data to turn into binary
    * @return - Instance of [[models.AbiJsonToBin]]
    */
  def abiJsonToBin(code:String, action:String, args:JsValue):Future[AbiJsonToBin] =
    abiJsonToBin(AbiJsonToBinRequest(code, action, args))

  def abiJsonToBin(request:AbiJsonToBinRequest):Future[AbiJsonToBin] =
    post(route("abi_json_to_bin"), Json.toJson(request)).map(_.as[AbiJsonToBin])

  /***
    *
    * @param code - Contract name
    * @param action - Action name
    * @param binargs - Binary data to turn into json
    * @return - Instance of [[models.AbiBinToJson]]
    */
  def abiBinToJson(code:String, action:String, binargs:String):Future[AbiBinToJson] =
    abiBinToJson(AbiBinToJsonRequest(code, action, binargs))

  def abiBinToJson(request:AbiBinToJsonRequest):Future[AbiBinToJson] =
    post(route("abi_bin_to_json"), Json.toJson(request)).map(_.as[AbiBinToJson])

  def getRequiredKeys(unsignedTransaction:Transaction): Future[List[String]] = {
    val data = Json.obj("transaction" -> Json.toJson(unsignedTransaction), "signatures"->Json.arr(), "available_keys"->Json.arr())
    post(route("get_required_keys"), data).map(x => (x \ "required_keys").as[List[String]])
  }

  def pushTransaction(signedTransaction:Transaction):Future[PushedTransaction] = {
    signedTransaction.isSigned match {
      case false => throw new EOSApiException("Cannot push unsigned transaction")
      case true => post(route("push_transaction"), Json.toJson(signedTransaction)) map { pushed =>
        pushed.as[PushedTransaction]
      }
    }
  }

  def pushTransactions(signedTransactions:List[Transaction]):Future[List[PushedTransaction]] = {
    signedTransactions.forall(_.isSigned) match {
      case false => throw new EOSApiException("One or more transactions is unsigned")
      case true => post(route("push_transactions"), Json.toJson(signedTransactions)) map { pushed =>
        pushed.as[List[PushedTransaction]]
      }
    }
  }

  //TODO: Whoops, forgot these
  def pushBlock = ???
}
