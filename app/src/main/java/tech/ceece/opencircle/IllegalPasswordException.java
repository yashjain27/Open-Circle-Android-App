package tech.ceece.opencircle;

/**
 * Illegal Password exception. An exception thrown when an invalid password is attempted.
 *
 * Created by Yash Jain on 5/16/2017.
 */
public class IllegalPasswordException extends Exception{
    /**
     * Constructor for the IllegalPasswordException.
     * @param message
     *      A message indicating what the error is
     */
    public IllegalPasswordException(String message){
        super(message);
    }
}
