package com.acme.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static com.acme.utility.GeneralUtil.readResults;
import static com.acme.utility.GeneralUtil.loadEnvVar;

import java.sql.ResultSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.exceptions.DatabaseConnectionError;

public class DBControllerTest {
    private static DBController dbController;

    @BeforeClass
    public static void setup() {
        String TEST_URL = loadEnvVar("TEST_URL");
        String TEST_USERNAME = loadEnvVar("TEST_USERNAME");
        String TEST_PASSWORD = loadEnvVar("TEST_PASSWORD");
        dbController = new DBController(TEST_URL, TEST_USERNAME, TEST_PASSWORD);
        dbController.connect();
    }

    @Test
    public void fetchTestData_12DigitBarcodeFormat() {
        ResultSet rs = dbController.getItem("123456789055");
        assertThat(readResults(rs)).containsExactly("123456789055", "Apple", "1.5");
    }

    @Test
    public void fetchTestData_13DigitBarcodeFormat() {
        ResultSet rs = dbController.getItem("0550987654321");
        assertThat(readResults(rs)).containsExactly("0550987654321", "Banana", "20");
    }

    @Test
    public void fetchInvalidData_ShortBarcode() {
        assertThatThrownBy(() -> {dbController.getItem("2324");}).isInstanceOf(DatabaseConnectionError.class).hasMessageContaining("ResultSet is empty.");
    }

    @Test
    public void fetchInvalidData_NoBarcode() {
        assertThatThrownBy(() -> {dbController.getItem("");}).isInstanceOf(DatabaseConnectionError.class).hasMessageContaining("ResultSet is empty.");
    }

    @Test
    public void fetchInvalidData_SQLInjection() {
        assertThatThrownBy(() -> {dbController.getItem("5'; insert into products values ('barcode', 'item', 1); select * from products where barcode = 'barcode");}).isInstanceOf(DatabaseConnectionError.class)
        .hasMessageContaining("Barcode contains invalid characters");
    }

    @AfterClass
    public static void tearDown() {
        dbController.disconnect();
    }
}
