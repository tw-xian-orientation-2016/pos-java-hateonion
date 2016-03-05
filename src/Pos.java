import java.util.HashMap;
import java.util.Hashtable;

import static java.lang.Integer.compareUnsigned;
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
}
