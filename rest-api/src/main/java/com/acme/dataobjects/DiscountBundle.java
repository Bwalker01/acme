package com.acme.dataobjects;

import com.acme.utility.InvalidDiscountBundleException;
import static com.acme.utility.GeneralUtil.round;
import static com.acme.dataobjects.DiscountType.PERCENTAGE;
import static com.acme.dataobjects.DiscountType.PRICE;

public class DiscountBundle {
    private final int discountId;
    private final Product product;
    private final int quantity;
    private final DiscountType type;
    private final double amount;
    private final double totalPrice;

    public DiscountBundle(int id, Product product, int quantity, DiscountType type, double amount) {
        validateBundle(quantity, type, amount);
        this.discountId = id;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.amount = round(amount);
        if (type == PERCENTAGE) {
            this.totalPrice = round((product.getPrice() * quantity) * (1.0 - (amount / 100.0)));
        } else if (type == PRICE) {
            this.totalPrice = round((product.getPrice() * quantity) - amount);
        } else throw new InvalidDiscountBundleException("invalid Discount Type.");
    }

    private static void validateBundle(int quantity, DiscountType type, double amount) {
        if (quantity < 2) {
            throw new InvalidDiscountBundleException("quantity is too low.");
        } else if (amount < 0.01) {
            throw new InvalidDiscountBundleException("there is no discount amount.");
        }
    }


    public int getDiscountId() {
        return discountId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public DiscountType getDiscountType() {
        return type;
    }

    public double getDiscountAmount() {
        return amount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDiscountAmountFormatted() {
        return String.format(this.type == PERCENTAGE ? "%.2f%%" : "£%.2f", this.amount);
    }

    public String getTotalPriceFormatted() {
        return String.format("£%.2f", this.totalPrice);
    }
}
