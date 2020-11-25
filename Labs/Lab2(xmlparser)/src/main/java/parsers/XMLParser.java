package parsers;

import java.io.File;

public abstract class XMLParser {
    OrangeryHandler orangeryHandler = new OrangeryHandler();
    public abstract void parse(File XMLFile);
}
