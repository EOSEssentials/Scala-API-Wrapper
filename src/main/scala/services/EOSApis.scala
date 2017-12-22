package services

import models.{Message, PushedTransaction, Transaction}
import play.api.libs.json.{JsValue, Json}
import utils.{Logger, Timestamp}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/***
  * Single access point to all rpc methods
  */
object EOSApis {
  val chain = ChainAPI
  val wallet = WalletAPI
  val accountHistory = AccountHistoryAPI

  def sendMessage(message: Message, jsonData:JsValue, scope:List[String], publicKey:String): Future[Option[PushedTransaction]] = {
    chain.getLastIrreversibleBlock flatMap {
      case None => Future(None)
      case Some(lastBlock) =>
        chain.abiJsonToBin(message.code, message.`type`, jsonData) flatMap {
          case None => Logger.debug("A"); Future(None)
          case Some(abiJsonToBin) =>
            val binaryMessage = message.copy(data = Some(abiJsonToBin.binargs))
            val unsignedTrx = Transaction.create(Timestamp.getExpiration(120), scope, binaryMessage).copy(ref_block_num = lastBlock.block_num, ref_block_prefix = lastBlock.ref_block_prefix)
            wallet.signTransaction(unsignedTrx, publicKey, "") flatMap {
              case Right(err) => Logger.debug(s"Err $err"); Future(None)
              case Left(signed) => chain.pushTransaction(signed) map {
                case Right(err) => Logger.warn("Error: "+ err); None
                case Left(pushedTransaction) => Some(pushedTransaction)
              }
            }
        }
    }
  }
}
