import org.scalatest.{Assertion, AsyncFlatSpec, Matchers}
import play.api.libs.json.JsValue
import services.ChainAPI
import utils.Logger
import TestingHelpers.assertNonEmptyJsonResponseWithLogger

class ChainApiTest extends AsyncFlatSpec with Matchers {

  "ChainAPI" should "be able to get block info" in { ChainAPI.getInfo map {x => x} }
          it should "be able to get a block" in { ChainAPI.getBlock("671239") map {x => x} }
          it should "be able to get an account" in { ChainAPI.getAccount("inita") map {x => x} }
          it should "be able to get a code" in { ChainAPI.getCode("currency") map {x => x} }

}
