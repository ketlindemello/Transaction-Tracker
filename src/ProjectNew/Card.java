package ProjectNew;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class Card extends FinancialEntity {

    String cardNumber = "";

    protected HashMap<String, Transaction> transactionHistory = new HashMap<>();

    public Card(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    //+ money spent
    //- money paid to account
    // in case there is a negative number
    public void addTransaction(String transactionId, double transactionAmount) throws Exception {
        if (transactionId == null || transactionId == "") {
            throw new Exception("TransactionId cannot be null in 'addTransaction'!");
        }
        Transaction transaction = new Transaction(transactionId, transactionAmount);
        transactionHistory.put(transactionId, transaction);
        this.AddAmount(transactionAmount);

    }

    public HashMap<String, Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public Transaction getTransactionById(String transactionId) {
        return transactionHistory.get(transactionId);
    }

    private boolean verifyTransactionBalance() {
        boolean result = false;
        double transactionBalance = 0.0;
        Set<String> keySet = transactionHistory.keySet();
        Iterator it = keySet.iterator();
        while (it.hasNext()) {
            Transaction t = transactionHistory.get(it.next().toString());
            transactionBalance += t.getAmount();
        }

        if (transactionBalance == this.getBalance()) {
            result = true;
        }

        return result;
    }

}
