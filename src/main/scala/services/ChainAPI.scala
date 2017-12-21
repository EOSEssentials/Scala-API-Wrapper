package services

import models._
import play.api.libs.json.{JsValue, Json}
import requests.GetTableRowsRequest
import utils.{Client, Settings}
import utils.Client.{get, post}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ChainAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/chain/$r")

  /***
    * @return - Instance of [[models.GetInfo]]
    */
  def getInfo: Future[Option[GetInfo]] =
    get(route("get_info")).map(_.map(_.as[GetInfo]))

  /***
    * @param blockNumOrId - The block number or ID
    * @return - Instance of [[models.Block]]
    */
  def getBlock(blockNumOrId:String): Future[Option[Block]] =
    post(route("get_block"), Json.obj("block_num_or_id" -> blockNumOrId)).map(_.map(_.as[Block]))

  /***
    *
    * @param accountName - The name of the account
    * @return - Instance of [[models.Account]]
    */
  def getAccount(accountName:String): Future[Option[Account]] =
    post(route("get_account"), Json.obj("account_name" -> accountName)).map(_.map(_.as[Account]))

  /***
    *
    * @param accountName - The name of the contract to get the code for
    * @return - Instance of [[models.Contract]]
    */
  def getCode(accountName:String): Future[Option[Contract]] =
    post(route("get_code"), Json.obj("account_name" -> accountName)).map(_.map(_.as[Contract]))

  /***
    *
    * @param request - [[requests.GetTableRowsRequest]]
    * @return - Instance of [[models.Rows]]
    */
  def getTableRows(request:GetTableRowsRequest):Future[Option[Rows]] =
    post(route("get_table_rows"), Json.toJson(request)).map(_.map(_.as[Rows]))

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
  def getTableRows(scope:String, code:String, table:String, lowerBound:Long = 0, upperBound:Long = -1, limit:Long = 10):Future[Option[Rows]] =
    getTableRows(GetTableRowsRequest(scope, code, table, true, lowerBound, upperBound, limit))

  /***
    * @param code - Contract name
    * @param action - Action name
    * @param args - Data to turn into binary
    * @return - Instance of [[models.AbiJsonToBin]]
    */
  def abiJsonToBin(code:String, action:String, args:JsValue):Future[Option[AbiJsonToBin]] =
    post(route("abi_json_to_bin"), Json.obj("code" -> code, "action" -> action, "args" -> args)).map(_.map(_.as[AbiJsonToBin]))

  /***
    *
    * @param code - Contract name
    * @param action - Action name
    * @param binargs - Binary data to turn into json
    * @return - Instance of [[models.AbiBinToJson]]
    */
  def abiBinToJson(code:String, action:String, binargs:String):Future[Option[AbiBinToJson]] =
    post(route("abi_bin_to_json"), Json.obj("code" -> code, "action" -> action, "binargs" -> binargs)).map(_.map(_.as[AbiBinToJson]))



//TODO----------- Require other methods from wallet --------
  def pushBlock = ???                               //TODO--
  def pushTransaction = ???                         //TODO--
  def pushTransactions = ???                        //TODO--
  def getRequiredKeys = ???                         //TODO--
//TODO------------------------------------------------------

}
