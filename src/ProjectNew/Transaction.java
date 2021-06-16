package ProjectNew;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class Transaction {

    private String id;
    private double amount;

    public Transaction(String id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    Transaction(String transactionId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }
}
