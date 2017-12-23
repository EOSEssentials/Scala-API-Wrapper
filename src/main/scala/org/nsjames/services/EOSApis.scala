package org.nsjames.services

import org.nsjames.models._
import play.api.libs.json.{JsValue, Json}
import org.nsjames.utils.{EOSApiException, Logger, Timestamp}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/***
  * Single access point to all rpc methods
  */
object EOSApis {
  val chain = ChainAPI
  val wallet = WalletAPI
  val accountHistory = AccountHistoryAPI

  /***
    * Send a single transaction
 *
    * @param messageBuilder - Instance of MessageBuilder
    * @return - Instance of [[org.nsjames.models.PushedTransaction]]
    */
  def sendMessage(messageBuilder: MessageBuilder): Future[PushedTransaction] = {
    chain.getLastIrreversibleBlock flatMap { lastBlock =>
      prepareTransaction(lastBlock, messageBuilder.message, messageBuilder.jsonData, messageBuilder.scope, messageBuilder.publicKey) flatMap { signed =>
        chain.pushTransaction(signed)
      }
    }
  }

  /***
    * Bulk send multiple transactions
 *
    * @param messageBuilders - A list of messageBuilders to convert into transactions
    * @return - Instance of [[org.nsjames.models.PushedTransaction]]
    */
  def sendMessages(messageBuilders: List[MessageBuilder]): Future[PushedTransactions] = {
    MessageBuilder.allMessagesAreUnique(messageBuilders) match {
      case false => throw new EOSApiException("Messages must be unique")
      case true => chain.getLastIrreversibleBlock flatMap { lastBlock =>
        Future.sequence(messageBuilders.map(messageBuilder => prepareTransaction(lastBlock, messageBuilder.message, messageBuilder.jsonData, messageBuilder.scope, messageBuilder.publicKey))) flatMap { transactions =>
          chain.pushTransactions(transactions)
        }
      }
    }
  }

  private def prepareTransaction(lastBlock: Block, message: Message, jsonData:JsValue, scope:List[String], publicKey:String):Future[Transaction] = {
    chain.abiJsonToBin(message.code, message.`type`, jsonData) flatMap { abiJsonToBin =>
      val binaryMessage = message.copy(data = Some(abiJsonToBin.binargs))
      val unsignedTrx = Transaction.create(Timestamp.getExpiration(120), scope, binaryMessage).copy(ref_block_num = lastBlock.block_num, ref_block_prefix = lastBlock.ref_block_prefix)
      wallet.signTransaction(unsignedTrx, publicKey, "")
    }
  }
}
