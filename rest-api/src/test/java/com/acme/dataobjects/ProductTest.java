package com.acme.dataobjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Before;
import org.junit.Test;

import com.acme.utility.InvalidProductException;

public class ProductTest {
    
    private Product testProduct;

    @Before
    public void setup() {
        this.testProduct = new Product("0123456789015", "Apple", 1.47);
    }

    @Test
    public void getProductInformation() {
        assertThat(testProduct.getBarcode()).isEqualTo("0123456789015");
        assertThat(testProduct.getName()).isEqualTo("Apple");
        assertThat(testProduct.getPrice()).isEqualTo(1.47);
    }

    @Test
    public void getFormattedPrice() {
        assertThat(testProduct.getPriceString()).isEqualTo("£1.47");
    }

    @Test
    public void getFormattedPrice_At2DecPlaces() {
        assertThat(new Product("0123456789015", "Item", 1).getPriceString()).isEqualTo("£1.00");
        assertThat(new Product("0123456789015", "Item", 5.4).getPriceString()).isEqualTo("£5.40");
    }

    @Test
    public void createIncompleteProduct_NullName() {
        assertThatThrownBy(() -> {
            new Product("0123456789015", null, 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has no name.");
    }

    @Test
    public void createIncompleteProduct_EmptyName() {
        assertThatThrownBy(() -> {
            new Product("0123456789015", "", 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has no name.");
    }

    @Test
    public void createIncompleteProduct_NullBarcode() {
        assertThatThrownBy(() -> {
            new Product(null, "Item", 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has no barcode.");
    }

    @Test
    public void createIncompleteProduct_EmptyBarcode() {
        assertThatThrownBy(() -> {
            new Product("", "Item", 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has no barcode.");
    }

    @Test
    public void createIncompleteProduct_PriceIs0() {
        assertThatThrownBy(() -> {
            new Product("0123456789015", "Item", 0);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has no price.");
    }

    @Test
    public void createIncompleteProduct_Barcode13DigitsNoLeading0 () {
        assertThatThrownBy(() -> {
            new Product("1234567891234", "Item", 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has invalid barcode.");
    }

    @Test
    public void createIncompleteProduct_BarcodeTooShort() {
        assertThatThrownBy(() -> {
            new Product("123", "Item", 1);
        }).isInstanceOf(InvalidProductException.class).hasMessage("Incomplete Product: product has invalid barcode.");
    }

    @Test
    public void createProduct_PriceRoundUp() {
        assertThat(new Product("0123456789015", "Item", 1.456).getPrice()).isEqualTo(1.46);
    }

    @Test
    public void createProduct_PriceRoundDown() {
        assertThat(new Product("0123456789015", "Item", 1.454).getPrice()).isEqualTo(1.45);
    }
}
