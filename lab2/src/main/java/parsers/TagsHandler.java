package parsers;

import parsers.utils.ObjectBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TagsHandler {
    private HashMap<String, String> hashMap;
    private List<String> tags = new ArrayList<>(Arrays.asList("type", "monthly", "colorful", "pageAmount", "subscribable", "glossy"));

    public void add(String nodeName, String textContent, ObjectBuilder objectBuilder) {
        System.out.println("NodeName: " + nodeName + " " + textContent);
        if (nodeName.equals("title")) {
            if (hashMap != null) {
                objectBuilder.addPaper(hashMap);
            }

            hashMap = new HashMap<>();
            hashMap.put(nodeName, textContent);
        } else if (tags.contains(nodeName)) {
            hashMap.put(nodeName, textContent);
        }
    }

    public void flush(ObjectBuilder objectBuilder){
        objectBuilder.addPaper(hashMap);
        hashMap = new HashMap<>();
    }
}
