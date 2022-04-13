package persist;

import items.*;
import java.util.*;
import java.io.*;

public class InitialData {
    public static List<Weapon> getWeapons() throws IOException {
        List<Weapon> weapons = new ArrayList<Weapon>();
        ReadCSV readWeapons = new ReadCSV("weapons.csv");

        try {
            Integer id = 1;

            while (true) {
                List<String> tuple = readWeapons.next();
                if (tuple == null) {
                    break;
                }

                Iterator<String> i = tuple.iterator();
                Weapon weapon = new Weapon();

                weapon.setName(i.next());
                weapon.setDescription(i.next());
                weapon.setItemType(ItemType.valueOf(i.next()));
                weapon.setAttackPower(Integer.parseInt(i.next()));

                System.out.println(weapon.toString());

                weapons.add(weapon);
            }

            return weapons;
        }
        finally {
            readWeapons.close();
        }
    }

    public static List<StatusItem> getStatusItems() throws IOException {
        List<StatusItem> statusItems = new ArrayList<StatusItem>();
        ReadCSV readStatusItems = new ReadCSV("statusitems.csv");

        try {
            Integer id = 1;

            while (true) {
                List<String> tuple = readStatusItems.next();
                if (tuple == null) {
                    break;
                }

                Iterator<String> i = tuple.iterator();
                StatusItem statusItem = new StatusItem();

                statusItem.setName(i.next());
                statusItem.setDescription(i.next());
                statusItem.setItemType(ItemType.valueOf(i.next()));
                statusItem.setStatusItemType(StatusItemType.valueOf(i.next()));
                statusItem.setEffectAmt(Integer.parseInt(i.next()));

                statusItems.add(statusItem);
            }

            return statusItems;
        }
        finally {
            readStatusItems.close();
        }
    }
}
