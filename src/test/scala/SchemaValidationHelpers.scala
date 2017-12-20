import play.api.libs.json.{JsObject, JsValue}
import utils.StringUtils.underToCamel
import utils.{Client, Logger}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object SchemaValidationHelpers {
  def validate(chainMethods:List[String], classPath:String): Boolean = {
    val classFinder = Class.forName(classPath)
    chainMethods.forall(methodName => {
      try { classFinder.getMethod(methodName); true }
      catch { case (e: Exception) => Logger.warn(s"Method `${e.getMessage}` not found in $classPath.scala"); false }
    })
  }

  def getSchemaJson(uri:String):Future[Option[List[String]]] = Client.get(uri) map {
    case None => Logger.warn(s"Could not get ${uri.split("/").last} schema from EOS"); None
    case Some(chainJs) => Some(chainJs.as[JsObject].value.toList.map(x => underToCamel(x._1)))
  }
}
