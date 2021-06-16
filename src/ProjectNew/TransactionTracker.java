package ProjectNew;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Final Project CITC 1318
 *
 * @author Ketlin Mello
 */
public class TransactionTracker {

    static HashMap<String, Account> accounts = new HashMap<String, Account>();
    static HashMap<String, String> cardAccountLookup = new HashMap<String, String>();

    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

        populateFromFile();
        CardSummary[] highestCardSummaries = findTopSummariesForCards();
        outputCardsToFile(highestCardSummaries);
        String[] highestAccountNumbers = findTopAccountNumbers();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        // <editor-fold defaultstate="collapsed" desc="Local Test Output">
        //Hidden for no reason of being displayed here
        //boolean debug = true;
        //Top all card transaction amounts across all accounts having the highest card balance
//        if (debug) {
//            //display card summaries
//            for (int i = 0; i < highestCardSummaries.length; i++) {
//                //System.out.print("Account Number: " + highestCardSummaries[i].accountNumber + "\t");
//                //System.out.print("Card Number: " + highestCardSummaries[i].cardNumber + "\t");
//                //System.out.print("Card Balance: " + highestCardSummaries[i].cardBalance);
//                //System.out.println();
//            }
//        }
        //get all account transactions sorted by greatest sum on the account for the highest 25 account sum totals     
//
//        if (debug) {
//            System.out.println();
//            for (int i = 0; i < highestAccountNumbers.length; i++) {
////                System.out.print("Account Number: " + accounts.get(highestAccountNumbers[i]).getAccountNumber() + "\t");
////                System.out.print("Account Balance: " + accounts.get(highestAccountNumbers[i]).getBalance() + "\t");
////                System.out.println();
//            }
//        }
        //show cards for each of the highest accounts
//        if (debug) {
//            //System.out.println();
//            for (int i = 0; i < highestAccountNumbers.length; i++) {
//                //System.out.print("Account Number: " + accounts.get(highestAccountNumbers[i]).getAccountNumber() + "\t");
//                //System.out.print("Account Balance: " + accounts.get(highestAccountNumbers[i]).getBalance() + "\t");
//                //System.out.println();
//                Account account = accounts.get(highestAccountNumbers[i]);
//                Object[] cardNumbers = account.GetCardNumbers().toArray();
//                for (int j = 0; j < cardNumbers.length; j++) {
//                    //System.out.print("\t Card Number: " + cardNumbers[j].toString());
//                    //System.out.println();
//                }
//            }
//        }
        // </editor-fold>
        outputAccountsToFile(highestAccountNumbers);

        //given a debit card number, find the sum total for all cards that are connected to one account
        System.out.println("To consult an account total balance provide a card number.");
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String userCardNumber = scanner.nextLine();

