
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesDistributionKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesDistributionKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PayablesDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentKey" minOccurs="0"/&gt;
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
@XmlType(name = "PayablesDistributionKey", propOrder = {
    "payablesDocumentKey",
    "sequenceNumber"
})
public class PayablesDistributionKey
    extends TransactionKey
{

    @XmlElement(name = "PayablesDocumentKey")
    protected PayablesDocumentKey payablesDocumentKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the payablesDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesDocumentKey }
     *     
     */
    public PayablesDocumentKey getPayablesDocumentKey() {
        return payablesDocumentKey;
    }

    /**
     * Sets the value of the payablesDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesDocumentKey }
     *     
     */
    public void setPayablesDocumentKey(PayablesDocumentKey value) {
        this.payablesDocumentKey = value;
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
