import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Pos {

    public HashMap generateTags(String[] input) {
        HashMap tags = new HashMap();
        int count;

        for (String item : input) {
            String[] current = item.split("-");
            if (tags.containsKey(current[0])) {
                if (current.length > 1) {
                    count = parseInt(current[1]) + parseInt((tags.get(current[0])).toString());
                    tags.remove(current[0]);
                    tags.put(current[0], count);
                } else {
                    count = 1 + parseInt((tags.get(current[0])).toString());
                    tags.remove(current[0]);
                    tags.put(current[0], count);
                }
            } else {
                if (current.length > 1) {
                    tags.put(current[0], parseInt(current[1]));
                } else {
                    tags.put(current[0], 1);
                }
            }
        }
        return tags;
    }

    public List<CartItem> generateCartItems(HashMap tags) {

        Item []allItems = Fixture.loadAllItems();
        List<CartItem> cartItems = new ArrayList<>();

        for(int i = 0; i < allItems.length; i++) {
            if(tags.containsKey(allItems[i].barcode)) {
                CartItem cartItem = new CartItem(parseInt(tags.get(allItems[i].barcode).toString()), allItems[i]);
                cartItems.add(cartItem);
            }
        }
        return cartItems;
    }


    public List<ReceiptItem> generateReceiptItems(List<CartItem> cartItems) {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        Promotion []promotions = Fixture.loadPromotions();


        for(CartItem cartItem : cartItems) {
            double save = 0;
            for(Promotion promotion : promotions) {
                for(String barcode : promotion.barcodes) {
                    if(barcode == cartItem.item.barcode) {
                        save = cartItem.count / 3 * cartItem.item.price;
                        break;
                    }
                }
            }
            double total = cartItem.item.price * cartItem.count;
            ReceiptItem receiptItem = new ReceiptItem(cartItem, total, save);
            receiptItems.add(receiptItem);
        }

        return receiptItems;
    }
}
