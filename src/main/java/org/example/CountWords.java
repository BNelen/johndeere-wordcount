package org.example;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountWords {

    private static final Set<String> COMMON_WORDS = Stream.of("the", "of", "to", "and", "a", "in", "is", "it", "you", "that", "he", "was", "for", "on", "are", "with", "as", "I", "his", "they", "be", "at", "one", "have", "this", "from", "or", "had", "by", "not", "word", "but", "what", "some", "we", "can", "out", "other", "were", "all", "there", "when", "up", "use", "your", "how", "said", "an", "each", "she")
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

    public static void main(String[] args) {
        String siteUrl = "https://www.gutenberg.org/files/2701/2701-0.txt";
        LinkedHashSet<Map.Entry<String, Integer>> wordCounts = countAndSortWords(readContentFromUrl(siteUrl), 50, 2);
        System.out.println("Results: " + wordCounts.toString());
    }

    /**
     * Reads content from given site url and returns as a string
     *
     * @param siteUrl
     * @return
     * @throws IOException
     */
    public static String readContentFromUrl(String siteUrl) {
        try {
            return new Scanner(new URL(siteUrl).openStream(), StandardCharsets.UTF_8).useDelimiter("\\A").next();
        } catch (IOException e) {
            System.out.println("Unable to retrieve content from URL: " + siteUrl);
            throw new RuntimeException(e);
        }
    }

    /**
     * Splits content string into words according to the given regex pattern: [a-zA-Z_0-9-'].
     * Filters out any words that are common as defined by us
     * Counts number of occurrences for each word and returns results sorted by words with most occurrences first.
     *
     * @param content    text to be split into words and counted
     * @param maxResults limits number of results to return
     * @return Set of words with their number of occurrences count, sorted by most frequent.
     */
    public static LinkedHashSet<Map.Entry<String, Integer>> countAndSortWords(String content, int maxResults, int minWordLength) {
        if (content == null || maxResults < 1) {
            return new LinkedHashSet<>();
        }

        HashMap<String, Integer> wordCount = new HashMap<>();

        Arrays.stream(content.split("[^a-zA-Z_0-9-']+"))
                .filter(Predicate.not(String::isBlank))
                .filter(word -> word.length() >= minWordLength) // Require words to be this long
                .map(String::toLowerCase)
                .filter(word -> !COMMON_WORDS.contains(word))
                .forEach(word -> wordCount.merge(word, 1, Integer::sum));

        return wordCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(maxResults)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}