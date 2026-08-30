
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseChangedSalesDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseChangedSalesDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedBusinessObjectGreatPlainsKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesDocumentTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentTypeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseChangedSalesDocument", propOrder = {
    "salesDocumentTypeKey"
})
@XmlSeeAlso({
    ChangedSalesOrderKey.class,
    ChangedSalesInvoiceKey.class
})
public class BaseChangedSalesDocument
    extends ChangedBusinessObjectGreatPlainsKey
{

    @XmlElement(name = "SalesDocumentTypeKey")
    protected SalesDocumentTypeKey salesDocumentTypeKey;

    /**
     * Gets the value of the salesDocumentTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesDocumentTypeKey }
     *     
     */
    public SalesDocumentTypeKey getSalesDocumentTypeKey() {
        return salesDocumentTypeKey;
    }

    /**
     * Sets the value of the salesDocumentTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesDocumentTypeKey }
     *     
     */
    public void setSalesDocumentTypeKey(SalesDocumentTypeKey value) {
        this.salesDocumentTypeKey = value;
    }

}
