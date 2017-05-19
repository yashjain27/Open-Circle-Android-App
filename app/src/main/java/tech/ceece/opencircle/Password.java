package tech.ceece.opencircle;

/**
 * Password class. Manages passwords for people.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class Password {
    //Data fields
    private String password;

    //Constructor

    /**
     * Constructor for the Password class.
     * <dd><b>Preconditions:</b></dd>
     * <db>Password must be 6 characters long, one lower case, one upper case, one digit, and one
     * special character (~!@#$%^&*()_+)</db>
     * @param initPassword
     *        Password String
     * @throws IllegalPasswordException
     *      Thrown when a password rule is violated.
     */
    public Password(String initPassword) throws IllegalPasswordException{
        boolean upperCase = false, lowerCase = false, number = false, specialChar = false;

        //Check if the password is greater than 6 characters
        if (initPassword.length() < 6) throw new IllegalPasswordException("Password must be greater than 6 characters.");

        for(int i = 0; i < initPassword.length(); i++){
            if (initPassword.charAt(i) > 64 && initPassword.charAt(i) < 91)
                upperCase = true;
            if (initPassword.charAt(i) > 96 && initPassword.charAt(i) < 123)
                lowerCase = true;
            if (initPassword.charAt(i) > 47 && initPassword.charAt(i) < 58)
                number = true;
            if (initPassword.charAt(i) == 33 || initPassword.charAt(i) == 64 || initPassword.charAt(i) == 35
                    || initPassword.charAt(i) == 36 || initPassword.charAt(i) == 37 || initPassword.charAt(i) == 94
                    || initPassword.charAt(i) == 38 || initPassword.charAt(i) == 42 || initPassword.charAt(i) == 126
                    || initPassword.charAt(i) == 40 || initPassword.charAt(i) == 41 || initPassword.charAt(i) == 95
                    || initPassword.charAt(i) == 43)
                specialChar = true;
        }
        if(!upperCase || !number || !specialChar || !lowerCase)
            throw new IllegalArgumentException();
        else
            password = initPassword;
    }

    //Accessors

    /**
     * Returns the password string
     * @return
     *      a String representing the password
     */
    public String getPassword(){
        return password;
    }

    //Mutator

    /**
     * Sets the new Password for the object
     * @param newPassword
     *      a String representing the new password to be set
     */
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
}
