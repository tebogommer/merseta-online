
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
 *         &lt;element name="GetChangedInternetAddressKeyListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfChangedInternetAddressKey" minOccurs="0"/&gt;
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
    "getChangedInternetAddressKeyListResult"
})
@XmlRootElement(name = "GetChangedInternetAddressKeyListResponse")
public class GetChangedInternetAddressKeyListResponse {

    @XmlElement(name = "GetChangedInternetAddressKeyListResult")
    protected ArrayOfChangedInternetAddressKey getChangedInternetAddressKeyListResult;

    /**
     * Gets the value of the getChangedInternetAddressKeyListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChangedInternetAddressKey }
     *     
     */
    public ArrayOfChangedInternetAddressKey getGetChangedInternetAddressKeyListResult() {
        return getChangedInternetAddressKeyListResult;
    }

    /**
     * Sets the value of the getChangedInternetAddressKeyListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChangedInternetAddressKey }
     *     
     */
    public void setGetChangedInternetAddressKeyListResult(ArrayOfChangedInternetAddressKey value) {
        this.getChangedInternetAddressKeyListResult = value;
    }

}
