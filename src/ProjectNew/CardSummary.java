package ProjectNew;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class CardSummary {

    public CardSummary(String accountNumber, String cardNumber, double cardBalance) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
    }

    public String accountNumber = "";
    public String cardNumber = "";
    public double cardBalance = 0;

    public double getBalance() {
        return cardBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
