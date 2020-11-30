package com.university.parser;

import com.university.gem.Gem;
import com.university.validator.Validator;

import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SAXParser {

    public static List<Gem> parseXML(String fileName){

        if(!Validator.validateDocument(fileName)){
            return new ArrayList<>();
        }

        List<Gem> gemList = new ArrayList<>();
        try {
            javax.xml.parsers.SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            GemHandler handler = new GemHandler();
            parser.parse(fileName, handler);

            gemList = handler.obtainResult();
            Collections.sort(gemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gemList;
    }
}
