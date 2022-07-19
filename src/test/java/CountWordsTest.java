import org.example.CountWords;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountWordsTest {

    @Test
    public void commonWordExclusion() {
        String text = "Apple a this word computer Computer desk computer's";
        HashMap<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("computer", 2);
        expectedMap.put("computer's", 1);
        expectedMap.put("apple", 1);
        expectedMap.put("desk", 1);

        Set<Map.Entry<String, Integer>> expectedList = new HashSet<>(expectedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toCollection(LinkedHashSet::new)));

        assertEquals(expectedList, CountWords.countAndSortWords(text, 50, 2));
    }

    @Test
    public void emptyContent() {
        assertEquals(Collections.emptySet(), CountWords.countAndSortWords("", 10, 1));
    }

    @Test
    public void nullContent() {
        assertEquals(Collections.emptySet(), CountWords.countAndSortWords(null, 10, 1));
    }
}
