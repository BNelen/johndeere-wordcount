package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.LinkedHashSet;
import java.util.Map;

public class LambdaRequestHandler implements RequestHandler<String, String> {
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input URL: " + input);

        LinkedHashSet<Map.Entry<String, Integer>> wordCounts = CountWords.countAndSortWords(CountWords.readContentFromUrl(input), 50, 2);

        return "Results: " + wordCounts.toString();
    }
}