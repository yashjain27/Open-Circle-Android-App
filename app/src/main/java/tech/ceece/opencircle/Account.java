package tech.ceece.opencircle;

/**
 * The Account class. Class that handles the creation of new Accounts by people.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class Account {
    //Data fields
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Password password;

    //Constructor

    /**
     * Constructor for the Account class.
     * @param userName
     *      a String representing the userName
     * @param firstName
     *      a String representing the user's first name
     * @param lastName
     *      a String representing the user's last name
     * @param email
     *      a String representing the user's email
     * @param phoneNumber
     *      an int indicating the user's phone number
     * @param password
     *      a Password object containing the password of the Account
     */
    public Account(String userName, String firstName, String lastName, String email, String phoneNumber, Password password){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    //Accessors

    /**
     * Returns the userName associated with this account
     * @return
     *      a String representation of the userName associated with the Account
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the first name associated with this account
     * @return
     *      a String representation of first name associated with the Account
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name associated with this account
     * @return
     *      a String representation of last name associated with the Account
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the email associated with this account
     * @return
     *      a String representation of the email associated with the Account
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the phone number associated with this account
     * @return
     *      an int indicating the phone number associated with the Account
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the Password object associated with this account
     * @return
     *      a Password object that contains the password of the Account
     */
    public Password getPassword() {
        return password;
    }

    //Mutators

    /**
     * Sets the new user name associated with the Account
     * @param userName
     *      a String representing the userName associated with this account
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets the new first name associated with the Account
     * @param firstName
     *      a String representing the first name associated with this account
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the new last name associated with the Account
     * @param lastName
     *      a String representing the last name associated with this account
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the new email associated with the Account
     * @param email
     *       a String representing the email associated with this account
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the new phone number associated with the Account
     * @param phoneNumber
     *      a String representing the phone number associated with this account
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the new password associated with the Account
     * @param password
     *      a Password object associated with
     */
    public void setPassword(Password password) {
        this.password = password;
    }
}
