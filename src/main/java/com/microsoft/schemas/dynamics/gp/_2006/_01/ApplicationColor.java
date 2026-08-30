
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationColor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationColor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ColorCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationColorCode"/&gt;
 *         &lt;element name="ColorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationColor", propOrder = {
    "colorCode",
    "colorName"
})
public class ApplicationColor {

    @XmlElement(name = "ColorCode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ApplicationColorCode colorCode;
    @XmlElement(name = "ColorName")
    protected String colorName;

    /**
     * Gets the value of the colorCode property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationColorCode }
     *     
     */
    public ApplicationColorCode getColorCode() {
        return colorCode;
    }

    /**
     * Sets the value of the colorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationColorCode }
     *     
     */
    public void setColorCode(ApplicationColorCode value) {
        this.colorCode = value;
    }

    /**
     * Gets the value of the colorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorName() {
        return colorName;
    }

    /**
     * Sets the value of the colorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorName(String value) {
        this.colorName = value;
    }

}
