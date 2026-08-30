
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
 *         &lt;element name="GetChangedPriceLevelKeyListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfChangedPriceLevelKey" minOccurs="0"/&gt;
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
    "getChangedPriceLevelKeyListResult"
})
@XmlRootElement(name = "GetChangedPriceLevelKeyListResponse")
public class GetChangedPriceLevelKeyListResponse {

    @XmlElement(name = "GetChangedPriceLevelKeyListResult")
    protected ArrayOfChangedPriceLevelKey getChangedPriceLevelKeyListResult;

    /**
     * Gets the value of the getChangedPriceLevelKeyListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChangedPriceLevelKey }
     *     
     */
    public ArrayOfChangedPriceLevelKey getGetChangedPriceLevelKeyListResult() {
        return getChangedPriceLevelKeyListResult;
    }

    /**
     * Sets the value of the getChangedPriceLevelKeyListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChangedPriceLevelKey }
     *     
     */
    public void setGetChangedPriceLevelKeyListResult(ArrayOfChangedPriceLevelKey value) {
        this.getChangedPriceLevelKeyListResult = value;
    }

}
