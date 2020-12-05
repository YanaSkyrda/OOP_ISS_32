
package generated.classes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Characteristics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Characteristics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="colorful" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="pageAmount" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="glossy" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="subscribable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Characteristics", propOrder = {
    "colorful",
    "pageAmount",
    "glossy",
    "subscribable"
})
public class Characteristics {

    protected boolean colorful;
    @XmlSchemaType(name = "unsignedInt")
    protected long pageAmount;
    protected Boolean glossy;
    protected Boolean subscribable;

    /**
     * Gets the value of the colorful property.
     * 
     */
    public boolean isColorful() {
        return colorful;
    }

    /**
     * Sets the value of the colorful property.
     * 
     */
    public void setColorful(boolean value) {
        this.colorful = value;
    }

    /**
     * Gets the value of the pageAmount property.
     * 
     */
    public long getPageAmount() {
        return pageAmount;
    }

    /**
     * Sets the value of the pageAmount property.
     * 
     */
    public void setPageAmount(long value) {
        this.pageAmount = value;
    }

    /**
     * Gets the value of the glossy property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGlossy() {
        return glossy;
    }

    /**
     * Sets the value of the glossy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGlossy(Boolean value) {
        this.glossy = value;
    }

    /**
     * Gets the value of the subscribable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSubscribable() {
        return subscribable;
    }

    /**
     * Sets the value of the subscribable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSubscribable(Boolean value) {
        this.subscribable = value;
    }

    @Override
    public String toString() {
        return "Characteristics{" +
                "colorful=" + colorful +
                ", pageAmount=" + pageAmount +
                ((glossy != null) ? ", glossy=" + glossy : "")+
                ((subscribable != null) ? ", subscribable=" + subscribable : "") +
                '}';
    }
}
