package items;

import java.util.ArrayList;

public class ItemContainer<T> extends Item {
    private ArrayList<T> items;

    public ItemContainer() {
        items = new ArrayList<T>();
    }

    public T get(int index) {
        return items.get(index);
    }

    public void add(T item) {
        items.add(item);
    }
    public void remove(T item) {
        items.remove(item);
    }
}
