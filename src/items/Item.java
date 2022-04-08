package items;

public class Item {
    protected String name;
    protected String description;
    protected ItemType itemType;

    public Item() {
        name = "";
        description = "";
        itemType = ItemType.ITEM;
    }
    public Item(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public ItemType getItemType() {
        return itemType;
    }
}
