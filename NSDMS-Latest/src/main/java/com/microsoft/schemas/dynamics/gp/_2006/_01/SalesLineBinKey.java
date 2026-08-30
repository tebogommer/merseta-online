
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesLineBinKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesLineBinKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
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
@XmlType(name = "SalesLineBinKey", propOrder = {
    "salesLineKey",
    "sequenceNumber"
})
public class SalesLineBinKey
    extends TransactionKey
{

    @XmlElement(name = "SalesLineKey")
    protected SalesLineKey salesLineKey;
    @XmlElement(name = "SequenceNumber")
    protected int sequenceNumber;

    /**
     * Gets the value of the salesLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getSalesLineKey() {
        return salesLineKey;
    }

    /**
     * Sets the value of the salesLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setSalesLineKey(SalesLineKey value) {
        this.salesLineKey = value;
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
