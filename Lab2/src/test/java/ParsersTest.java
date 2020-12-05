import com.university.gem.Gem;
import com.university.gem.VisualParameters;
import com.university.parser.DOMParser;
import com.university.parser.SAXParser;
import com.university.parser.StAXParser;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsersTest {

    private static List<Gem> gemList;

    @BeforeClass
    public static void setUp(){
        gemList = new ArrayList<>();

        VisualParameters visualParameters1 = new VisualParameters("Yellow", 54, 5);
        VisualParameters visualParameters2 = new VisualParameters("White", 99, 15);
        VisualParameters visualParameters3 = new VisualParameters("Green", 10 ,10);
        VisualParameters visualParameters4 = new VisualParameters("Purple", 41, 11);

        Gem gem1 = new Gem("gem1", "Jade", true, "Spain", visualParameters1, 0.5);
        Gem gem2 = new Gem("gem2", "Opal", true, "Denmark", visualParameters2, 55);
        Gem gem3 = new Gem("gem3", "Beryl", false, "Sweden", visualParameters3, 22);
        Gem gem4 = new Gem("gem4", "Diamond", true, "Ukraine", visualParameters4, 32.1);

        gemList.add(gem3);
        gemList.add(gem4);
        gemList.add(gem1);
        gemList.add(gem2);
    }
    
    //purpose of the method (name)
    @Test
    public void domParser_TEST() throws ParserConfigurationException, SAXException, IOException {
        List<Gem> gemListActual = DOMParser.parseXML("Gem.xml");

        Assert.assertEquals(gemList.size(), gemListActual.size());

        for(int i = 0; i < gemList.size(); i++){
            Assert.assertEquals(gemList.get(i).getId(), gemListActual.get(i).getId());
            Assert.assertEquals(gemList.get(i).getName(), gemListActual.get(i).getName());
            Assert.assertEquals(gemList.get(i).getOrigin(), gemListActual.get(i).getOrigin());
            Assert.assertEquals(gemList.get(i).getValue(), gemListActual.get(i).getValue(), 0.1);
            Assert.assertEquals(gemList.get(i).getVisualParameters().getColor(), gemListActual.get(i).getVisualParameters().getColor());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getEdging(), gemListActual.get(i).getVisualParameters().getEdging());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getOpacity(), gemListActual.get(i).getVisualParameters().getOpacity());
        }
    }

    @Test
    public void saxParser_TEST() throws ParserConfigurationException, SAXException, IOException {
        List<Gem> gemListActual = SAXParser.parseXML("Gem.xml");

        Assert.assertEquals(gemList.size(), gemListActual.size());

        for(int i = 0; i < gemList.size(); i++){
            Assert.assertEquals(gemList.get(i).getId(), gemListActual.get(i).getId());
            Assert.assertEquals(gemList.get(i).getName(), gemListActual.get(i).getName());
            Assert.assertEquals(gemList.get(i).getOrigin(), gemListActual.get(i).getOrigin());
            Assert.assertEquals(gemList.get(i).getValue(), gemListActual.get(i).getValue(), 0.1);
            Assert.assertEquals(gemList.get(i).getVisualParameters().getColor(), gemListActual.get(i).getVisualParameters().getColor());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getEdging(), gemListActual.get(i).getVisualParameters().getEdging());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getOpacity(), gemListActual.get(i).getVisualParameters().getOpacity());
        }
    }

    @Test
    public void staxParser_TEST() throws IOException, XMLStreamException {
        List<Gem> gemListActual = StAXParser.parseXML("Gem.xml");

        Assert.assertEquals(gemList.size(), gemListActual.size());

        for(int i = 0; i < gemList.size(); i++){
            Assert.assertEquals(gemList.get(i).getId(), gemListActual.get(i).getId());
            Assert.assertEquals(gemList.get(i).getName(), gemListActual.get(i).getName());
            Assert.assertEquals(gemList.get(i).getOrigin(), gemListActual.get(i).getOrigin());
            Assert.assertEquals(gemList.get(i).getValue(), gemListActual.get(i).getValue(), 0.1);
            Assert.assertEquals(gemList.get(i).getVisualParameters().getColor(), gemListActual.get(i).getVisualParameters().getColor());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getEdging(), gemListActual.get(i).getVisualParameters().getEdging());
            Assert.assertEquals(gemList.get(i).getVisualParameters().getOpacity(), gemListActual.get(i).getVisualParameters().getOpacity());
        }
    }



}
