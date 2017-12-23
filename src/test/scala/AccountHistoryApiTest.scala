import org.nsjames.models.{GetTransactionWrapper, GetTransactionsWrapper}
import org.nsjames.services.AccountHistoryAPI
import org.scalatest.{AsyncFlatSpec, Matchers}
import TestingHelpers.booleanToAssertion

class AccountHistoryApiTest extends AsyncFlatSpec with Matchers {

  val fakePublicKey = "EOS6AUHhmx7WUcM3YhJh7uzfCfAQgyqZh3LYHTLbf2hxNtwoHBpXQ"

  var transactions:GetTransactionsWrapper = _

  "AccountHistoryAPI" should
                "be able to get a list of transactions" in { AccountHistoryAPI.getTransactions("inita").map(x => {transactions = x; x}) map {_.isInstanceOf[GetTransactionsWrapper]} }
      it should "be able to get a transaction" in { AccountHistoryAPI.getTransaction(transactions.transactions.head.transaction_id) map {_.isInstanceOf[GetTransactionWrapper]} }
      it should "be able to get a list of account names" in { AccountHistoryAPI.getKeyAccounts(fakePublicKey) map {_.isInstanceOf[List[String]]} }
      it should "be able to get a list of controlled accounts" in { AccountHistoryAPI.getControlledAccounts("inita") map {_.isInstanceOf[List[String]]} }
}
