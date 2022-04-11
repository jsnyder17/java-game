package persist;

public class Constants {
    public static final String DATABASE_SOURCE = "jdbc:derby:C:/Users/" + System.getProperty("user.name") + "/Documents/Java_Test_DB/database.db;create=true";    // Location of the database on the client machine

    // Table creation queries
    public static final String CREATE_ENTITIES_TABLE = "";
    public static final String CREATE_WEAPONS_TABLE = "create table weapons(id int primary key, name varchar(40), description varchar(128), item_type varchar(40), attack_power int)";
    public static final String CREATE_STATUS_ITEMS_TABLE = "create table status_items(id int primary key, name varchar(40), description varchar(128), item_type varchar(40), effect_amt int)";

}
