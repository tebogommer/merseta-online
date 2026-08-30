
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseChangedSalesDocumentKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseChangedSalesDocumentKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesDocumentKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseChangedSalesDocumentKeyCriteria", propOrder = {
    "salesDocumentKeyId"
})
@XmlSeeAlso({
    SalesOrderChangedKeyCriteria.class,
    SalesInvoiceChangedKeyCriteria.class
})
public class BaseChangedSalesDocumentKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "SalesDocumentKeyId")
    protected BetweenRestrictionOfString salesDocumentKeyId;

    /**
     * Gets the value of the salesDocumentKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getSalesDocumentKeyId() {
        return salesDocumentKeyId;
    }

    /**
     * Sets the value of the salesDocumentKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setSalesDocumentKeyId(BetweenRestrictionOfString value) {
        this.salesDocumentKeyId = value;
    }

}
