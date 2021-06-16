package ProjectNew;

import java.util.HashMap;
import java.util.Set;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class Account {

    String accountNumber = "";
    double balance = 0;
    HashMap<String, Card> cards = new HashMap<>();

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public boolean CardExists(String cardNumber) {
        return cards.containsKey(cardNumber);
    }

    public Card getCard(String cardNumber) {
        return cards.get(cardNumber);
    }

    public Set<String> GetCardNumbers() {
        return cards.keySet();
    }

    public HashMap<String, Transaction> getCardTransactions(String cardNumber) {
        Card card = cards.get(cardNumber);
        return card.getTransactionHistory();
    }

    public void TryAddCard(String cardNumber) {
        if (!cards.containsKey(cardNumber)) {
            Card card = new Card(cardNumber);
            cards.put(cardNumber, card);
        }
    }

    public void addTransaction(String cardNumber, String transactionId, double amount) throws Exception {
        if (!CardExists(cardNumber)) {
            throw new Exception("Card does not exist!");
        }

        cards.get(cardNumber).addTransaction(transactionId, amount);
        balance += amount;
    }

    void compareTo(Account value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
