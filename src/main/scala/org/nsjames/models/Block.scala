package org.nsjames.models

import play.api.libs.json.Json

case class Block(previous:String,
                 timestamp:String,
                 transaction_merkle_root:String,
                 producer:String,
                 producer_changes:List[String],
                 producer_signature:String,
                 cycles:List[String],
                 id:String,
                 block_num:Long,
                 ref_block_prefix:Long)
object Block {
  implicit val format = Json.format[Block]
}
