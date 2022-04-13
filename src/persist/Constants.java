package persist;

public class Constants {
    // Database source locations
    public static final String DATABASE_SOURCE_WIN = "jdbc:derby:C:/Users/" + System.getProperty("user.name") + "/Documents/Java_Test_DB/database.db;create=true";    // Location of the database on the client machine
    public static final String DATABASE_SOURCE_LINUX = "jdbc:derby:/home/" + System.getProperty("user.name") + "/Documents/Java_Test_DB/database.db;create=true";
    public static final String DATABASE_SOURCE_WIN_REF = "jdbc:derby:C:/Users/" + System.getProperty("user.name") + "/Documents/Java_Test_DB/database.db";
    public static final String DATABASE_SOURCE_LINUX_REF = "jdbc:derby:/home/" + System.getProperty("user.name") + "/Documents/Java_Test_DB/database.db";
    // Table creation queries
    public static final String CREATE_ENTITIES_TABLE = "";
    public static final String CREATE_WEAPONS_TABLE = "create table weapons(id int primary key, name varchar(40), description varchar(128), item_type varchar(40), attack_power int)";
    public static final String CREATE_STATUS_ITEMS_TABLE = "create table status_items(id int primary key, name varchar(40), description varchar(128), item_type varchar(40), status_item_type varchar(40), effect_amt int)";

    // Table insert queries
    public static final String INSERT_INTO_WEAPONS_QUERY = "insert into weapons(id, name, description, item_type, attack_power) values(?,?,?,?,?)";
    public static final String INSERT_INTO_STATUS_ITEMS_QUERY = "insert into status_items(id, name, description, item_type, status_item_type, effect_amt) values (?,?,?,?,?,?)";
}
