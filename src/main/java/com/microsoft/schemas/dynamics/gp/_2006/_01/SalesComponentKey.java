
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesComponentKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesComponentKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
 *         &lt;element name="ComponentSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesComponentKey", propOrder = {
    "salesLineKey",
    "componentSequenceNumber"
})
public class SalesComponentKey
    extends TransactionKey
{

    @XmlElement(name = "SalesLineKey")
    protected SalesLineKey salesLineKey;
    @XmlElement(name = "ComponentSequenceNumber")
    protected int componentSequenceNumber;

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
     * Gets the value of the componentSequenceNumber property.
     * 
     */
    public int getComponentSequenceNumber() {
        return componentSequenceNumber;
    }

    /**
     * Sets the value of the componentSequenceNumber property.
     * 
     */
    public void setComponentSequenceNumber(int value) {
        this.componentSequenceNumber = value;
    }

}
