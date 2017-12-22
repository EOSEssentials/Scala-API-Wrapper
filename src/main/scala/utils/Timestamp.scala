package utils
import java.util.{Date, TimeZone}
import java.text.SimpleDateFormat
import java.time.Instant

object Timestamp {
  def getExpiration(secondsFromNow:Int = 60):String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))

    val expiresIn = secondsFromNow * 1000
    sdf.format(System.currentTimeMillis + expiresIn).replace(" ", "T")
  }
}

/*
EOS_ASSERT(now <= trx.expiration, transaction_exception, "Transaction is expired",
              ("now",now)("trx.exp",trx.expiration));
 */
