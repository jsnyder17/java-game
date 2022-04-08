package actors;

import interfaces.EntityInterface;
import items.Item;
import java.util.*;

public class Entity implements EntityInterface {
    private String name;
    private ArrayList<Item> inventory;
    private int hp;
    private int defense;
    private int strength;
    private int dexterity;
    private int endurance;
    private int intelligence;
    private int arcane;

    public Entity() {
        name = "";
        inventory = new ArrayList<Item>();
        hp =  defense = strength = dexterity = endurance = intelligence = arcane = 0;
    }

    public Entity(String data) {
        this();

        // Break up the two parts of the data. <attribute_data&inventory_data>
        StringTokenizer stMain = new StringTokenizer(data, "&");

        // Assign string tokenizer for data
        StringTokenizer st = new StringTokenizer(stMain.nextToken(), "|");

        name = st.nextToken();
        hp = Integer.parseInt(st.nextToken());
        defense = Integer.parseInt(st.nextToken());
        strength = Integer.parseInt(st.nextToken());
        dexterity = Integer.parseInt(st.nextToken());
        endurance = Integer.parseInt(st.nextToken());
        intelligence = Integer.parseInt(st.nextToken());
        arcane = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(stMain.nextToken(), "|");

        // Add items to inventory
        for (int i = 0; i < st.countTokens(); i++) {
            inventory.add(new Item(st.nextToken()));
        }
    }

    public void damage(int amt) {
        hp -= amt;
    }
}