package tech.ceece.opencircle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Account class. Class that handles the creation of new Accounts by people.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class Account implements Serializable {
    //Data fields
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Password password;
    private ArrayList<Item> items = new ArrayList<>();
    private boolean banned;
    private boolean admin;

    //Constructor

    /**
     * Empty constructor
     */
    public Account(){}

    /**
     * Constructor for the Account class.
     * @param userName
     *      a String representing the userName
     * @param fullName
     *      a String representing the user's full name
     * @param email
     *      a String representing the user's email
     * @param phoneNumber
     *      an int indicating the user's phone number
     * @param password
     *      a Password object containing the password of the Account
     */
    public Account(String userName, String fullName, String email, String phoneNumber, Password password){
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        banned = false;
        admin = false;
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
    public String getFullName() {
        return fullName;
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

    /**
     * Returns the boolean if whether the user is banned or not.
     * @return
     *      true indicates the Account is banned, and a false indicates that the Account is not
     */
    public boolean getBanned(){
        return banned;
    }

    /**
     * Returns the boolean if whether an account is an admin or not
     * @return
     *      true indicates that Account is an admin, a false indicates a regular user
     */
    public boolean getAdmin(){
       return admin;
    }

    /**
     * Returns the ArrayList of the items the account is selling.
     * @return
     *      an ArrayList<Items> of items the account is selling
     */
    public ArrayList<Item> getItems(){
        return items;
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
     * @param fullName
     *      a String representing the first name associated with this account
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    /**
     * Sets the boolean value of whether the Account is banned or not.
     * @param bool
     *      a true indicates the Account should be banned, a false indicates to unban.
     */
    public void setBanned(Boolean bool){
        this.banned = bool;
    }

    /**
     * Sets the account's privilege if whether the account is an admin or not
     * @param bool
     *      a true indicates that the account should be an admin, a false indicates that the
     *      account loses it's admin privileges
     *
     */
    public void setAdmin(boolean bool){
        this.admin = bool;
    }

    /**
     * Removes an item from the list of items to sell
     * @param index
     *      Index of the items to be removed from the list
     */
    public void removeItem(int index){
        try {
            items.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bounds");
        }
    }

    /**
     * Item to be added to the list
     * @param item
     *      the item to be added to the list
     */
    public void addItem(Item item){
        items.add(item);
    }

//    public void setImageView(int id){
//        imageView.setImageResource(id);
//    }
}
