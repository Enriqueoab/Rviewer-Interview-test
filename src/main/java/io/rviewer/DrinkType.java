package io.rviewer;

public enum DrinkType {

    TEA("tea", 0.4F), COFFEE("coffee", 0.5F), CHOCOLATE("chocolate", 0.6F);

    public final String type;
    public final float price;

    DrinkType(String type, float price) {
        this.type = type;
        this.price = price;
    }
}
