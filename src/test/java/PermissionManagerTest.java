import java.util.*;
import java.lang.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PermissionManagerTest {

    PermissionManager permissionManager;

    @BeforeEach
    void setUp() {
        permissionManager = new PermissionManager();
    }

    @Test
    @DisplayName("Unit Test for getPermissionLevel() method")
    void testGetPermissionLevel() {
        assertEquals("User", permissionManager.getPermissionLevel(), "User should be returned as a default role");
    }

    @Test
    @DisplayName("Unit Test for setPermissionLevel() method")
    void testSetPermissionLevel() {
        var ht = new Hashtable<PermissionLevel, String>();

        ht.put(PermissionLevel.ADMIN, "Admin");
        ht.put(PermissionLevel.DEVELOPER, "Developer");
        ht.put(PermissionLevel.USER, "User");

        ht.forEach((key, value) -> {
            permissionManager.setPermissionLevel(key);
            assertEquals(value, permissionManager.getPermissionLevel(), value + "should be returned after setting the role to" + key + "case");
        });
    }
}