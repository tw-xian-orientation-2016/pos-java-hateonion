public class Item {
    String barcode, name, unit;
    double price;

    public Item(String barcode, String name, String unit, double price) {
        this.barcode = barcode;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public Item() {
    }
}
