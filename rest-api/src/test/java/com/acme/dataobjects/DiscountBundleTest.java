package com.acme.dataobjects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static com.acme.dataobjects.DiscountType.PERCENTAGE;
import static com.acme.dataobjects.DiscountType.PRICE;

import org.junit.Before;
import org.junit.Test;

import com.acme.utility.InvalidDiscountBundleException;

public class DiscountBundleTest {
    private Product bundleTestProduct;
    private DiscountBundle testBundlePercentage;
    private DiscountBundle testBundlePrice;

    @Before
    public void setup() {
        this.bundleTestProduct = new Product("123456789023", "Item", 2);
        this.testBundlePercentage = new DiscountBundle(1, this.bundleTestProduct, 3, PERCENTAGE, 5);
        this.testBundlePrice = new DiscountBundle(1, this.bundleTestProduct, 3, PRICE, 5);
    }

    @Test
    public void getDiscountBundleInformation() {
        assertThat(testBundlePercentage.getDiscountId()).isEqualTo(1);
        assertThat(testBundlePercentage.getQuantity()).isEqualTo(3);
        assertThat(testBundlePercentage.getDiscountType()).isEqualTo(PERCENTAGE);
        assertThat(testBundlePrice.getDiscountType()).isEqualTo(PRICE);
        assertThat(testBundlePercentage.getDiscountAmount()).isEqualTo(5);
    }

    @Test
    public void getDiscountBundlePercentage_TotalPrice() {
        assertThat(testBundlePercentage.getTotalPrice()).isEqualTo(5.7);
    }

    @Test
    public void getDiscountBundlePrice_TotalPrice() {
        assertThat(testBundlePrice.getTotalPrice()).isEqualTo(1);
    }

    @Test
    public void invalidDiscountBundle_QuantityOf1() {
        assertThatThrownBy(() -> {
            new DiscountBundle(1, this.bundleTestProduct, 1, PERCENTAGE, 2);
        }).isInstanceOf(InvalidDiscountBundleException.class).hasMessage("Invalid Discount Bundle: quantity is too low.");
    }

    @Test
    public void invalidDiscoundBundle_NegativeQuantity() {
        assertThatThrownBy(() -> {
            new DiscountBundle(1, this.bundleTestProduct, -4, PERCENTAGE, 2);
        }).isInstanceOf(InvalidDiscountBundleException.class).hasMessage("Invalid Discount Bundle: quantity is too low.");
    }

    @Test
    public void edgeCaseBundle_AmountMoreThan2DecPlacesRoundUp() {
        assertThat(new DiscountBundle(1, this.bundleTestProduct, 2, PERCENTAGE, 4.576).getDiscountAmount()).isEqualTo(4.58);
    }

    @Test
    public void edgeCaseBundle_AmountMoreThan2DecPlacesRoundDown() {
        assertThat(new DiscountBundle(1, this.bundleTestProduct, 2, PERCENTAGE, 4.574).getDiscountAmount()).isEqualTo(4.57);
    }

    @Test
    public void getDiscountAmountFormatted_Percentage() {
        assertThat(testBundlePercentage.getDiscountAmountFormatted()).isEqualTo("5.00%");
    }

    @Test
    public void getDiscountAmountFormatted_Price() {
        assertThat(testBundlePrice.getDiscountAmountFormatted()).isEqualTo("£5.00");
    }

    @Test
    public void getTotalPriceFormatted_Percentage() {
        assertThat(testBundlePercentage.getTotalPriceFormatted()).isEqualTo("£5.70");
    }

    @Test
    public void getTotalPriceFormatted_Price() {
        assertThat(testBundlePrice.getTotalPriceFormatted()).isEqualTo("£1.00");
    }
}
