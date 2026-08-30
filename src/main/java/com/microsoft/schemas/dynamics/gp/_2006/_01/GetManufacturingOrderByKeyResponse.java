
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetManufacturingOrderByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrder" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getManufacturingOrderByKeyResult"
})
@XmlRootElement(name = "GetManufacturingOrderByKeyResponse")
public class GetManufacturingOrderByKeyResponse {

    @XmlElement(name = "GetManufacturingOrderByKeyResult")
    protected ManufacturingOrder getManufacturingOrderByKeyResult;

    /**
     * Gets the value of the getManufacturingOrderByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrder }
     *     
     */
    public ManufacturingOrder getGetManufacturingOrderByKeyResult() {
        return getManufacturingOrderByKeyResult;
    }

    /**
     * Sets the value of the getManufacturingOrderByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrder }
     *     
     */
    public void setGetManufacturingOrderByKeyResult(ManufacturingOrder value) {
        this.getManufacturingOrderByKeyResult = value;
    }

}
