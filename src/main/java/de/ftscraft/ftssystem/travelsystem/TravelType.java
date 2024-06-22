package de.ftscraft.ftssystem.travelsystem;

public enum TravelType {

    CAPITAL_TO_KNOTENPUNKT(),
    CAPITAL_TO_TOWN(),
    KNOTENPUNKT_TO_CAPITAL(),
    KNOTENPUNKT_TO_KNOTENPUNKT(),
    KNOTENPUNKT_TO_TOWN(),
    TOWN_TO_CAPITAL(),
    TOWN_TO_KNOTENPUNKT(),
    TOWN_TO_TOWN();

    private int price;
    TravelType() {
        this.price = 0;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
