
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
 *         &lt;element name="GetWarehouseByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Warehouse" minOccurs="0"/&gt;
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
    "getWarehouseByKeyResult"
})
@XmlRootElement(name = "GetWarehouseByKeyResponse")
public class GetWarehouseByKeyResponse {

    @XmlElement(name = "GetWarehouseByKeyResult")
    protected Warehouse getWarehouseByKeyResult;

    /**
     * Gets the value of the getWarehouseByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Warehouse }
     *     
     */
    public Warehouse getGetWarehouseByKeyResult() {
        return getWarehouseByKeyResult;
    }

    /**
     * Sets the value of the getWarehouseByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Warehouse }
     *     
     */
    public void setGetWarehouseByKeyResult(Warehouse value) {
        this.getWarehouseByKeyResult = value;
    }

}
