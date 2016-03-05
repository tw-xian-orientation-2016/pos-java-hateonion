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

    public void printRecipt(List<ReceiptItem> receiptItems) {
        String receipt = "***<没钱赚商店>收据***\n";
        double total = 0;
        double totalSave = 0;
        for(ReceiptItem receiptItem : receiptItems) {
            double subTotal = receiptItem.total - receiptItem.save;
            String detail = String.format("名称：%s，数量：%d%s，单价：%.2f(元)，小计：%.2f(元)\n", receiptItem.cartItem.item.name, receiptItem.cartItem.count, receiptItem.cartItem.item.unit, receiptItem.cartItem.item.price, subTotal);
            receipt += detail;
            total += subTotal;
            totalSave += receiptItem.save;
        }
        receipt += "----------------------\n";
        receipt += String.format("总计：%.2f(元)\n", total) + String.format("节省：%.2f(元)\n", totalSave);
        receipt += "**********************";

        System.out.print(receipt);
    }
}
