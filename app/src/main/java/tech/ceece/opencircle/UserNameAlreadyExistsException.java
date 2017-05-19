package tech.ceece.opencircle;

/**
 * A throwable exception class indicating that an Account already exists in the AccountDataBase.
 * Created by yashj on 5/19/2017.
 */
public class UserNameAlreadyExistsException extends Throwable {
    /**
     * Constructor for the AccountAlreadyExistsException
     * @param message
     *      Message indicating what the error is
     */
    public UserNameAlreadyExistsException(String message){
        super(message);
    }
}
