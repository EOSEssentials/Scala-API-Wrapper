package services

/***
  * Single access point to all rpc methods
  */
object EOSRpc {
  val chain = ChainAPI
  val wallet = WalletAPI
}
