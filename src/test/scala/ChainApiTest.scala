import contracts.Contract
import models._
import services.ChainAPI
import org.scalatest.{AsyncFlatSpec, Matchers}
import play.api.libs.json.Json
import TestingHelpers.booleanToAssertion

class ChainApiTest extends AsyncFlatSpec with Matchers {

        var block_num = ""

  "ChainAPI" should "be able to get block info" in { ChainAPI.getInfo map {_.isInstanceOf[GetInfo]} }
          it should "be able to get last block" in { ChainAPI.getLastIrreversibleBlock.map(b => {block_num = b.block_num.toString; b}) map {_.isInstanceOf[Block]} }
          it should "be able to get a block" in { ChainAPI.getBlock(block_num) map {_.isInstanceOf[Block]} }
          it should "be able to get an account" in { ChainAPI.getAccount("inita") map {_.isInstanceOf[Account]} }
          it should "be able to get a code" in { ChainAPI.getCode("currency") map {_.isInstanceOf[Contract]} }
          it should "be able to get table rows" in { ChainAPI.getTableRows("inita", "currency", "account") map {_.isInstanceOf[Rows]} }
          it should "be able to get json to bin" in { ChainAPI.abiJsonToBin("currency", "transfer", Json.obj("from"->"currency", "to"->"inita", "quantity"->1)) map {_.isInstanceOf[AbiJsonToBin]} }
          it should "be able to get bin to json" in { ChainAPI.abiBinToJson("currency", "transfer", "000000008093dd74000000000094dd74e803000000000000") map {_.isInstanceOf[AbiBinToJson]} }
}
