public class CartItem {
    Item item = new Item();
    int count;

    public CartItem(int count, Item item) {
        this.count = count;
        this.item = item;
    }
}
