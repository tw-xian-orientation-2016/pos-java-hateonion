import org.junit.Before;
import org.junit.Test;
import sun.util.resources.cldr.ms.CalendarData_ms_MY;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PosTest {

    private Pos pos;
    private String[] input = {
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

    @Before
    public void init() {
        pos = new Pos();
    }

    @Test
    public void should_return_tags() {
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
}