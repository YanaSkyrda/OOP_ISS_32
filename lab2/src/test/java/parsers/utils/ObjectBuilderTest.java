package parsers.utils;

import generated.classes.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parsers.ParserTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectBuilderTest {
    ObjectBuilder objectBuilder;
    HashMap<String, String> hashMap;

    public ObjectBuilderTest(){
        objectBuilder = new ObjectBuilder();

        objectBuilder.resetID();

        hashMap = new HashMap<>();

        hashMap.put("colorful","true");
        hashMap.put("pageAmount","4");
        hashMap.put("glossy","true");
        hashMap.put("title","Kharkiv city tour");
        hashMap.put("type","BOOKLET");
        hashMap.put("monthly","false");

        objectBuilder.addPaper(hashMap);
        hashMap.clear();

        hashMap.put("colorful","true");
        hashMap.put("pageAmount","30");
        hashMap.put("glossy","true");
        hashMap.put("subscribable","true");
        hashMap.put("title","Playboy");
        hashMap.put("type","MAGAZINE");
        hashMap.put("monthly","true");

        objectBuilder.addPaper(hashMap);
        hashMap.clear();

        hashMap.put("colorful","false");
        hashMap.put("pageAmount","8");
        hashMap.put("subscribable","true");
        hashMap.put("title","New Your Times");
        hashMap.put("type","NEWSPAPER");
        hashMap.put("monthly","false");

        objectBuilder.addPaper(hashMap);
        hashMap.clear();
    }

    @Test
    @DisplayName("Check if object created correctly")
    void checkObjectCreation(){
        assertTrue(objectBuilder.getPaper().equals(ParserTest.toCheck));
    }
}
