public class ReceiptItem {
    CartItem cartItem;
    double total;
    double save;

    public ReceiptItem(CartItem cartItem, double total, double save) {
        this.cartItem = cartItem;
        this.total = total;
        this.save = save;
    }
}
