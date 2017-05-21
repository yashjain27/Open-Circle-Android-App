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
     * @param description
     * @param price
     */
    public Item(String itemName, String description, double price){
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    //Accessors
    public String getItemName(){
        return itemName;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    }

    //Mutators
    public void setItemName(String newName){
        itemName = newName;
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }

    public void setPrice(double newPrice){
        price = newPrice;
    }
}
