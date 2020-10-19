package org.example.util;

public enum XML {
    // elements names
    MEDICINE("Medicine"), DRUG("Drug"),

    //inner tags
    NAME("Name"), PHARM("Pharm"), GROUP("Group"),
    ANALOGS("Analogs"), VERSION("Version"), TYPE("Type"),

    CERTIFICATE("Certificate"),
    //attributes
    NUMBER("number"), CREATED("created"), TILL("till"), ORGANIZATION("organization"),

    PACKAGE("Package"),
    //attributes
    PACKTYPE("packtype"), AMOUNT("amount"), PRICE("price"),

    DOSAGE("Dosage"),
    //attributes
    DOSAGEAMOUNT("dosageAmount"), PERIODICITY("periodicity");


    private final String value;

    XML(String value) {
        this.value = value;
    }

    /**
     * Determines if a name is equal to the string value wrapped by this enum element.<br/>
     * If a SAX/StAX parser make all names of elements and attributes interned you can use
     * <pre>return value == name;</pre> instead <pre>return value.equals(name);</pre>
     * @param name string to compare with value.
     * @return value.equals(name)
     */
    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public String value() {
        return value;
    }
}
