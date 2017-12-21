package services

import utils.Settings
import utils.Client.{post, postRaw}

import scala.concurrent.Future

object WalletAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/wallet/$r")

  def create(walletName:String): Future[Option[String]] = postRaw(route("create"), walletName)
  def open = ???
  def lock = ???
  def lockAll = ???
  def importKey = ???
  def list = ???
  def listKeys = ???
  def getPublicKeys = ???
  def setTimeout = ???
  def signTrx = ???

}
