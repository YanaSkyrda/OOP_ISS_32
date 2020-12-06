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
    private List<Candy> listOfCandies = new ArrayList<>() ;
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
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
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
    public void endElement(String uri, String localName, String qName) {
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
            case XMLElements.NAME:
                getLastCandy().setName(content);
                break;
            case XMLElements.ID:
                getLastCandy().setId(content);
                break;
            case XMLElements.VALUE:
                Value value = new Value();
                getLastCandy().setValue(value);
                break;
            case XMLElements.COMPONENT:
                Component component = new Component();
                String amount = attributes.get(XMLElements.AMOUNT);

                component.setAmount(Integer.valueOf(amount));
                String typeOfMeasure = attributes.get(XMLElements.TYPEOFMEASURE);

                component.setTypeOfMeasure(typeOfMeasure);
                component.setName(content);
                getLastCandy().getComponents().add(component);
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