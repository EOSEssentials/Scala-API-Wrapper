import utils.StringUtils.underToCamel
import org.scalatest.{AsyncFlatSpec, Matchers}

class ValidateWalletSchemas extends AsyncFlatSpec with Matchers {
  //TODO: There currently is no wallet.json schema available, for now using dummy
  var methods:List[String] = List(
    "wallet_create",
    "wallet_open",
    "wallet_lock",
    "wallet_lock_all",
    "wallet_unlock",
    "wallet_import_key",
    "wallet_list",
    "wallet_list_keys",
    "wallet_get_public_keys",
    "wallet_set_timeout",
    "wallet_sign_trx"
  )
    .map(_.replace("sign_trx", "sign_transaction")) // Supporting non-abbreviations
    .map(_.replace("wallet_", "")) // Removing the `wallet` before the methods because it is redundant.
    .map(x => underToCamel(x))

  "WalletAPI" should "have all required methods" in {
    assert(TestingHelpers.validateMethods(methods, "services.WalletAPI"))
  }
}