        String accountNumber = cardAccountLookup.get(userCardNumber);
        System.out.println("Balance for account " + accounts.get(accountNumber).accountNumber + " associated with card " + userCardNumber + ", is: " + formatter.format(accounts.get(accountNumber).balance));
    }

    private static CardSummary[] findTopSummariesForCards() {
        CardSummary[] result = new CardSummary[25];

        Set<String> accountKeySet = accounts.keySet();
        Iterator accountIterator = accountKeySet.iterator();
        while (accountIterator.hasNext()) {
            Account evalAccount = accounts.get(accountIterator.next().toString());
            Set<String> cardNumbers = evalAccount.GetCardNumbers();

            //compare to all values in array
            Iterator cardIterator = cardNumbers.iterator();
            while (cardIterator.hasNext()) {
                Card evalCard = evalAccount.getCard(cardIterator.next().toString());
                result = placeAndSortCardSummary(result, evalAccount.getAccountNumber(), evalCard);
            }
        }

        return result;
    }

    private static CardSummary[] placeAndSortCardSummary(CardSummary[] cardSummary, String accountNumber, Card card) {
        //find out where to put the card in the array
        for (int i = 0; i < cardSummary.length; i++) {
            if (cardSummary[i] != null && card.getBalance() >= cardSummary[i].getBalance()) {
                //place and pull the other ones down
                for (int j = cardSummary.length - 1; j > i; j--) {
                    cardSummary[j] = cardSummary[j - 1];
                }
                cardSummary[i] = new CardSummary(accountNumber, card.getCardNumber(), card.getBalance());
                break;
            } else if (cardSummary[i] == null) {
                cardSummary[i] = new CardSummary(accountNumber, card.getCardNumber(), card.getBalance());
                break;
            }
        }

        return cardSummary;
    }

    private static String[] findTopAccountNumbers() {
        String[] result = new String[25];

        Set<String> accountKeySet = accounts.keySet();
        Iterator accountIterator = accountKeySet.iterator();
        while (accountIterator.hasNext()) {
            Account evalAccount = accounts.get(accountIterator.next().toString());
            result = placeAndSortAccounts(result, evalAccount);
        }

        return result;
    }

    private static String[] placeAndSortAccounts(String[] accountNumbers, Account account) {

        for (int i = 0; i < accountNumbers.length; i++) {
            if ((accountNumbers[i] != null && !accountNumbers[i].equals(""))
                    && (accounts.get(accountNumbers[i]).getBalance() <= account.getBalance())) {
                for (int j = accountNumbers.length - 1; j > i; j--) {
                    accountNumbers[j] = accountNumbers[j - 1];
                }
                accountNumbers[i] = account.getAccountNumber();
                break;
            } else if (accountNumbers[i] == null || accountNumbers[i].equals("")) {
                accountNumbers[i] = account.getAccountNumber();
                break;
            }
        }

        return accountNumbers;
    }

    private static void outputCardsToFile(CardSummary[] cardSummaries) throws IOException {
        String cardOutputFile = "C:\\Users\\ketli\\Documents\\Fall 2020\\CITC 1318 - Data Structures\\TransactionTracker\\cardoutput.txt";
        File file = new File(cardOutputFile);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);

        for (int i = 0; i < cardSummaries.length; i++) {
            Account account = accounts.get(cardSummaries[i].getAccountNumber());
            HashMap<String, Transaction> transactions = account.getCardTransactions(cardSummaries[i].getCardNumber());
            Set<String> transactionKeySet = transactions.keySet();
            Object[] transactionKeys = transactionKeySet.toArray();

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            for (int j = 0; j < transactionKeys.length; j++) {
                fileWriter.write("Account Number: " + cardSummaries[i].accountNumber + "\t");
                fileWriter.write("Card Number: " + cardSummaries[i].cardNumber + "\t");
                fileWriter.write("Card Balance: " + formatter.format(cardSummaries[i].getBalance()) + "\t");
                fileWriter.write("Transaction ID: " + transactions.get(transactionKeys[j]).getId() + "\t");
                fileWriter.write("Transaction Amount: " + formatter.format(transactions.get(transactionKeys[j]).getAmount()));
                fileWriter.write("\r\n");
            }
        }

        fileWriter.close();
    }

    private static void outputAccountsToFile(String[] accountKeys) throws IOException {
        String accountOutputFile = "C:\\Users\\ketli\\Documents\\Fall 2020\\CITC 1318 - Data Structures\\TransactionTracker\\accountsOutput.txt";

        File file = new File(accountOutputFile);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        for (int i = 0; i < accountKeys.length; i++) {
            Account account = accounts.get(accountKeys[i]);
            Object[] cardNumbers = account.GetCardNumbers().toArray();
            for (int j = 0; j < cardNumbers.length; j++) {
                HashMap<String, Transaction> transactions = account.getCardTransactions(cardNumbers[j].toString());
                Object[] transactionKeys = transactions.keySet().toArray();
                for (int k = 0; k < transactionKeys.length; k++) {
                    fileWriter.write("Account Number: " + accountKeys[i] + "\t");
                    fileWriter.write("Account Balance: " + formatter.format(account.getBalance()) + "\t");
                    fileWriter.write("Card Number: " + cardNumbers[j] + "\t");
                    fileWriter.write("Transaction ID: " + transactions.get(transactionKeys[k]).getId() + "\t");
                    fileWriter.write("Transaction Amount: " + formatter.format(transactions.get(transactionKeys[k]).getAmount()));
                    fileWriter.write("\r\n");
                }
            }
        }
    }

    private static void populateFromFile() throws FileNotFoundException, Exception {
        File transactionsInfo = new File("C:\\Users\\ketli\\Documents\\Fall 2020\\CITC 1318 - Data Structures\\TransactionTracker\\Input1K.txt");
        String accountOutputFile = "account.txt";

        String transactionOutputFile = "transaction.txt";
        int cardCounter = 0;

        File accountOuput = new File("C:\\Users\\ketli\\Documents\\Fall 2020\\CITC 1318 - Data Structures\\TransactionTracker\\");
        File cardOutput = new File("C:\\Users\\ketli\\Documents\\Fall 2020\\CITC 1318 - Data Structures\\TransactionTracker\\");

        FileReader fileReader = new FileReader(transactionsInfo);

        boolean skipFileHeader = true;

        String line = "";
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while (null != (line = bufferedReader.readLine())) {
                if (skipFileHeader) {
                    skipFileHeader = false;
                    continue;
                }

                String[] temporaryBuffer;
                temporaryBuffer = line.split("\\;");

                String accountNumber = temporaryBuffer[0];
                String cardNumber = temporaryBuffer[1];
                String transactionId = temporaryBuffer[2];
                double transactionAmount = Double.parseDouble(temporaryBuffer[3].replaceAll("[$,]", ""));

                if (!accounts.containsKey(accountNumber)) {
                    try {
                        Account account = new Account(accountNumber);
                        account.TryAddCard(cardNumber);
                        account.addTransaction(cardNumber, transactionId, transactionAmount);

                        accounts.put(accountNumber, account);

                        cardAccountLookup.put(cardNumber, accountNumber);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                } else {
                    Account account = accounts.get(accountNumber);
                    if (account.CardExists(cardNumber)) {
                        try {
                            account.addTransaction(cardNumber, transactionId, transactionAmount);
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    } else {
                        //add the card to the account
                        account.TryAddCard(cardNumber);
                        cardAccountLookup.put(cardNumber, accountNumber);

                        //add the transaction to the card
                        account.addTransaction(cardNumber, transactionId, transactionAmount);
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
