import org.scalatest.{Assertion, AsyncFlatSpec, FlatSpec, Matchers}
import play.api.libs.json.{JsValue, Json}
import utils.{Client, Logger}
import TestingHelpers.assertNonEmptyJsonResponse

import scala.concurrent.Future


class ClientTest extends AsyncFlatSpec with Matchers {

  private def uri(route:String):String = s"https://jsonplaceholder.typicode.com$route"
  implicit def requestToAssertion(future:Future[Option[JsValue]]):Future[Assertion] = future map { response => response }
  val blank:JsValue = Json.obj()

  "Client" should "be able to do GET requests" in { Client.get(uri("/posts/1"))}
        it should "be able to do POST requests" in { Client.post(uri("/posts/"), blank)}
        it should "be able to do PUT requests" in { Client.put(uri("/posts/1"), blank)}
        it should "be able to do PATCH requests" in { Client.patch(uri("/posts/1"), blank)}
        it should "be able to do DELETE requests" in { Client.delete(uri("/posts/1"))}
        it should "be able to test a real EOS route" in { Client.get("http://testnet1.eos.io/v1/chain/get_info")}
        it should "be able to pull routes from EOS github" in { Client.get(s"https://raw.githubusercontent.com/EOSIO/eosjs-json/master/api/v1/chain.json")}
}
