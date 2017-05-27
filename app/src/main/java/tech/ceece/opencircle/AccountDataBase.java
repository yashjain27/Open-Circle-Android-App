package tech.ceece.opencircle;
import java.io.Serializable;
import java.util.HashMap;

/**
 * A DataBase class for all Accounts. This class will contain all the Accounts in a database using  a hashmap.
 * Username is the key for the hashmap.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class AccountDataBase extends HashMap<String, Account> implements Serializable {
    //Data fields
//    private AccountDataBase accountDataBase = new AccountDataBase();
    private HashMap<String, String> bannedUsers = new HashMap<>(); //HashMap containing banned Accounts with usernames as key and value
    private HashMap<String, String> phoneNumberDataBase = new HashMap<>(); //ArrayList containing all the phone number's in the
                                                                       //database. Used to avoid multiple accounts with same number

    //Cosntructor

    /**
     * Empty private constructor.
     */
    public AccountDataBase(){}

    //Methods

    /**
     * Adds an account to the AcountDataBase
     * @param account
     *      Account to be added to the AccountDataBase
     * @throws UserNameAlreadyExistsException
     *      Indicates that a Username already exists
     */
    public void addAccount(Account account) throws UserNameAlreadyExistsException, AccountAlreadyExistsException{
        if(phoneNumberDataBase.get(account.getPhoneNumber()) != null)
            throw new AccountAlreadyExistsException("An account already exists with the associated phone number.");
        if(this.get(account.getUserName()) != null)
            throw new UserNameAlreadyExistsException("Username already taken! Please enter a different username! ");
        this.put(account.getUserName(), account);
        phoneNumberDataBase.put(account.getPhoneNumber(), account.getPhoneNumber()); //Key-value both phone #'s, used to store to the database
    }

    public Account getAccount(String userName) throws UserNameDoesNotExistException{
        if(this.get(userName) == null)
            throw new UserNameDoesNotExistException();
        return this.get(userName);
    }

    /**
     * Removes an account from the AccountDataBase
     * @param username
     *      a String representing the Username to be removed from the AccountDataBase
     * @param password
     *      a String representing the password of the Account
     */
    public void deleteAccount(String username, String password) throws UserNameDoesNotExistException{
        if(this.get(username) == null)
            throw new UserNameDoesNotExistException();
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
            throw new UserNameDoesNotExistException();
        account.setBanned(true);
        bannedUsers.put(account.getUserName(), account.getPhoneNumber());
    }

    /**
     * Unbans a user from the AccountDataBase
     * @param userName
     *      a String representing the username.
     * @throws UserNameDoesNotExistException
     *      indicates that the Username entered doesn't exist in the database
     */
    public void unBanUser(String userName) throws UserNameDoesNotExistException{
        if(bannedUsers.get(userName) == null)
            throw new UserNameDoesNotExistException();
        bannedUsers.remove(userName); //Remove the banned userUsers from the list of banned users
        this.get(userName).setBanned(false); //Unban the user
    }

    /**
     * To string method that returns all the accounts full names
     * @return
     *      a String representing the names of all accounts
     */
    @Override
    public String toString(){
        String print = "";

        for(String key : this.keySet()){
            try {
                print += this.getAccount(key).getFullName() + "\n";
            } catch (UserNameDoesNotExistException e) {
            }
        }

        return print;
    }

}
