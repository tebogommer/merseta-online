
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
 *         &lt;element name="GetInventoryVarianceByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryVariance" minOccurs="0"/&gt;
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
    "getInventoryVarianceByKeyResult"
})
@XmlRootElement(name = "GetInventoryVarianceByKeyResponse")
public class GetInventoryVarianceByKeyResponse {

    @XmlElement(name = "GetInventoryVarianceByKeyResult")
    protected InventoryVariance getInventoryVarianceByKeyResult;

    /**
     * Gets the value of the getInventoryVarianceByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryVariance }
     *     
     */
    public InventoryVariance getGetInventoryVarianceByKeyResult() {
        return getInventoryVarianceByKeyResult;
    }

    /**
     * Sets the value of the getInventoryVarianceByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryVariance }
     *     
     */
    public void setGetInventoryVarianceByKeyResult(InventoryVariance value) {
        this.getInventoryVarianceByKeyResult = value;
    }

}
