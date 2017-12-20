package utils

import com.typesafe.config.{Config, ConfigFactory}

trait Settings {
  val config:Config = ConfigFactory.load

  case class HttpSettings(connectTimeout:Int, readTimeout:Int)
  val httpSettings:HttpSettings = HttpSettings(
    config.getInt("httpClient.connectTimeout"),
    config.getInt("httpClient.readTimeout")
  )
}
