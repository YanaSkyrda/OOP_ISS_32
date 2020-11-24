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
            SAXHandler handler = new SAXHandler();
            parser.parse(fileName, handler);

            gemList = handler.getNotes();
            Collections.sort(gemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gemList;
    }
}
