package com.acme.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.dataobjects.Product;
import com.acme.exceptions.InvalidProductException;

public class ProductDAOTest {
    private static ProductDAO dao;
    private static DBController mockController;

    @BeforeClass
    public static void setup() {
        mockController = mock(DBController.class);
        dao = new ProductDAO(mockController);
        when(mockController.connect()).thenReturn(true);
        when(mockController.disconnect()).thenReturn(true);
    }

    @Test
    public void fetchItem_Standard() {
        String[] mockedResult = {"123456789055", "name", "1"};
        when(mockController.getItem("123456789055")).thenReturn(mockedResult);
        Product result = dao.fetchItem("123456789055");
        assertThat(result.getBarcode()).isEqualTo(mockedResult[0]);
        assertThat(result.getName()).isEqualTo(mockedResult[1]);
        assertThat(result.getPrice()).isEqualTo(1.0);
    }

    @Test
    public void fetchItem_InvalidBarcode() {
        String[] mockedResult = {"1234", "name", "1"};
        when (mockController.getItem("1234")).thenReturn(mockedResult);
        assertThatThrownBy(() -> dao.fetchItem("1234")).isInstanceOf(InvalidProductException.class);
    }
}
