public class Fixture {

    public static Item[] loadAllItems() {
        Item[] items = {new Item("ITEM000000", "可口可乐", "瓶", 3.00), new Item("ITEM000001", "雪碧", "瓶", 3.00), new Item("ITEM000002", "苹果", "斤", 5.50), new Item("ITEM000003", "荔枝", "斤", 15.00), new Item("ITEM000004", "电池", "个", 2.00), new Item("ITEM000005", "方便面", "袋", 4.50)};
        return items;
    }

    public static Promotion[] loadPromotions() {
        String []barcodes = {"ITEM000000", "ITEM000001", "ITEM000005"};
        Promotion []promotions = {new Promotion("Buy Two Get One Free",barcodes)};
        return promotions;
    }
}
