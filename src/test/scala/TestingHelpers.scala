import org.nsjames.utils.StringUtils.underToCamel
import org.nsjames.utils.{Client, Logger}
import org.scalatest.{Assertion, Matchers}
import play.api.libs.json.{JsObject, JsValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TestingHelpers extends Matchers {
  def validateMethods(validationMethods:List[String], classPath:String): Boolean = {
    val classFinder = Class.forName(classPath)
    val methods:List[String] = classFinder.getMethods.map(_.getName).toList
    validationMethods.forall(methods.contains)
  }

  def getSchemaJson(uri:String):Future[List[String]] = Client.get(uri) map { chainJs =>
    chainJs.as[JsObject].value.toList.map(x => underToCamel(x._1))
  }

  implicit def assertNonEmptyJsonResponse(json:Option[JsValue]):Assertion = assert(json.nonEmpty)
  implicit def assertNonEmptyJsonResponseWithLogger(json:Option[JsValue]):Assertion = { Logger.debug(s"$json"); assert(json.nonEmpty) }
  implicit def assertNonEmptyStringResponse(str:Option[String]):Assertion = assert(str.nonEmpty)
  implicit def assertNonEmptyStringResponseWithLogger(str:Option[String]):Assertion = { Logger.debug(s"$str"); assert(str.nonEmpty) }
  implicit def booleanToAssertion(bool:Boolean):Assertion = assert(bool)
}
