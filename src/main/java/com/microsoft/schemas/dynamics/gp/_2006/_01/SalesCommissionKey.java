
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesCommissionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesCommissionKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesCommissionKey", propOrder = {
    "salesDocumentKey",
    "sequenceNumber"
})
public class SalesCommissionKey
    extends TransactionKey
{

    @XmlElement(name = "SalesDocumentKey")
    protected SalesDocumentKey salesDocumentKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

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
     * Gets the value of the sequenceNumber property.
     * 
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     */
    public void setSequenceNumber(int value) {
        this.sequenceNumber = value;
    }

}
