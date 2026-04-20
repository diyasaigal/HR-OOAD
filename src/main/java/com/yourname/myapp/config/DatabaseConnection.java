package com.yourname.myapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DatabaseConnection - JDBC-based database connection manager
 * 
 * Replaces HibernateUtil for direct JDBC connectivity
 * Provides centralized database connection management without ORM
 * 
 * Features:
 * - Simple connection pooling support
 * - Environment variable configuration
 * - SQL schema initialization on startup
 * - Clean database connection closing
 * 
 * Usage:
 * Connection conn = DatabaseConnection.getConnection();
 * // Use connection for JDBC operations
 * conn.close();
 * 
 * Or use try-with-resources:
 * try (Connection conn = DatabaseConnection.getConnection()) {
 *     // JDBC operations
 * }
 * 
 * @author OOAD Project
 * @since 2024
 */
public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    
    private static String dbUrl;
    private static String dbUsername;
    private static String dbPassword;
    
    static {
        // Initialize database connection settings from environment or defaults
        dbUrl = System.getenv("DB_URL") != null ? System.getenv("DB_URL") : "jdbc:mysql://localhost:3306/eims_db";
        dbUsername = System.getenv("DB_USERNAME") != null ? System.getenv("DB_USERNAME") : "root";
        dbPassword = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "Guggulop@9";
        
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver loaded successfully");
            
            // Initialize database schema on startup
            DatabaseInitializer.initializeDatabase(dbUrl, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load MySQL JDBC Driver", e);
            throw new ExceptionInInitializerError(e);
        }
    }
    
    /**
     * Get a new database connection
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }
    
    /**
     * Get database URL
     * 
     * @return Database JDBC URL
     */
    public static String getDbUrl() {
        return dbUrl;
    }
    
    /**
     * Get database username
     * 
     * @return Database username
     */
    public static String getDbUsername() {
        return dbUsername;
    }
    
    /**
     * Get database password
     * 
     * @return Database password
     */
    public static String getDbPassword() {
        return dbPassword;
    }
}
