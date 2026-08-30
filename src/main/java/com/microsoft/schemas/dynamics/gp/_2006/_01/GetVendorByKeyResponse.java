
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
 *         &lt;element name="GetVendorByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Vendor" minOccurs="0"/&gt;
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
    "getVendorByKeyResult"
})
@XmlRootElement(name = "GetVendorByKeyResponse")
public class GetVendorByKeyResponse {

    @XmlElement(name = "GetVendorByKeyResult")
    protected Vendor getVendorByKeyResult;

    /**
     * Gets the value of the getVendorByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link Vendor }
     *     
     */
    public Vendor getGetVendorByKeyResult() {
        return getVendorByKeyResult;
    }

    /**
     * Sets the value of the getVendorByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vendor }
     *     
     */
    public void setGetVendorByKeyResult(Vendor value) {
        this.getVendorByKeyResult = value;
    }

}
