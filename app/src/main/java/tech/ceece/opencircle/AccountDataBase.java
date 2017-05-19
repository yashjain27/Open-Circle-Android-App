package tech.ceece.opencircle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A DataBase class for all Accounts. This class will contain all the Accounts in a database using  a hashmap.
 * Username is the key for the hashmap.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class AccountDataBase extends HashMap<String, Account> {
    //Data fields
    private HashMap<String, Account> bannedUsers = new HashMap<>(); //HashMap containing banned Accounts with phone numbers as key
    private ArrayList<String> phoneNumberDataBase = new ArrayList<>(); //ArrayList containing all the phone number's in the
                                                                       //database. Used to avoid multiple accounts with same number

    //Methods

    /**
     * Adds an account to the AcountDataBase
     * @param account
     *      Account to be added to the AccountDataBase
     * @throws UserNameAlreadyExistsException
     *      Indicates that a Username already exists
     */
    public void addAccount(Account account) throws UserNameAlreadyExistsException{
        if(this.get(account.getUserName()) != null)
            throw new UserNameAlreadyExistsException("Username already taken! Please enter a different username! ");
        this.put(account.getUserName(), account);
    }

    /**
     * Removes an account from the AccountDataBase
     * @param username
     *      a String representing the Username to be removed from the AccountDataBase
     * @param password
     *      a String representing the password of the Account
     */
    public void removeAccount(String username, String password) throws UserNameDoesNotExistException{
        if(this.get(username) == null)
            throw new UserNameDoesNotExistException("Incorrect username. Please try again.");
        if(this.get(username).getPassword().getPassword().equals(password))
            this.remove(username);
    }

    /**
     * Bans a User from the AccountDataBase.
     * @param account
     *      an Account to be banned from the network
     * @throws UserNameDoesNotExistException
     *      indicates that the Username entered doesn't exist in the database
     */
    //Create a numberPicker to choose from all the users to ban. with a search bar.
    public void banUser(Account account) throws UserNameDoesNotExistException{
        if(this.get(account.getUserName()) == null)
            throw new UserNameDoesNotExistException("Incorrect username. Please try again");
        bannedUsers.put(account.getPhoneNumber(), account); //Add the account to the banned user's list. Provided phone number
        this.remove(account.getUserName()); //Removes account from the database
    }
}
