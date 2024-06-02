package dataaccess;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

public class DatabaseManager {
    private static final String DATABASE_NAME;
    private static final String USER;
    private static final String PASSWORD;
    private static final String CONNECTION_URL;

    private static final String[] createUserTableStatement = {
          """
          CREATE TABLE IF NOT EXISTS UserData (
            `username` VARCHAR(256) NOT NULL,
            `password` VARCHAR(256) NOT NULL,
            `email` VARCHAR(256) NOT NULL,
            PRIMARY KEY (`username`),
            INDEX (`email`)
          ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
          """
    };


//    private static final String[] createUserTableStatement = {
//        "CREATE TABLE IF NOT EXISTS UserData (",
//        "`username` VARCHAR(256) NOT NULL,",
//        "`password` VARCHAR(256) NOT NULL,",
//        "`email` VARCHAR(256) NOT NULL,",
//        "PRIMARY KEY (`username`),",
//        "INDEX (`email`)",
//        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
//    };





    private static final String[] createAuthTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS AuthData (
            `authToken` VARCHAR(256) NOT NULL,
            `username` VARCHAR(256) DEFAULT NULL,
            PRIMARY KEY (`authToken`),
            FOREIGN KEY (`username`) REFERENCES UserData(`username`)
                ON DELETE CASCADE
                ON UPDATE CASCADE
            )
            """
    };


    private static final String[] createGameTableStatement = {
        """
        CREATE TABLE IF NOT EXISTS GameData (
        `gameID` INT NOT NULL AUTO_INCREMENT,
        `whiteUsername` VARCHAR(256) DEFAULT NULL,
        `blackUsername` VARCHAR(256) DEFAULT NULL,
        `gameName` VARCHAR(256) NOT NULL,
        `game` LONGTEXT DEFAULT NULL,
        PRIMARY KEY (`gameID`)
        )
        """
    };


    /*
     * Load the database information for the db.properties file.
     */
    static {
        try {
            try (var propStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
                if (propStream == null) throw new Exception("Unable to load db.properties");
                Properties props = new Properties();
                props.load(propStream);
                DATABASE_NAME = props.getProperty("db.name");
                USER = props.getProperty("db.user");
                PASSWORD = props.getProperty("db.password");

                var host = props.getProperty("db.host");
                var port = Integer.parseInt(props.getProperty("db.port"));
                CONNECTION_URL = String.format("jdbc:mysql://%s:%d", host, port);
            }
        } catch (Exception ex) {
            throw new RuntimeException("unable to process db.properties. " + ex.getMessage());
        }
    }

    /**
     * Creates the database if it does not already exist.
     */
    public static void createDatabase() throws DataAccessException {
        try {
            var statement = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();

////                String createUserTableSQL = String.join("\n", createUserTableStatement);
//                try (var preparedStatementUser = conn.prepareStatement("""
//                        CREATE TABLE IF NOT EXISTS UserData (`username` VARCHAR(256) NOT NULL, `password` VARCHAR(256) NOT NULL, `email` VARCHAR(256) NOT NULL, PRIMARY KEY (`username`), INDEX (`email`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci""")) {
//                    preparedStatementUser.executeUpdate();
//                }
//                try (var preparedStatementAuth = conn.prepareStatement(Arrays.toString(createAuthTableStatement))) {
//                    preparedStatementAuth.executeUpdate();
//                }
//                try (var preparedStatementGame = conn.prepareStatement(Arrays.toString(createGameTableStatement))) {
//                    preparedStatementGame.executeUpdate();
//                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Create a connection to the database and sets the catalog based upon the
     * properties specified in db.properties. Connections to the database should
     * be short-lived, and you must close the connection when you are done with it.
     * The easiest way to do that is with a try-with-resource block.
     * <br/>
     * <code>
     * try (var conn = DbInfo.getConnection(databaseName)) {
     * // execute SQL statements.
     * }
     * </code>
     */
    public static Connection getConnection() throws DataAccessException {
        try {
            var conn = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            conn.setCatalog(DATABASE_NAME);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }



//
//    private static void createTables() throws DataAccessException{
//        // method for creating all the tables and types in mySQL
//        // I think this could be a good place for it, but does it need to return something? just throw exc?
//        try (var conn = DatabaseManager.getConnection()) {
//            try (var preparedStatement = conn.prepareStatement(Arrays.toString(createUserTableStatement))) {
//                preparedStatement.executeQuery();
//            }
//            try (var preparedStatement = conn.prepareStatement(Arrays.toString(createAuthTableStatement))) {
//                preparedStatement.executeQuery();
//            }
//            try (var preparedStatement = conn.prepareStatement(Arrays.toString(createGameTableStatement))) {
//                preparedStatement.executeQuery();
//            }
//        } catch (SQLException e) {
//          throw new DataAccessException("Error: Couldn't Create the SQL Tables");
//        }
//    }
}
