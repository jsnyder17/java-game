package persist;

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

    public void initialize() {
        System.out.println("Searching for existing database ... ");

        File file = new File(Constants.DATABASE_SOURCE);

        if (!file.exists()) {
            createTables();
            loadInitialData();
        }

        System.out.println("Finished search");
    }

    public void loadInitialData() {
        DerbyDatabase db = new DerbyDatabase();
        db.createTables();

        db.loadInitialData();
    }

    public void createTables() {
        executeTransaction(new Transaction<Boolean>() {
            @SuppressWarnings("resource")
            @Override
            public Boolean execute(Connection conn) throws SQLException {
                PreparedStatement stmt = null;

                try {
                    stmt = conn.prepareStatement(Constants.CREATE_ENTITIES_TABLE);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement(Constants.CREATE_WEAPONS_TABLE);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement(Constants.CREATE_SKILLS_TABLE);
                    stmt.executeUpdate();

                    stmt = conn.prepareStatement(Constants.CREATE_BALLS);
                    stmt.executeUpdate();
                }
                finally {
                    DBUtil.closeQuietly(stmt);
                }

                return true;
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
        Connection conn = DriverManager.getConnection(Constants.DATABASE_SOURCE + "create=true");

        // Set autocommit() to false to allow the execution of
        // multiple queries/statements as part of the same transaction.
        conn.setAutoCommit(false);

        return conn;
    }
}
