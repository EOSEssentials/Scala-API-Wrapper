import org.scalatest.{AsyncFlatSpec, Matchers}
import services.ChainAPI
import TestingHelpers.assertNonEmptyJsonResponseWithLogger
import play.api.libs.json.Json

class ChainApiTest extends AsyncFlatSpec with Matchers {

  "ChainAPI" should "be able to get block info" in { ChainAPI.getInfo map {x => assert(x.nonEmpty)} }
          it should "be able to get a block" in { ChainAPI.getBlock("671239") map {x => assert(x.nonEmpty)} }
          it should "be able to get an account" in { ChainAPI.getAccount("inita") map {x => assert(x.nonEmpty)} }
          it should "be able to get a code" in { ChainAPI.getCode("currency") map {x => assert(x.nonEmpty)} }
          it should "be able to get table rows" in { ChainAPI.getTableRows("inita", "currency", "account") map {x => assert(x.nonEmpty)} }
          it should "be able to get json to bin" in { ChainAPI.abiJsonToBin("currency", "transfer", Json.obj("from"->"initb", "to"->"initv", "quantity"->1000)) map {x => assert(x.nonEmpty)} }
          it should "be able to get bin to json" in { ChainAPI.abiBinToJson("currency", "transfer", "000000008093dd74000000000094dd74e803000000000000") map {x => assert(x.nonEmpty)} }
}
