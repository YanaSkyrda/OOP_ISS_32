package parsers.test.utils;

import generated.classes.Paper;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.xml.sax.SAXException;
import parsers.DOMParser;
import parsers.SAXParserHandler;
import parsers.StAXParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class ParserTestArgumentsProvider implements ArgumentsProvider {
    private String filename = "src/test/resources/test.xml";

    Paper getDOMParse(){
        DOMParser domParser = new DOMParser();
        domParser.parseXML(filename);
        return domParser.getObject();
    }

    Paper getSAXParse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXParserHandler saxParserHandler = new SAXParserHandler();
        saxParser.parse(new File(filename),saxParserHandler);
        return saxParserHandler.getObject();
    }

    Paper getStAXParse(){
        StAXParser stAXParser = new StAXParser();
        stAXParser.parseXML(filename);
        return stAXParser.getObject();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(getDOMParse(),
                getSAXParse(),
                getStAXParse()).map(Arguments::of);
    }
}
