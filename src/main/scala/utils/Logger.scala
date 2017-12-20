package utils
import java.util.Calendar

object Logger {
  object Colors {
    val RESET = "\u001B[0m"
    val RED = "\u001B[31m"
    val GREEN = "\u001B[36m"
    val YELLOW = "\u001B[33m"
  }

  def iterate(list:List[AnyRef]):Unit = { var i = 0; list.foreach(x => { log(s"$x", Colors.GREEN, s"ITERATION ($i)"); i+=1 }) }
  def print(message:String):Unit = log(message)
  def info(message:String):Unit = log(message, Colors.YELLOW, "INFO")
  def debug(message:String):Unit = log(message, Colors.GREEN, "DEBUG")
  def warn(message:String):Unit = log(message, Colors.RED, "WARN")
  def line() = System.out.print(s"${Colors.YELLOW}------------------------------------------------------------------${Colors.RESET} \n")
  def colorText(message:String):String = s"${Colors.YELLOW}$message${Colors.RESET}"
  def block(messages:List[String]) = {
    Logger.line()
    messages.foreach(message => Logger.info(message))
    Logger.line()
  }

  private def log(message:String, color:String = "", level:String = "") = System.out.print(s"${prefixer(level, color)} $message \n")
  def prefixer(level:String, color:String):String = if(level.isEmpty) s"[$getTimeString]" else s"$color[$level $getTimeString]${Colors.RESET}"
  def getTimeString:String = { val now = Calendar.getInstance(); s"${fillInZeroes(now.get(Calendar.HOUR))}:${fillInZeroes(now.get(Calendar.MINUTE))}:${fillInZeroes(now.get(Calendar.SECOND))}" }
  def fillInZeroes(partialTime:Long):String = if(partialTime.toString.length == 1) s"0${partialTime.toString}" else partialTime.toString
}
