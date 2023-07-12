package com.acme.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.BeforeClass;
import org.junit.Test;

import com.acme.dataobjects.DiscountBundle;
import com.acme.dataobjects.Product;
import com.acme.exceptions.InvalidProductException;

public class ProductDAOTest {
    private static ProductDAO dao;
    private static DBController mockController;
    private static Product testProduct;

    @BeforeClass
    public static void setup() {
        testProduct = new Product("123456789055", "name", 1);
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

    @Test
    public void checkForDiscount_Standard() {
        String[] mockedResult = {"2", "123456789055", "2", "true", "5"};
        when(mockController.getDiscount("123456789055", 2)).thenReturn(mockedResult);
        DiscountBundle testBundle = dao.checkForBundle(testProduct, 2);
        assertThat(testBundle.getDiscountId()).isEqualTo(2);
        assertThat(testBundle.getDiscountAmountFormatted()).isEqualTo("5.00%");
        assertThat(testBundle.getQuantity()).isEqualTo(2);
        assertThat(testBundle.getDiscountAmount()).isEqualTo(5);
    }

    @Test
    public void checkForDiscount_InvalidQuery() {
        when(mockController.getDiscount("123456789055", 2)).thenReturn(null);
        DiscountBundle testBundle = dao.checkForBundle(testProduct, 2);
        assertThat(testBundle).isNull();
    }
}
