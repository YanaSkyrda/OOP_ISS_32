package com.university.parser;

import com.university.gem.Gem;
import com.university.gem.VisualParameters;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler {

    private final List<Gem> gemList;
    private Gem gem;
    private VisualParameters visualParameters;
    private int currentOption;

    public SAXHandler() {
        gemList = new ArrayList<>();
        currentOption = 0;
    }

    public List<Gem> getNotes() {
        return gemList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs) {

        if (qName.equals("gem")) {
            gem = new Gem();
            visualParameters =  new VisualParameters();
            gem.setId(attrs.getValue(0));
        }
        switch (qName) {
            case "name" -> currentOption = 1;
            case "preciousness" -> currentOption = 2;
            case "origin" -> currentOption = 3;
            case "color" -> currentOption = 4;
            case "opacity" -> currentOption = 5;
            case "edging" -> currentOption = 6;
            case "value" -> currentOption = 7;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {

        if (qName.equals("gem")) {
            gem.setVisualParameters(visualParameters);
            gemList.add(gem);
        }
    }

    @Override
    public void characters(char[] ch, int offset, int length) {

        String s = new String(ch, offset, length);

        try {
            switch (currentOption) {
                case 1 -> gem.setName(s);
                case 2 -> gem.setPreciousness(Boolean.parseBoolean(s));
                case 3 -> gem.setOrigin(s);
                case 4 -> visualParameters.setColor(s);
                case 5 -> visualParameters.setOpacity(Integer.parseInt(s));
                case 6 -> visualParameters.setEdging(Integer.parseInt(s));
                case 7 -> gem.setValue(Double.parseDouble(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        currentOption = 0;
    }
}
