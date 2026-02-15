package data;

import dal.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseConnectionTest {

    @AfterEach
    void tearDown() {
        // Close connection after each test to avoid resource leaks
        DatabaseConnection instance = DatabaseConnection.getInstance();
        instance.closeConnection();
    }

    @Test
    void testSingletonInstanceIsNotNull() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertNotNull(instance, "DatabaseConnection instance should not be null");
    }

    @Test
    void testSingletonReturnsSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertSame(instance1, instance2, "getInstance() should always return the same instance");
    }

    @Test
    void testGetConnectionReturnsSameConnectionObject() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        Connection conn1 = instance.getConnection();
        Connection conn2 = instance.getConnection();

        assertSame(conn1, conn2, "getConnection() should return the same Connection instance");
    }

    @Test
    void testCloseConnectionDoesNotThrow() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertDoesNotThrow(() -> instance.closeConnection(), "closeConnection() should not throw exception");
    }

    @Test
    void testConnectionMayBeNullIfConfigInvalid() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        Connection conn = instance.getConnection();

        // This test documents current behavior if DB config is missing/invalid
        // Either connection is valid OR null, but should not crash the test
        try {
			assertTrue(conn == null || !conn.isClosed(), 
			    "Connection should be either null (config/DB not available) or open");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
