package services

import utils.Settings

object WalletAPI extends Settings {

  private def route(r:String):String = eosSettings.uri(s"/wallet/$r")

  def walletCreate = ???
  def walletOpen = ???
  def walletLock = ???
  def walletLockAll = ???
  def walletImportKey = ???
  def walletList = ???
  def walletListKeys = ???
  def walletGetPublicKeys = ???
  def walletSetTimeout = ???
  def walletSignTrx = ???

}
