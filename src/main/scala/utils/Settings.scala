package utils

import scala.collection.JavaConverters._
import com.typesafe.config.{Config, ConfigFactory}

trait Settings {
  val config:Config = ConfigFactory.load

  // HTTP
  case class HttpSettings(connectTimeout:Int, readTimeout:Int)
  val httpSettings:HttpSettings = HttpSettings(
    config.getInt("httpClient.connectTimeout"),
    config.getInt("httpClient.readTimeout")
  )

  // EOS
  case class EosSettings(nodes:List[String]){
    def node:String = nodes.head
    def uri(route:String):String = node + "/" + (if(route.head=='/') route.drop(1) else route)
  }
  val eosSettings:EosSettings = EosSettings(
    config.getStringList("eos.nodes").asScala.toList.map(x => if(x.last=='/') x dropRight 1 else x)
  )
}
