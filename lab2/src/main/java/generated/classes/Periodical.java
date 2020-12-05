
package generated.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Objects;


/**
 * <p>Java class for Periodical complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Periodical">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{}Type"/>
 *         &lt;element name="monthly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="characteristics" type="{}Characteristics"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Periodical", propOrder = {
    "title",
    "type",
    "monthly",
    "characteristics"
})
public class Periodical {

    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Type type;
    protected boolean monthly;
    @XmlElement(required = true)
    protected Characteristics characteristics;
    @XmlAttribute(name = "id")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Type }
     *     
     */
    public Type getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Type }
     *     
     */
    public void setType(Type value) {
        this.type = value;
    }

    /**
     * Gets the value of the monthly property.
     * 
     */
    public boolean isMonthly() {
        return monthly;
    }

    /**
     * Sets the value of the monthly property.
     * 
     */
    public void setMonthly(boolean value) {
        this.monthly = value;
    }

    /**
     * Gets the value of the characteristics property.
     * 
     * @return
     *     possible object is
     *     {@link Characteristics }
     *     
     */
    public Characteristics getCharacteristics() {
        return characteristics;
    }

    /**
     * Sets the value of the characteristics property.
     * 
     * @param value
     *     allowed object is
     *     {@link Characteristics }
     *     
     */
    public void setCharacteristics(Characteristics value) {
        this.characteristics = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public String toString() {
        return "Periodical{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", monthly=" + monthly +
                ", characteristics=" + characteristics +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodical that = (Periodical) o;
        return monthly == that.monthly &&
                Objects.equals(title, that.title) &&
                type == that.type &&
                Objects.equals(characteristics, that.characteristics) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, monthly, characteristics, id);
    }
}
