package persist;

import items.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DerbyDatabase implements IDatabase {
    static {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (Exception e) {
            throw new IllegalStateException("Could not load Derby driver");
        }
    }

    public interface Transaction<ResultType> {
        public ResultType execute(Connection conn) throws SQLException;
    }

    private static final int MAX_ATTEMPTS = 10;
    public String databaseSource = "";
    public String databaseSourceRef = "";

    public static void main(String[] args) {
        DerbyDatabase db = new DerbyDatabase();

        db.getDatabaseSource();

        db.dropTables();

        /*
        if (db.checkDatabase()) {
            System.out.println("Previous database found. Removing ... ");
            db.dropTables();
        }
        */


        System.out.println("Creating database ... ");

        db.createTables();
        db.loadInitialData();

        System.out.println("Finished creating database");

        db.printDatabase();
    }

    public void getDatabaseSource() {
        if (System.getProperty("os.name").contains("Linux")) {
            databaseSource = Constants.DATABASE_SOURCE_LINUX;
            databaseSourceRef = Constants.DATABASE_SOURCE_LINUX_REF;
        }
        else if (System.getProperty("os.name").contains("Windows")) {
            databaseSource = Constants.DATABASE_SOURCE_WIN;
            databaseSourceRef = Constants.DATABASE_SOURCE_WIN_REF;
        }
    }

    public void printDatabase() {
        ArrayList<Weapon> weapons = getWeapons();
        ArrayList<StatusItem> statusItems = getStatusItems();

        for (Weapon weapon : weapons) {
            System.out.println(weapon.toString());
        }
        for (StatusItem statusItem : statusItems) {
            System.out.println(statusItem.toString());
        }
    }

    public void dropTables() {
        executeTransaction(new Transaction<Boolean>() {
            @SuppressWarnings("resource")
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                ArrayList<String> tables = new ArrayList<String>();

                tables.add("drop table weapons");
                tables.add("drop table status_items");

                try {
                    for (int i = 0; i < tables.size(); i++) {
                        stmt = conn.prepareStatement(tables.get(i));
                        stmt.executeUpdate();
                    }
                }
                finally {
                    DBUtil.closeQuietly(stmt);
                }

                return true;
            }
        });
    }

    public boolean checkDatabase() {
        File file = new File(databaseSourceRef);

        return file.exists();
    }

    public void createTables() {
        executeTransaction(new Transaction<Boolean>() {
            @SuppressWarnings("resource")
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    stmt = conn.prepareStatement(Constants.CREATE_STATUS_ITEMS_TABLE);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement(Constants.CREATE_WEAPONS_TABLE);
                    stmt.executeUpdate();
                }
                finally {
                    DBUtil.closeQuietly(stmt);
                }

                return true;
            }
        });
    }

    public void loadInitialData() {
        executeTransaction(new Transaction<Boolean>() {
            @SuppressWarnings("resource")
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    List<Weapon> weapons = InitialData.getWeapons();
                    List<StatusItem> statusItems = InitialData.getStatusItems();

                    try {
                        int id = 1;

                        // Weapons
                        stmt = conn.prepareStatement(Constants.INSERT_INTO_WEAPONS_QUERY);

                        for (Weapon weapon : weapons) {
                            stmt.setInt(1, id);
                            stmt.setString(2, weapon.getName());
                            stmt.setString(3, weapon.getDescription());
                            stmt.setString(4, weapon.getItemType().toString());
                            stmt.setInt(5, weapon.getAttackPower());
                            stmt.addBatch();
                            id++;
                        }

                        stmt.executeBatch();

                        // Status items
                        stmt = conn.prepareStatement(Constants.INSERT_INTO_STATUS_ITEMS_QUERY);
                        id = 1;

                        for (StatusItem statusItem : statusItems) {
                            stmt.setInt(1, id);
                            stmt.setString(2, statusItem.getName());
                            stmt.setString(3, statusItem.getDescription());
                            stmt.setString(4, statusItem.getItemType().toString());
                            stmt.setInt(5, statusItem.getEffectAmt());
                            stmt.addBatch();
                            id++;
                        }

                        stmt.executeBatch();
                    }
                    finally {
                        DBUtil.closeQuietly(stmt);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
    }

    public ArrayList<Weapon> getWeapons() {
        return executeTransaction(new Transaction<ArrayList<Weapon>>() {
            public ArrayList<Weapon> execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                try {
                    stmt = conn.prepareStatement("SELECT * FROM weapons");

                    ArrayList<Weapon> weapons = new ArrayList<Weapon>();

                    resultSet = stmt.executeQuery();

                    Boolean found = false;

                    System.out.println(resultSet.toString());

                    while (resultSet.next()) {
                        found = true;

                        Weapon weapon = new Weapon();
                        int index = 1;

                        weapon.setName(resultSet.getString(index++));
                        weapon.setDescription(resultSet.getString(index++));
                        weapon.setItemType(ItemType.valueOf(resultSet.getString(index++)));
                        weapon.setAttackPower(resultSet.getInt(index++));

                        weapons.add(weapon);
                    }

                    if (!found) {
                        System.out.println("No weapons found in the database");
                    }

                    return weapons;
                }
                finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }

    public ArrayList<StatusItem> getStatusItems() {
        return executeTransaction(new Transaction<ArrayList<StatusItem>>() {
            public ArrayList<StatusItem> execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;
                ResultSet resultSet = null;

                try {
                    stmt = conn.prepareStatement("SELECT * FROM status_items");

                    ArrayList<StatusItem> statusItems = new ArrayList<StatusItem>();

                    resultSet = stmt.executeQuery();

                    Boolean found = false;


                    while (resultSet.next()) {
                        found = true;

                        StatusItem statusItem = new StatusItem();
                        int index = 1;

                        statusItem.setName(resultSet.getString(index++));
                        statusItem.setDescription(resultSet.getString(index++));
                        statusItem.setItemType(ItemType.valueOf(resultSet.getString(index++)));
                        statusItem.setStatusItemType(StatusItemType.valueOf(resultSet.getString(index++)));
                        statusItem.setEffectAmt(resultSet.getInt(index++));

                        statusItems.add(statusItem);
                    }

                    if (!found) {
                        System.out.println("No status items found in the database");
                    }

                    return statusItems;
                }
                finally {
                    DBUtil.closeQuietly(stmt);
                }
            }
        });
    }

    // wrapper SQL transaction function that calls actual transaction function
    // (which has retries)
    public <ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
        try {
            return doExecuteTransaction(txn);
        } catch (SQLException e) {
            throw new PersistenceException("Transaction failed", e);
        }
    }

    // SQL transaction function which retries the transaction MAX_ATTEMPTS times
    // before failing
    public <ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
        Connection conn = connect();

        try {
            int numAttempts = 0;
            boolean success = false;
            ResultType result = null;

            while (!success && numAttempts < MAX_ATTEMPTS) {
                try {
                    result = txn.execute(conn);
                    conn.commit();
                    success = true;
                } catch (SQLException e) {
                    if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
                        // Deadlock: retry (unless max retry count has been reached)
                        numAttempts++;
                    } else {
                        // Some other kind of SQLException
                        throw e;
                    }
                }
            }

            if (!success) {
                throw new SQLException("Transaction failed (too many retries)");
            }

            // Success!
            return result;
        } finally {
            DBUtil.closeQuietly(conn);
        }
    }

    // TODO: Here is where you name and specify the location of your Derby SQL
    // database
    // TODO: Change it here and in SQLDemo.java under
    // CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
    // TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause
    // conflicts later w/Git
    private Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(databaseSource);

        // Set autocommit() to false to allow the execution of
        // multiple queries/statements as part of the same transaction.
        conn.setAutoCommit(false);

        return conn;
    }
}
