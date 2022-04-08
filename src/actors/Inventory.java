package actors;

import items.Item;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        items.add(item);
    }
    public void removeItem(Item item) {
        items.remove(item);
    }

    public String toString() {
        String str = "";

        for (int i = 0; i < items.size(); i++) {
            str += (items.toString() + "\n");
        }

        return str;
    }
}
