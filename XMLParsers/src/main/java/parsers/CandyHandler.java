package parsers;

import model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CandyHandler extends DefaultHandler {

    private String elementValue;
    private List<Candy> listOfCandies ;
    public List<Candy> getCandies() {
        return listOfCandies;
    }

    @Override
    public void startDocument() throws SAXException {
        listOfCandies = new ArrayList<>();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    public String getName() {
        return XMLElements.CANDY;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case XMLElements.CANDY:
                Candy candy = new Candy();
                listOfCandies.add(candy);
                break;
            case XMLElements.VALUE:
                Value value = new Value();
                getLastCandy().setValue(value);
                break;
            case XMLElements.COMPONENT:
                Component ingredient = new Component();
                ingredient.setAmount(Integer.valueOf(attributes.getValue("amount")));
                ingredient.setTypeOfMeasure(attributes.getValue("typeOfMeasure"));
                getLastCandy().getComponents().add(ingredient);
                break;
        }
    }

    private Candy getLastCandy() {
        return listOfCandies.get(listOfCandies.size() - 1);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case XMLElements.ID:
                getLastCandy().setId(elementValue);
                break;
            case XMLElements.NAME:
                getLastCandy().setName(elementValue);
                break;
            case XMLElements.ENERGY:
                getLastCandy().setEnergy(Integer.valueOf(elementValue));
                break;
            case XMLElements.TYPE:
                getLastCandy().getTypes().add(Types.valueOfLabel(elementValue));
                break;
            case XMLElements.PROTEINS:
                getLastCandy().getValue().setProteins(Integer.valueOf(elementValue));
                break;
            case XMLElements.FATS:
                getLastCandy().getValue().setFats(Integer.valueOf(elementValue));
                break;
            case XMLElements.CARBONHYDRATES:
                getLastCandy().getValue().setCarbohydrates(Integer.valueOf(elementValue));
                break;
            case XMLElements.COMPONENT:
                getLastCandy().getComponents().get(getLastCandy().getComponents().size() - 1).setName(elementValue);
                break;

        }
    }

    public void setField(String qName, String content, Map<String ,String> attributes) {
        switch (qName) {
            case XMLElements.CANDY:
                Candy candy = new Candy();
                listOfCandies.add(candy);
                break;
            case XMLElements.VALUE:
                Value value = new Value();
                getLastCandy().setValue(value);
                break;
            case XMLElements.COMPONENT:
                Component ingredient = new Component();
                String amount = attributes.get("amount");
                ingredient.setAmount(Integer.valueOf(amount));
                String measurment = attributes.get("measurment");
                ingredient.setTypeOfMeasure(measurment);
                ingredient.setName(content);
                getLastCandy().getComponents().add(ingredient);
                break;
            case XMLElements.ID:
                getLastCandy().setId(content);
                break;
            case XMLElements.NAME:
                getLastCandy().setName(content);
                break;
            case XMLElements.ENERGY:
                getLastCandy().setEnergy(Integer.valueOf(content));
                break;
            case XMLElements.TYPE:
                getLastCandy().getTypes().add(Types.valueOfLabel(content));
                break;
            case XMLElements.PROTEINS:
                getLastCandy().getValue().setProteins(Integer.valueOf(content));
                break;
            case XMLElements.FATS:
                getLastCandy().getValue().setFats(Integer.valueOf(content));
                break;
            case XMLElements.CARBONHYDRATES:
                getLastCandy().getValue().setCarbohydrates(Integer.valueOf(content));
                break;
        }
    }
}