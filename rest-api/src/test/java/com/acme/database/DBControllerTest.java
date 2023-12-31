package com.acme.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.exceptions.DatabaseConnectionError;

public class DBControllerTest {
    private static DBController dbController;

    @BeforeClass
    public static void setup() {
        String TEST_URL = System.getenv("ACMEDB_TEST_URL");
        String TEST_USERNAME = System.getenv("ACMEDB_TEST_USERNAME");
        String TEST_PASSWORD = System.getenv("ACMEDB_TEST_PASSWORD");
        dbController = new DBController(TEST_URL, TEST_USERNAME, TEST_PASSWORD);
        dbController.connect();
    }

    @Test
    public void product_FetchTestData_12DigitBarcodeFormat() {
        assertThat(dbController.getItem("123456789055")).containsExactly("123456789055", "Apple", "1.5");
    }

    @Test
    public void product_FetchTestData_13DigitBarcodeFormat() {
        assertThat(dbController.getItem("0550987654321")).containsExactly("0550987654321", "Banana", "20");
    }

    @Test
    public void product_FetchInvalidData_ShortBarcode() {
        assertThatThrownBy(() -> {
            dbController.getItem("2324");
        }).isInstanceOf(DatabaseConnectionError.class).hasMessageContaining("ResultSet is empty.");
    }

    @Test
    public void product_FetchInvalidData_NoBarcode() {
        assertThatThrownBy(() -> {
            dbController.getItem("");
        }).isInstanceOf(DatabaseConnectionError.class).hasMessageContaining("ResultSet is empty.");
    }

    @Test
    public void product_FetchInvalidData_SQLInjection() {
        assertThatThrownBy(() -> {
            dbController.getItem(
                    "5'; insert into products values ('barcode', 'item', 1); select * from products where barcode = 'barcode");
        }).isInstanceOf(DatabaseConnectionError.class)
                .hasMessageContaining("Barcode contains invalid characters");
    }

    @AfterClass
    public static void tearDown() {
        dbController.disconnect();
    }
}
