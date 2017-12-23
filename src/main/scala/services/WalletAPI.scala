package services

import models.Transaction
import play.api.libs.json.{JsValue, Json}
import utils.{Client, Settings}
import utils.Client.{get, post, postRaw}
import utils.Errors.{eitherError, futureEitherError}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object WalletAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/wallet/$r")
  implicit def eosOkToBoolean(future:Future[String]):Future[Boolean] = future.map(_==Client.EOS_OK)

  /***
    * Creates a new wallet.
    * @param name - The requested name of a new wallet
    * @return - The password to the newly created wallet
    */
  def create(name:String):Future[String] =
    postRaw(route("create"), name).map(_.replace("\"", ""))

  /***
    * Opens a wallet. Still needs to be unlocked after it's opened
    * @param name - The name of the wallet to open
    * @return - True if open
    */
  def open(name:String):Future[Boolean] =
    postRaw(route("open"), name)

  /***
    * Locks a wallet
    * @param name - The name of the wallet to lock
    * @return - True if locked
    */
  def lock(name:String):Future[Boolean] =
    postRaw(route("lock"), name)

  /***
    * Locks all open wallets
    * @return - True if locked
    */
  def lockAll:Future[Boolean] =
    get(route("lock_all")).map(_.toString==Client.EOS_OK)

  /***
    * Unlocks a wallet
    * @param name - The name of the wallet to unlock
    * @param password - The wallet's password
    * @return - True if unlocked
    */
  def unlock(name:String, password:String): Future[Boolean] =
    postRaw(route("unlock"), Json.arr(name, password).toString)

  /***
    * Imports a private key into your wallet
    * @param name - The name of the wallet to import your key into
    * @param privateKey - The private key to import
    * @return - True if imported
    */
  def importKey(name:String, privateKey:String): Future[Boolean] = {
    // I've decided to check if the key exists before to avoid the needless error message
    listKeys flatMap { keys =>
      keys.flatten.contains(privateKey) match {
        case false => postRaw(route("import_key"), Json.arr(name, privateKey).toString)
        case true => Future(true)
      }
    }
  }


  //TODO: These could really use return models

  def list: Future[List[String]] =
    get(route("list_wallets")).map(_.as[List[String]])

  def listKeys: Future[List[List[String]]] =
    get(route("list_keys")).map(_.as[List[List[String]]])

  def getPublicKeys: Future[List[String]] =
    get(route("get_public_keys")).map(_.as[List[String]])

  def setTimeout(timeout:Long): Future[String] =
    postRaw(route("set_timeout"), timeout.toString)

  def signTransaction(unsignedTransaction: Transaction, publicKey:String, chainId:String):Future[Transaction] = {
    if(unsignedTransaction.isSigned) Future(unsignedTransaction)
    else {
      val json = Json.arr(Json.toJson(unsignedTransaction), Json.arr(publicKey), chainId)
      post(route("sign_transaction"), json).map(_.as[Transaction])
    }
  }


}
