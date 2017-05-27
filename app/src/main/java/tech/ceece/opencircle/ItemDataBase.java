package tech.ceece.opencircle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A database for all the items ready to sell
 *
 * Created by Yash Jain on 5/20/2017.
 */

public class ItemDataBase {
    //Data fields
    private static HashMap<String, ArrayList<Item>> itemDataBase = new HashMap<>();

    //Consructor

    /**
     * Private empty constructor. Static class, only one ItemDataBase can exist
     */
    private ItemDataBase() {}

    //Accessor

    /**
     * Returns the database for the items.
     * @return
     *      a HashMap<String, ArrayList<Item>> representing the database consisting of all items
     */
    public static HashMap getItemDataBase(){
        return  itemDataBase;
    }
}
