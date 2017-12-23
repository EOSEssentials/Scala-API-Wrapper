package utils
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.Dsl._
import org.asynchttpclient._
import play.api.libs.json.{JsObject, JsValue, Json}
import scala.concurrent.{Future, Promise}


object Client extends Settings {
  val EOS_OK = "{}"

  val client:AsyncHttpClient = asyncHttpClient

  def close()                               :Unit =                 client close()
  def get(url:String)                       :Future[JsValue] =      client prepareGet url
  def head(url:String)                      :Future[JsValue] =      client prepareHead url
  def delete(url:String)                    :Future[JsValue] =      client prepareDelete url
  def put(url:String, payload:JsValue)      :Future[JsValue] =      jsReq(client preparePut url setBody payload)
  def post(url:String, payload:JsValue)     :Future[JsValue] =      jsReq(client preparePost url setBody payload)
  def postRaw(url:String, payload:String)   :Future[String]  =      client preparePost url setBody payload
  def patch(url:String, payload:JsValue)    :Future[JsValue] =      jsReq(client preparePatch url setBody payload)

  private def jsReq(rq: BoundRequestBuilder):BoundRequestBuilder = rq setHeader("Content-Type", "application/json")
  implicit private def jsToString(js:JsValue):String = js.toString

  implicit private def listenableToJsonFuture(boundRequestBuilder: BoundRequestBuilder): Future[JsValue] = {
    val promise = Promise[JsValue]()
    boundRequestBuilder.execute(new AsyncCompletionHandler[Response] {
      override def onCompleted(response: Response): Response = { promise.success(response); response }
      override def onThrowable(t: Throwable) { promise.failure(t); super.onThrowable(t) }
    })
    promise.future
  }

  implicit private def listenableToStringFuture(boundRequestBuilder: BoundRequestBuilder): Future[String] = {
    val promise = Promise[String]()
    boundRequestBuilder.execute(new AsyncCompletionHandler[Response] {
      override def onCompleted(response: Response): Response = { promise.success(response); response }
      override def onThrowable(t: Throwable) { promise.failure(t); super.onThrowable(t) }
    })
    promise.future
  }

  implicit private def responseToJsonResponse(response: Response):JsValue = is2N(response) match {
    case true => Logger.debug("response: " + response); Json.parse(response.getResponseBodyAsBytes)
    case false => throw new EOSApiException(response.getResponseBody)
  }

  implicit private def responseToStringResponse(response: Response):String = is2N(response) match {
    case true => Logger.debug("response: " + response); response.getResponseBody
    case false => throw new EOSApiException(response.getResponseBody)
  }

  private def is2N(response: Response):Boolean = response.getStatusCode.toString.take(1).toInt == 2
}
