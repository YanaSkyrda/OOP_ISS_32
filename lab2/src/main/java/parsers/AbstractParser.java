package parsers;

import java.util.logging.Logger;
import generated.classes.ObjectFactory;
import generated.classes.Paper;
import parsers.utils.ObjectBuilder;

import java.util.HashMap;

public abstract class AbstractParser {
    static final Logger logger = Logger.getLogger(DOMParser.class.getName());

    HashMap<String, String> data = new HashMap<>();
    ObjectBuilder objectBuilder = new ObjectBuilder();
    ObjectFactory objectFactory = new ObjectFactory();

    public AbstractParser(){
        objectBuilder.resetID();
    }

    public Paper getObject() {
        return objectBuilder.getPaper();
    }

    public abstract Paper parseXML(String filename);
}
