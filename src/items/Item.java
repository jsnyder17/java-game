package items;

public class Item {
    protected String name;
    protected String description;
    protected ItemType itemType;
    //protected int quantity;

    public Item() {
        name = "";
        description = "";
        itemType = ItemType.ITEM;
        //quantity = 0;
    }
    public Item(String description) { this.description = description; }

    public String getName() { return name; }
    public String getDescription() {
        return description;
    }
    public ItemType getItemType() {
        return itemType;
    }
    //public int getQuantity() { return quantity ; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setItemType(ItemType itemType) { this.itemType = itemType; }

}
