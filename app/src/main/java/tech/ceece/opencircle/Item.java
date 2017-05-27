package tech.ceece.opencircle;

/**
 * This class represents the item class.
 *
 * Created by Yash Jain on 5/20/2017.
 */

public class Item {
    //Data fields
    private String itemName;
    private String description;
    private double price;

    //Constructor
    /**
     * Constructor for an Item class
     * @param itemName
     *      Name of the item
     * @param description
     *      Description of the item
     * @param price
     *      Price of the item
     */
    public Item(String itemName, String description, double price){
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    //Accessors
    /**
     * Returns the name of the item
     * @return
     *      a String representing the name of the item
     */
    public String getItemName(){
        return itemName;
    }

    /**
     * Returns a description of the item
     * @return
     *      a String representing the description of the item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Returns the price of the item
     * @return
     *      a double representing the price of the item
     */
    public double getPrice(){
        return price;
    }

    //Mutators
    /**
     * Sets the new name of the item
     * @param newName
     *      a String representing the new name of the item
     */
    public void setItemName(String newName){
        itemName = newName;
    }

    /**
     * Sets the new description of the item
     * @param newDescription
     *      a string representing the new description of the item
     */
    public void setDescription(String newDescription){
        description = newDescription;
    }

    /**
     * Sets the new description of the price
     * @param newPrice
     *      a String representing the new price of the item.
     */
    public void setPrice(double newPrice){
        price = newPrice;
    }
}
