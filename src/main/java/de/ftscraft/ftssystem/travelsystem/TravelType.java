package de.ftscraft.ftssystem.travelsystem;

public enum TravelType {

    CAPITAL_TO_KNOTENPUNKT(0),
    CAPITAL_TO_TOWN(0),
    KNOTENPUNKT_TO_CAPITAL(0),
    KNOTENPUNKT_TO_KNOTENPUNKT(0),
    KNOTENPUNKT_TO_TOWN(0),
    TOWN_TO_CAPITAL(0),
    TOWN_TO_KNOTENPUNKT(0),
    TOWN_TO_TOWN(0);

    private int price;
    TravelType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
