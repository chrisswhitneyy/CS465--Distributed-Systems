package appserver.data;

import appserver.server.TransServer;
import appserver.lock.LockType;
import java.util.ArrayList;

/** Class [DataManager] : Class that is in charge of handling the accounts.
 *
 * Author: Christopher D. Whitney on May 1st, 2017
 */
public class DataManager implements LockType {

    static private ArrayList<Account> accounts;

    /**
     * ClassConstructor
     */
    public DataManager() {
        accounts = new ArrayList<>();

        // initialize 10 accounts to $10 and add to account list
        for (int i = 0; i<10; i++) {
            Account tempAccount = new Account();
            tempAccount.setAmount(10);
            tempAccount.setId(i);
            accounts.add(tempAccount);
        }
        System.out.println("[DataManager].constructor()  accounts initialized with $10.");

    }

    /**
     * read - reads the given account
     *
     * @param accountID - ID of the account to read
     * @param TID - ID of the transaction that wishes to read the account
     * @return balance - amount on the account
     */
    public int read(int accountID,int TID){
        TransServer.lockManager.setLock(accounts.get(accountID),TID,READ_LOCK);
        Account account = accounts.get(accountID);
        int balance = account.getAmount();
        System.out.println("[DataManager].read()  TID " + TID + " readied account " + account.getId());
        return balance;
    }

    /**
     * write - writes a given amount to a given account
     *
     * @param accountID - ID of the account to read
     * @param TID - ID of the transaction that wishes to read the account
     * @param amount - Amount to write to the account
     * @return balance - returns new balance on account
     *
     */
    public int write(int accountID,int TID, int amount){
        TransServer.lockManager.setLock(accounts.get(accountID),TID,WRITE_LOCK);
        Account account = accounts.get(accountID);
        account.setAmount(amount);
        System.out.println("[DataManager].write()  TID " + TID + " wrote $" + amount + " to " + account.getId());
        return account.getAmount();
    }

    /**
     * getAllAccountTotal - sums all of the accounts balances
     * @return total - sum of all the accounts
     */
    public int getAllAccountTotal(){
        int total = 0;
        for (int i=0; i<accounts.size(); i++){
            total += accounts.get(i).getAmount();
        }
        return total;
    }

}
