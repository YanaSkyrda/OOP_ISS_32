package parsers;

import lombok.Setter;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import targetclasses.*;

import java.math.BigInteger;
import java.util.List;

public class OrangeryHandler extends DefaultHandler {
    private static final String FLOWER = "flower";
    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String SOIL = "soil";
    private static final String ORIGIN = "origin";
    private static final String VISUALPARAMETERS = "visualParameters";
    private static final String GROWINGTIPS = "growingTips";
    private static final String MULTIPLYING = "multiplying";

    private static final String TEMPERATURE = "temperature";
    private static final String LIGHTLOVING = "lightloving";
    private static final String WATERING = "watering";

    private static final String STALKCOLOR = "stalkColor";
    private static final String LEAVESCOLOR = "leavesColor";
    private static final String AVERAGESIZE = "averageSize";

    private Orangery orangery = new Orangery();
    @Setter
    private String elementValue;

    @Override
    public void characters(char[] ch, int start, int length){
        elementValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() {
        orangery = new Orangery();
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr){
        switch (qName) {
            case FLOWER:
                Flower flower = new Flower();
                flower.setCode(new BigInteger(attr.getValue(0)));
                orangery.getFlower().add(flower);
                break;
            case VISUALPARAMETERS:
                lastFlower().setVisualParameters(new VisualParameters());
                break;
            case GROWINGTIPS:
                lastFlower().setGrowingTips(new GrowingTips());
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        setField(qName);
    }

    private Flower lastFlower() {
        List<Flower> flowers = orangery.getFlower();
        int indexOfLast = flowers.size() - 1;
        return flowers.get(indexOfLast);
    }

    public Orangery getOrangery() {
        return orangery;
    }

    void setField(String qName) {
        switch (qName) {
            case CODE:
                lastFlower().setCode(new BigInteger(String.valueOf(Long.parseLong(qName))));
            case NAME:
                lastFlower().setName(elementValue);
                break;
            case SOIL:
                lastFlower().setSoil(Soil.valueOf(elementValue));
                break;
            case ORIGIN:
                lastFlower().setOrigin(Country.valueOf(elementValue));
                break;
            case MULTIPLYING:
                lastFlower().setMultiplying(Multiplying.valueOf(elementValue));
                break;
            case TEMPERATURE:
                lastFlower().getGrowingTips()
                        .setTemperature(BigInteger.valueOf(Long.parseLong(elementValue)));
                break;
            case LIGHTLOVING:
                lastFlower().getGrowingTips().setLightloving(Boolean.parseBoolean(elementValue));
                break;
            case WATERING:
                lastFlower().getGrowingTips().setWatering(Integer.parseInt(elementValue));
                break;
            case STALKCOLOR:
                lastFlower().getVisualParameters().setStalkColor(StalkColor.valueOf(elementValue));
                break;
            case LEAVESCOLOR:
                lastFlower().getVisualParameters().setLeavesColor(LeavesColor.valueOf(elementValue));
                break;
            case AVERAGESIZE:
                lastFlower().getVisualParameters().setAverageSize(Integer.parseInt(elementValue));
                break;
        }
    }

    void setField(String qName, String attribute){
        switch (qName) {
            case FLOWER:
                Flower flower = new Flower();
                flower.setCode(new BigInteger(attribute));
                orangery.getFlower().add(flower);
                break;
            case VISUALPARAMETERS:
                lastFlower().setVisualParameters(new VisualParameters());
                break;
            case GROWINGTIPS:
                lastFlower().setGrowingTips(new GrowingTips());
                break;
            default:
                setField(qName);
                break;
        }
    }
}
