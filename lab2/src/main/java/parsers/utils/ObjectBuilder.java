package parsers.utils;

import generated.classes.*;

import java.util.HashMap;

public class ObjectBuilder {
    private ObjectFactory objectFactory = new ObjectFactory();
    private Paper paper = objectFactory.createPaper();

    private static int counter = 0;

    public void addPaper(HashMap<String, String> values){
        Periodical periodical = objectFactory.createPeriodical();
        Characteristics characteristics = objectFactory.createCharacteristics();

        periodical.setId(String.valueOf(counter++));
        periodical.setTitle(values.get("title"));
        periodical.setType(Type.valueOf(values.get("type")));
        periodical.setMonthly(Boolean.parseBoolean(values.get("monthly")));

        characteristics.setColorful(Boolean.parseBoolean(values.get("colorful")));
        characteristics.setPageAmount(Long.parseLong(values.get("pageAmount")));

        if (values.containsKey("glossy")) {
            characteristics.setGlossy(Boolean.parseBoolean(values.get("glossy")));
        }

        if (values.containsKey("subscribable")) {
            characteristics.setSubscribable(Boolean.parseBoolean(values.get("subscribable")));
        }

        periodical.setCharacteristics(characteristics);

        paper.getPeriodicals().add(periodical);
    }

    public Paper getPaper(){
        return paper;
    }
}
