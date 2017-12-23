package org.nsjames.models

import play.api.libs.json.Json
case class GetInfo(server_version:String,
                   head_block_num:Long,
                   last_irreversible_block_num:Long,
                   head_block_id:String,
                   head_block_time:String,
                   head_block_producer:String,
                   recent_slots:String,
                   participation_rate:String)
object GetInfo { implicit val format = Json.format[GetInfo] }
