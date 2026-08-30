
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTrackingNumberKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesTrackingNumberKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTrackingNumberKey", propOrder = {
    "salesDocumentKey",
    "id"
})
public class SalesTrackingNumberKey
    extends TransactionKey
{

    @XmlElement(name = "SalesDocumentKey")
    protected SalesDocumentKey salesDocumentKey;
    @XmlElement(name = "Id")
    protected String id;

    /**
     * Gets the value of the salesDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentKey }
     *     
     */
    public SalesDocumentKey getSalesDocumentKey() {
        return salesDocumentKey;
    }

    /**
     * Sets the value of the salesDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentKey }
     *     
     */
    public void setSalesDocumentKey(SalesDocumentKey value) {
        this.salesDocumentKey = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
