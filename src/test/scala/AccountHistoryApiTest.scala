import org.scalatest.{AsyncFlatSpec, Matchers}
import services.AccountHistoryAPI
import TestingHelpers.assertNonEmptyJsonResponseWithLogger
import models.{GetTransactionWrapper, GetTransactionsWrapper}

class AccountHistoryApiTest extends AsyncFlatSpec with Matchers {

  val fakePublicKey = "EOS8VJuNAvCq1EPgvCeUYXxiEfsuLmMRwdDSp526dMA6yNVQF8PLn"

  "AccountHistoryAPI" should
                "be able to get a list of transactions" in { AccountHistoryAPI.getTransactions("inita") map {x => assert(x.isInstanceOf[Option[GetTransactionsWrapper]])} }
      it should "be able to get a transaction" in { AccountHistoryAPI.getTransaction("") map {x => assert(x.isInstanceOf[Option[GetTransactionWrapper]])} }
      it should "be able to get a list of account names" in { AccountHistoryAPI.getKeyAccounts(fakePublicKey) map {x => assert(x.isInstanceOf[Option[List[String]]])} }
      it should "be able to get a list of controlled account" in { AccountHistoryAPI.getControlledAccounts("inita") map {x => assert(x.isInstanceOf[Option[List[String]]])} }
}
