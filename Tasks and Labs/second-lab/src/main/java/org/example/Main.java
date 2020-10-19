package org.example;


import org.example.controllers.DOMController;
import org.example.controllers.SAXController;
import org.example.controllers.STAXController;
import org.example.entity.Medicine;
import org.example.util.Sorter;

public class Main {
        private static final String OUTPUT = "Output ==> ";
        public static void main(final String[] args) throws Exception{

            String xmlFileName = args[0];
            System.out.println("Input ==> " + xmlFileName);

            //SAX
            SAXController saxController = new SAXController(xmlFileName);
            saxController.parse(true);
            Medicine drugList = saxController.getDrugList();

            Sorter.sortDrugsByPeriodicity(drugList);

            String outputFileName = "output.sax.xml";
            DOMController.saveToXML(drugList, outputFileName);
            System.out.println(OUTPUT + outputFileName);

            //DOM
            DOMController domConroller = new DOMController(xmlFileName);
            domConroller.parse(true);
            drugList = domConroller.getDrugs();

            Sorter.sortDrugsByNames(drugList);

            outputFileName = "output.dom.xml";
            DOMController.saveToXML(drugList, outputFileName);
            System.out.println(OUTPUT + outputFileName);

            //STAX
            STAXController staxController = new STAXController(xmlFileName);
            staxController.parse();
            drugList = staxController.getDrugList();

            Sorter.sortDrugsByPharms(drugList);

            outputFileName = "output.stax.xml";
            DOMController.saveToXML(drugList, outputFileName);
            System.out.println(OUTPUT + outputFileName);
        }

}
