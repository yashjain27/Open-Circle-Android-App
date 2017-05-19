package tech.ceece.opencircle;

/**
 * An exception class indicating that a Username does not exist in the database.
 *
 * Created by Yash Jain on 5/19/2017.
 */

public class UserNameDoesNotExistException extends Throwable{
    //Constructor

    /**
     * Constructor for the UserNameDoesNotExistException
     * @param message
     *      Message indicating what the exception is
     */
    public UserNameDoesNotExistException(String message){
        super(message);
    }
}
