
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
 *         &lt;element name="GetCustomerAddressByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddress" minOccurs="0"/&gt;
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
    "getCustomerAddressByKeyResult"
})
@XmlRootElement(name = "GetCustomerAddressByKeyResponse")
public class GetCustomerAddressByKeyResponse {

    @XmlElement(name = "GetCustomerAddressByKeyResult")
    protected CustomerAddress getCustomerAddressByKeyResult;

    /**
     * Gets the value of the getCustomerAddressByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddress }
     *     
     */
    public CustomerAddress getGetCustomerAddressByKeyResult() {
        return getCustomerAddressByKeyResult;
    }

    /**
     * Sets the value of the getCustomerAddressByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddress }
     *     
     */
    public void setGetCustomerAddressByKeyResult(CustomerAddress value) {
        this.getCustomerAddressByKeyResult = value;
    }

}
