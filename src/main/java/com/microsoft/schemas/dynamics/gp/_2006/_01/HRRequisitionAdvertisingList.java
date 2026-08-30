
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRRequisitionAdvertisingList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HRRequisitionAdvertisingList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Line1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Line2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Line3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Line4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Line5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HRRequisitionAdvertisingList", propOrder = {
    "line1",
    "line2",
    "line3",
    "line4",
    "line5"
})
public class HRRequisitionAdvertisingList {

    @XmlElement(name = "Line1")
    protected String line1;
    @XmlElement(name = "Line2")
    protected String line2;
    @XmlElement(name = "Line3")
    protected String line3;
    @XmlElement(name = "Line4")
    protected String line4;
    @XmlElement(name = "Line5")
    protected String line5;

    /**
     * Gets the value of the line1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine1() {
        return line1;
    }

    /**
     * Sets the value of the line1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine1(String value) {
        this.line1 = value;
    }

    /**
     * Gets the value of the line2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine2() {
        return line2;
    }

    /**
     * Sets the value of the line2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine2(String value) {
        this.line2 = value;
    }

    /**
     * Gets the value of the line3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine3() {
        return line3;
    }

    /**
     * Sets the value of the line3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine3(String value) {
        this.line3 = value;
    }

    /**
     * Gets the value of the line4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine4() {
        return line4;
    }

    /**
     * Sets the value of the line4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine4(String value) {
        this.line4 = value;
    }

    /**
     * Gets the value of the line5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine5() {
        return line5;
    }

    /**
     * Sets the value of the line5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine5(String value) {
        this.line5 = value;
    }

}
