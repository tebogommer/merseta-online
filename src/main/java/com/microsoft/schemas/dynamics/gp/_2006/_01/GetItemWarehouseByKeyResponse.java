
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
 *         &lt;element name="GetItemWarehouseByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemWarehouse" minOccurs="0"/&gt;
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
    "getItemWarehouseByKeyResult"
})
@XmlRootElement(name = "GetItemWarehouseByKeyResponse")
public class GetItemWarehouseByKeyResponse {

    @XmlElement(name = "GetItemWarehouseByKeyResult")
    protected ItemWarehouse getItemWarehouseByKeyResult;

    /**
     * Gets the value of the getItemWarehouseByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ItemWarehouse }
     *     
     */
    public ItemWarehouse getGetItemWarehouseByKeyResult() {
        return getItemWarehouseByKeyResult;
    }

    /**
     * Sets the value of the getItemWarehouseByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemWarehouse }
     *     
     */
    public void setGetItemWarehouseByKeyResult(ItemWarehouse value) {
        this.getItemWarehouseByKeyResult = value;
    }

}
