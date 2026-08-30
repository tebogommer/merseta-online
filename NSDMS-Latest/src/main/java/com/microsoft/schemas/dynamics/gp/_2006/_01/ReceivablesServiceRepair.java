
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesServiceRepair complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesServiceRepair"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDebitDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CommissionBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionBasedOn"/&gt;
 *         &lt;element name="CommissionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Commissions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReceivablesCommission" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesServiceRepair", propOrder = {
    "commissionBasedOn",
    "commissionAmount",
    "commissions"
})
public class ReceivablesServiceRepair
    extends ReceivablesDebitDocument
{

    @XmlElement(name = "CommissionBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CommissionBasedOn commissionBasedOn;
    @XmlElement(name = "CommissionAmount")
    protected MoneyAmount commissionAmount;
    @XmlElement(name = "Commissions")
    protected ArrayOfReceivablesCommission commissions;

    /**
     * Gets the value of the commissionBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionBasedOn }
     *     
     */
    public CommissionBasedOn getCommissionBasedOn() {
        return commissionBasedOn;
    }

    /**
     * Sets the value of the commissionBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionBasedOn }
     *     
     */
    public void setCommissionBasedOn(CommissionBasedOn value) {
        this.commissionBasedOn = value;
    }

    /**
     * Gets the value of the commissionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * Sets the value of the commissionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionAmount(MoneyAmount value) {
        this.commissionAmount = value;
    }

    /**
     * Gets the value of the commissions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReceivablesCommission }
     *     
     */
    public ArrayOfReceivablesCommission getCommissions() {
        return commissions;
    }

    /**
     * Sets the value of the commissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReceivablesCommission }
     *     
     */
    public void setCommissions(ArrayOfReceivablesCommission value) {
        this.commissions = value;
    }

}
