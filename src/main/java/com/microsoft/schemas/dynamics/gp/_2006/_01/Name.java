
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Name complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Name"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Family" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Given" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Middle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Preferred" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Alternate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Suffix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Name", propOrder = {
    "family",
    "given",
    "middle",
    "preferred",
    "alternate",
    "suffix"
})
public class Name {

    @XmlElement(name = "Family")
    protected String family;
    @XmlElement(name = "Given")
    protected String given;
    @XmlElement(name = "Middle")
    protected String middle;
    @XmlElement(name = "Preferred")
    protected String preferred;
    @XmlElement(name = "Alternate")
    protected String alternate;
    @XmlElement(name = "Suffix")
    protected String suffix;

    /**
     * Gets the value of the family property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamily() {
        return family;
    }

    /**
     * Sets the value of the family property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamily(String value) {
        this.family = value;
    }

    /**
     * Gets the value of the given property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiven() {
        return given;
    }

    /**
     * Sets the value of the given property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiven(String value) {
        this.given = value;
    }

    /**
     * Gets the value of the middle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddle() {
        return middle;
    }

    /**
     * Sets the value of the middle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddle(String value) {
        this.middle = value;
    }

    /**
     * Gets the value of the preferred property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferred() {
        return preferred;
    }

    /**
     * Sets the value of the preferred property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferred(String value) {
        this.preferred = value;
    }

    /**
     * Gets the value of the alternate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlternate() {
        return alternate;
    }

    /**
     * Sets the value of the alternate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlternate(String value) {
        this.alternate = value;
    }

    /**
     * Gets the value of the suffix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * Sets the value of the suffix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuffix(String value) {
        this.suffix = value;
    }

}
