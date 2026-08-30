
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
 *         &lt;element name="GetChangedTaxDetailKeyListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfChangedTaxDetailKey" minOccurs="0"/&gt;
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
    "getChangedTaxDetailKeyListResult"
})
@XmlRootElement(name = "GetChangedTaxDetailKeyListResponse")
public class GetChangedTaxDetailKeyListResponse {

    @XmlElement(name = "GetChangedTaxDetailKeyListResult")
    protected ArrayOfChangedTaxDetailKey getChangedTaxDetailKeyListResult;

    /**
     * Gets the value of the getChangedTaxDetailKeyListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfChangedTaxDetailKey }
     *     
     */
    public ArrayOfChangedTaxDetailKey getGetChangedTaxDetailKeyListResult() {
        return getChangedTaxDetailKeyListResult;
    }

    /**
     * Sets the value of the getChangedTaxDetailKeyListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfChangedTaxDetailKey }
     *     
     */
    public void setGetChangedTaxDetailKeyListResult(ArrayOfChangedTaxDetailKey value) {
        this.getChangedTaxDetailKeyListResult = value;
    }

}
