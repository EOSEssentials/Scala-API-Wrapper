import org.nsjames.utils.Client
import org.scalatest.{Assertion, AsyncFlatSpec, Matchers}
import play.api.libs.json.{JsValue, Json}
import TestingHelpers.booleanToAssertion

import scala.concurrent.Future


class ClientTest extends AsyncFlatSpec with Matchers {

  private def uri(route:String):String = s"https://jsonplaceholder.typicode.com$route"
  val blank:JsValue = Json.obj()

  "Client" should "be able to do GET org.nsjames.requests" in { Client.get(uri("/posts/1")) map {_.isInstanceOf[JsValue]} }
        it should "be able to do POST org.nsjames.requests" in { Client.post(uri("/posts/"), blank) map {_.isInstanceOf[JsValue]} }
        it should "be able to do PUT org.nsjames.requests" in { Client.put(uri("/posts/1"), blank) map {_.isInstanceOf[JsValue]} }
        it should "be able to do PATCH org.nsjames.requests" in { Client.patch(uri("/posts/1"), blank) map {_.isInstanceOf[JsValue]} }
        it should "be able to do DELETE org.nsjames.requests" in { Client.delete(uri("/posts/1")) map {_.isInstanceOf[JsValue]} }
        it should "be able to test a real EOS route" in { Client.get("http://testnet1.eos.io/v1/chain/get_info") map {_.isInstanceOf[JsValue]} }
        it should "be able to pull routes from EOS github" in { Client.get(s"https://raw.githubusercontent.com/EOSIO/eosjs-json/master/api/v1/chain.json") map {_.isInstanceOf[JsValue]} }
}
