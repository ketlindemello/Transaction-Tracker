package ProjectNew;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class FinancialEntity {

    double balance = 0.0;

    public FinancialEntity() {
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    ///Returns balance after transaction
    protected double AddAmount(double amount) {
        balance += amount;
        return balance;
    }
}
