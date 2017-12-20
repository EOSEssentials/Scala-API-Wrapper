import org.scalatest.{AsyncFlatSpec, Matchers}

class ValidateChainSchemas extends AsyncFlatSpec with Matchers {

  val jsonSchemaURI = "https://raw.githubusercontent.com/EOSIO/eosjs-json/master/api/v1/chain.json"
  var methods:List[String] = List()

  "Client" should "be able to fetch methods schema" in {
    TestingHelpers.getSchemaJson(jsonSchemaURI) map {
      case None =>            assert(1==2)
      case Some(results) =>   methods = results
                              assert(methods.nonEmpty)
    }
  }

  "ChainAPI" should "have all required methods" in {
    assert(TestingHelpers.validateMethods(methods, "services.ChainAPI"))
  }

}
