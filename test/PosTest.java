import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PosTest {

    @Test
    public void should_return_splitedNumber_HashTable() {
        Pos pos = new Pos();
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
        HashMap correctResult = new HashMap();
        correctResult.put("ITEM000001", 5);
        correctResult.put("ITEM000003", 2);
        correctResult.put("ITEM000005", 3);

        HashMap result = pos.generateTags(input);

        assertThat(result, is(correctResult));
    }
}