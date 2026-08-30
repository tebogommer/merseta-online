
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalespersonHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalespersonHistory"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonHistoryKey" minOccurs="0"/&gt;
 *         &lt;element name="CommissionSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionSummary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalespersonHistory", propOrder = {
    "key",
    "commissionSummary"
})
public class SalespersonHistory
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalespersonHistoryKey key;
    @XmlElement(name = "CommissionSummary")
    protected CommissionSummary commissionSummary;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonHistoryKey }
     *     
     */
    public SalespersonHistoryKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonHistoryKey }
     *     
     */
    public void setKey(SalespersonHistoryKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the commissionSummary property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionSummary }
     *     
     */
    public CommissionSummary getCommissionSummary() {
        return commissionSummary;
    }

    /**
     * Sets the value of the commissionSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionSummary }
     *     
     */
    public void setCommissionSummary(CommissionSummary value) {
        this.commissionSummary = value;
    }

}
