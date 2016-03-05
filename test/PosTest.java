import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PosTest {

    private Pos pos;

    @Before
    public void init() {
        pos = new Pos();
    }

    @Test
    public void should_return_tags() {
        String[] input = {
                "ITEM000001",
                "ITEM000001",
                "ITEM000001",
                "ITEM000001",
                "ITEM000001",
                "ITEM000003-2",
                "ITEM000005",
                "ITEM000005",
                "ITEM000005"
        };

        HashMap settedTags = new HashMap();
        settedTags.put("ITEM000001", 5);
        settedTags.put("ITEM000003", 2);
        settedTags.put("ITEM000005", 3);

        HashMap myTags = pos.generateTags(input);

        assertThat(myTags, is(settedTags));
    }

    @Test
    public void should_return_cartItems() {

        HashMap tags = new HashMap();
        tags.put("ITEM000001", 5);
        tags.put("ITEM000003", 2);
        tags.put("ITEM000005", 3);
        Item []allItems = Fixture.loadAllItems();

        List<CartItem> settedCartItems = new ArrayList<>();

        settedCartItems.add(new CartItem(5, allItems[1]));
        settedCartItems.add(new CartItem(2, allItems[3]));
        settedCartItems.add(new CartItem(3, allItems[5]));

        List<CartItem> myCartItems= pos.generateCartItems(tags);

        assertTrue(settedCartItems.size() == myCartItems.size());
        for(int i = 0; i < settedCartItems.size(); i++) {
            CartItem currentMyCartItem = myCartItems.get(i);
            CartItem currentSettedCartItem = settedCartItems.get(i);

            assertThat(currentMyCartItem.count, is(currentSettedCartItem.count));
            assertThat(currentMyCartItem.item.barcode, is(currentSettedCartItem.item.barcode));
            assertThat(currentMyCartItem.item.name, is(currentSettedCartItem.item.name));
            assertThat(currentMyCartItem.item.price, is(currentSettedCartItem.item.price));
            assertThat(currentMyCartItem.item.unit, is(currentSettedCartItem.item.unit));
        }
    }

    @Test
    public void should_return_receiptItems() {
        Item []allItems = Fixture.loadAllItems();

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(5, allItems[1]));
        cartItems.add(new CartItem(2, allItems[3]));
        cartItems.add(new CartItem(3, allItems[5]));

        List<ReceiptItem> settedReceiptItems = new ArrayList<>();
        settedReceiptItems.add(new ReceiptItem(cartItems.get(0), 15, 3));
        settedReceiptItems.add(new ReceiptItem(cartItems.get(1), 30, 0));
        settedReceiptItems.add(new ReceiptItem(cartItems.get(2), 13.5, 4.5));

        List<ReceiptItem> myReceiptItems = pos.generateReceiptItems(cartItems);

        assertTrue(myReceiptItems.size() == settedReceiptItems.size());

        for(int i = 0; i < settedReceiptItems.size(); i++) {
            ReceiptItem currentMyItem = myReceiptItems.get(i);
            ReceiptItem currentSettedItem = settedReceiptItems.get(i);

            assertThat(currentMyItem.total, is(currentSettedItem.total));
            assertThat(currentMyItem.save, is(currentSettedItem.save));
            assertThat(currentMyItem.cartItem.count, is(currentSettedItem.cartItem.count));
            assertThat(currentMyItem.cartItem.item.name, is(currentSettedItem.cartItem.item.name));
            assertThat(currentMyItem.cartItem.item.unit, is(currentSettedItem.cartItem.item.unit));
            assertThat(currentMyItem.cartItem.item.barcode, is(currentSettedItem.cartItem.item.barcode));
            assertThat(currentMyItem.cartItem.item.price, is(currentSettedItem.cartItem.item.price));
        }
    }

    @Test
    public void should_print_correct_content() {
        List<ReceiptItem> receiptItems = new ArrayList<>();
        Item []allItems = Fixture.loadAllItems();

        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(5, allItems[1]));
        cartItems.add(new CartItem(2, allItems[3]));
        cartItems.add(new CartItem(3, allItems[5]));

        receiptItems.add(new ReceiptItem(cartItems.get(0), 15, 3));
        receiptItems.add(new ReceiptItem(cartItems.get(1), 30, 0));
        receiptItems.add(new ReceiptItem(cartItems.get(2), 13.5, 4.5));

        PrintStream mockedPrintStream = mock(PrintStream.class);
        System.setOut(mockedPrintStream);
        String expectedContent =
                "***<没钱赚商店>收据***\n" +
                "名称：雪碧，数量：5瓶，单价：3.00(元)，小计：12.00(元)\n" +
                "名称：荔枝，数量：2斤，单价：15.00(元)，小计：30.00(元)\n" +
                "名称：方便面，数量：3袋，单价：4.50(元)，小计：9.00(元)\n" +
                "----------------------\n" +
                "总计：51.00(元)\n" +
                "节省：7.50(元)\n" +
                "**********************";
        pos.printRecipt(receiptItems);
        verify(mockedPrintStream).print(expectedContent);

    }
}