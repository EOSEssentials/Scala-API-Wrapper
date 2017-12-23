package org.nsjames.utils
import java.util.{Date, TimeZone}
import java.text.SimpleDateFormat
import java.time.Instant

object Timestamp {
  def getExpiration(secondsFromNow:Int = 60):String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
    sdf.format(System.currentTimeMillis + secondsFromNow * 1000).replace(" ", "T")
  }
}