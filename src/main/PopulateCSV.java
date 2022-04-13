package main;

import items.Weapon;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class PopulateCSV {
    public static void populateWeapons() {
        String fileName = "resources/weapons.csv";

        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            StringBuilder sb = new StringBuilder();

            sb.append("Iron sword|A basic sword forged with iron.|WEAPON|10\n");
            sb.append("Wooden bow|A basic bow made from wood.|WEAPON|10\n");
            sb.append("Iron greatsword|A large sword forged with iron.|WEAPON|15\n");
            sb.append("Wooden greatbow|A large bow made from wood.|WEAPON|15\n");

            fw.write(sb.toString());
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void populateStatusItems() {
        String fileName = "resources/statusitems.csv";

        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            StringBuilder sb = new StringBuilder();

            sb.append("Basic healing potion|A potion that restores a small amount of HP.|STATUS_ITEM|HP|10\n");

            fw.write(sb.toString());
            fw.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        populateWeapons();
        populateStatusItems();
    }
}
