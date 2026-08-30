
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBudgetPosted complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBudgetPosted"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectPostedBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CommittedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CommittedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="CommittedTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxPaidAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBudgetPosted", propOrder = {
    "billableAmount",
    "committedCost",
    "committedQuantity",
    "committedTaxAmount",
    "taxPaidAmount"
})
public class ProjectBudgetPosted
    extends ProjectPostedBase
{

    @XmlElement(name = "BillableAmount")
    protected MoneyAmount billableAmount;
    @XmlElement(name = "CommittedCost")
    protected MoneyAmount committedCost;
    @XmlElement(name = "CommittedQuantity")
    protected Quantity committedQuantity;
    @XmlElement(name = "CommittedTaxAmount")
    protected MoneyAmount committedTaxAmount;
    @XmlElement(name = "TaxPaidAmount")
    protected MoneyAmount taxPaidAmount;

    /**
     * Gets the value of the billableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBillableAmount() {
        return billableAmount;
    }

    /**
     * Sets the value of the billableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBillableAmount(MoneyAmount value) {
        this.billableAmount = value;
    }

    /**
     * Gets the value of the committedCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommittedCost() {
        return committedCost;
    }

    /**
     * Sets the value of the committedCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommittedCost(MoneyAmount value) {
        this.committedCost = value;
    }

    /**
     * Gets the value of the committedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getCommittedQuantity() {
        return committedQuantity;
    }

    /**
     * Sets the value of the committedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setCommittedQuantity(Quantity value) {
        this.committedQuantity = value;
    }

    /**
     * Gets the value of the committedTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommittedTaxAmount() {
        return committedTaxAmount;
    }

    /**
     * Sets the value of the committedTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommittedTaxAmount(MoneyAmount value) {
        this.committedTaxAmount = value;
    }

    /**
     * Gets the value of the taxPaidAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxPaidAmount() {
        return taxPaidAmount;
    }

    /**
     * Sets the value of the taxPaidAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxPaidAmount(MoneyAmount value) {
        this.taxPaidAmount = value;
    }

}
