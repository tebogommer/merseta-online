
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
 *         &lt;element name="GetItemByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Item" minOccurs="0"/&gt;
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
    "getItemByKeyResult"
})
@XmlRootElement(name = "GetItemByKeyResponse")
public class GetItemByKeyResponse {

    @XmlElement(name = "GetItemByKeyResult")
    protected Item getItemByKeyResult;

    /**
     * Gets the value of the getItemByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Item }
     *     
     */
    public Item getGetItemByKeyResult() {
        return getItemByKeyResult;
    }

    /**
     * Sets the value of the getItemByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Item }
     *     
     */
    public void setGetItemByKeyResult(Item value) {
        this.getItemByKeyResult = value;
    }

}
