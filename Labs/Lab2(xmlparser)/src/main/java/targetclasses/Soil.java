//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.23 at 10:21:45 AM EET 
//


package targetclasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Soil.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Soil"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PODZOLIC"/&gt;
 *     &lt;enumeration value="SOIL"/&gt;
 *     &lt;enumeration value="SOD-PODZOLIC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Soil")
@XmlEnum
public enum Soil {

    PODZOLIC("PODZOLIC"),
    SOIL("SOIL"),
    @XmlEnumValue("SODPODZOLIC")
    SODPODZOLIC("SODPODZOLIC");
    private final String value;

    Soil(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Soil fromValue(String v) {
        for (Soil c: Soil.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
