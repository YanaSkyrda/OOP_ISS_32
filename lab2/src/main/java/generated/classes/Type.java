
package generated.classes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="NEWSPAPER"/>
 *     &lt;enumeration value="MAGAZINE"/>
 *     &lt;enumeration value="BOOKLET"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Type")
@XmlEnum
public enum Type {

    NEWSPAPER,
    MAGAZINE,
    BOOKLET;

    public String value() {
        return name();
    }

    public static Type fromValue(String v) {
        return valueOf(v);
    }


}
