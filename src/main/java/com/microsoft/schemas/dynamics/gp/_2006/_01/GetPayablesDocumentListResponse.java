
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
 *         &lt;element name="GetPayablesDocumentListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPayablesDocumentSummary" minOccurs="0"/&gt;
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
    "getPayablesDocumentListResult"
})
@XmlRootElement(name = "GetPayablesDocumentListResponse")
public class GetPayablesDocumentListResponse {

    @XmlElement(name = "GetPayablesDocumentListResult")
    protected ArrayOfPayablesDocumentSummary getPayablesDocumentListResult;

    /**
     * Gets the value of the getPayablesDocumentListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPayablesDocumentSummary }
     *     
     */
    public ArrayOfPayablesDocumentSummary getGetPayablesDocumentListResult() {
        return getPayablesDocumentListResult;
    }

    /**
     * Sets the value of the getPayablesDocumentListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPayablesDocumentSummary }
     *     
     */
    public void setGetPayablesDocumentListResult(ArrayOfPayablesDocumentSummary value) {
        this.getPayablesDocumentListResult = value;
    }

}
