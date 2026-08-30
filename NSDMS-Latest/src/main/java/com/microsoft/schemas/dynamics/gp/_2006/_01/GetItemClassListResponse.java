
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
 *         &lt;element name="GetItemClassListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfItemClass" minOccurs="0"/&gt;
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
    "getItemClassListResult"
})
@XmlRootElement(name = "GetItemClassListResponse")
public class GetItemClassListResponse {

    @XmlElement(name = "GetItemClassListResult")
    protected ArrayOfItemClass getItemClassListResult;

    /**
     * Gets the value of the getItemClassListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfItemClass }
     *     
     */
    public ArrayOfItemClass getGetItemClassListResult() {
        return getItemClassListResult;
    }

    /**
     * Sets the value of the getItemClassListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfItemClass }
     *     
     */
    public void setGetItemClassListResult(ArrayOfItemClass value) {
        this.getItemClassListResult = value;
    }

}
