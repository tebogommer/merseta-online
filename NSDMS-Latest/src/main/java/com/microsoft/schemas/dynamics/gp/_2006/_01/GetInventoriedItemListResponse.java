
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
 *         &lt;element name="GetInventoriedItemListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoriedItemSummary" minOccurs="0"/&gt;
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
    "getInventoriedItemListResult"
})
@XmlRootElement(name = "GetInventoriedItemListResponse")
public class GetInventoriedItemListResponse {

    @XmlElement(name = "GetInventoriedItemListResult")
    protected ArrayOfInventoriedItemSummary getInventoriedItemListResult;

    /**
     * Gets the value of the getInventoriedItemListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoriedItemSummary }
     *     
     */
    public ArrayOfInventoriedItemSummary getGetInventoriedItemListResult() {
        return getInventoriedItemListResult;
    }

    /**
     * Sets the value of the getInventoriedItemListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoriedItemSummary }
     *     
     */
    public void setGetInventoriedItemListResult(ArrayOfInventoriedItemSummary value) {
        this.getInventoriedItemListResult = value;
    }

}
