//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.11.23 at 10:21:45 AM EET 
//


package targetclasses;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Country.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Country"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="UKRAINE"/&gt;
 *     &lt;enumeration value="GERMANY"/&gt;
 *     &lt;enumeration value="NETHERLANDS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "Country")
@XmlEnum
public enum Country {

    UKRAINE,
    GERMANY,
    NETHERLANDS;

    public String value() {
        return name();
    }

    public static Country fromValue(String v) {
        return valueOf(v);
    }

}
