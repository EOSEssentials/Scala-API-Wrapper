package utils
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.Dsl._
import org.asynchttpclient._
import play.api.libs.json.{JsObject, JsValue, Json}
import scala.concurrent.{Future, Promise}


object Client extends Settings {
  case class JsonResponse(status:Int, body:Option[JsValue])
  val client:AsyncHttpClient = asyncHttpClient

  def close()                               :Unit =                         client close()
  def get(url:String)                       :Future[Option[JsValue]] =      client prepareGet url
  def head(url:String)                      :Future[Option[JsValue]] =      client prepareHead url
  def delete(url:String)                    :Future[Option[JsValue]] =      client prepareDelete url
  def put(url:String, payload:JsValue)      :Future[Option[JsValue]] =      jsReq(client preparePut url setBody payload)
  def post(url:String, payload:JsValue)     :Future[Option[JsValue]] =      jsReq(client preparePost url setBody payload)
  def postRaw(url:String, payload:String)   :Future[Option[String]]  =      client preparePost url setBody payload
  def patch(url:String, payload:JsValue)    :Future[Option[JsValue]] =      jsReq(client preparePatch url setBody payload)

  private def jsReq(rq: BoundRequestBuilder):BoundRequestBuilder = rq setHeader("Content-Type", "application/json")
  implicit private def jsToString(js:JsValue):String = js.toString
  implicit private def listenableToJsonFuture(boundRequestBuilder: BoundRequestBuilder): Future[Option[JsValue]] = {
    val promise = Promise[Option[JsValue]]()
    boundRequestBuilder.execute(new AsyncCompletionHandler[Response] {
      override def onCompleted(response: Response): Response = { promise.success(response.body); response }
      override def onThrowable(t: Throwable) { promise.failure(t); super.onThrowable(t) }
    })
    promise.future
  }
  implicit private def listenableToStringFuture(boundRequestBuilder: BoundRequestBuilder): Future[Option[String]] = {
    val promise = Promise[Option[String]]()
    boundRequestBuilder.execute(new AsyncCompletionHandler[Response] {
      override def onCompleted(response: Response): Response = { promise.success(response); response }
      override def onThrowable(t: Throwable) { promise.failure(t); super.onThrowable(t) }
    })
    promise.future
  }
  implicit private def responseToJsonResponse(response: Response):JsonResponse = {
    def tryParse:JsValue = try { Json.parse(response.getResponseBodyAsBytes) }
                           catch { case(e:Exception) => e.printStackTrace(); Json.obj() }

    val code:Int = response.getStatusCode
    val body:Option[JsValue] = List(tryParse).find(_ => code.toString.take(1).toInt == 2)
    JsonResponse(code, body)
  }
  implicit private def responseToStringResponse(response: Response):Option[String] = {
    response.getStatusCode.toString.take(1).toInt == 2 match {
      case true => Some(response.getResponseBody)
      case false => None
    }
  }
}
