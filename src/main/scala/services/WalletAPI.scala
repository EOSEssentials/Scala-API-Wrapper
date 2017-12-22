package services

import models.Transaction
import play.api.libs.json.{JsValue, Json}
import utils.{Logger, Settings}
import utils.Client.{get, post, postRaw}
import utils.Errors.{eitherError, futureEitherError}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object WalletAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/wallet/$r")

  def create(name:String):Future[Option[String]] =
    postRaw(route("create"), name)

  def open(name:String):Future[Option[String]] =
    postRaw(route("open"), name)

  def lock(name:String):Future[Option[String]] =
    postRaw(route("lock"), name)

  def lockAll:Future[Option[JsValue]] =
    get(route("lock_all"))

  def unlock(name:String, password:String): Future[Option[String]] =
    postRaw(route("unlock"), Json.arr(name, password).toString)

  def importKey(name:String, privateKey:String): Future[Option[String]] = {
    // I've decided to check if the key exists before to avoid the error message or previously added keys
    listKeys flatMap {
      case None => Future(None)
      case Some(keysJson) =>
        keysJson.as[List[List[String]]].flatten.contains(privateKey) match {
          case false => postRaw(route("import_key"), Json.arr(name, privateKey).toString)
          case true => Future(Some("{}")) // Returning true since key already exists
        }
    }
  }


  def list: Future[Option[JsValue]] =
    get(route("list_wallets"))

  def listKeys: Future[Option[JsValue]] =
    get(route("list_keys"))

  def getPublicKeys: Future[Option[JsValue]] =
    get(route("get_public_keys"))

  def setTimeout(timeout:Long): Future[Option[String]] =
    postRaw(route("set_timeout"), timeout.toString)

  def signTransaction(unsignedTransaction: Transaction, publicKey:String, chainId:String):Future[Either[Transaction, String]] = {
    if(unsignedTransaction.isSigned) Future(Right("Transaction is already signed"))
    else {
      val json = Json.arr(Json.toJson(unsignedTransaction), Json.arr(publicKey), chainId)
      Logger.warn("json: "+ json)
      post(route("sign_transaction"), json) map {
        case None => "Couldn't sign transaction"
        case Some(signed) => Left(signed.as[Transaction])
      }
    }
  }


}
