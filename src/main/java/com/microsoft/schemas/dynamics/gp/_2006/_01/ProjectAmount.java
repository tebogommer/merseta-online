
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectAmount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectAmount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="AccruedRevenueAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RecognizedRevenueAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BillingsInExcessOfEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="EarningsInExcessOfBillingsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BillingAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetainageAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectAmount", propOrder = {
    "quantity",
    "totalCost",
    "overheadAmount",
    "profitAmount",
    "taxAmount",
    "accruedRevenueAmount",
    "recognizedRevenueAmount",
    "billingsInExcessOfEarningsAmount",
    "earningsInExcessOfBillingsAmount",
    "billingAmount",
    "discountAmount",
    "salesTaxAmount",
    "retainageAmount"
})
@XmlSeeAlso({
    ProjectContractActual.class,
    ProjectUnpostedBase.class,
    ProjectPostedAmount.class,
    ProjectPostedBase.class,
    ProjectActual.class
})
public abstract class ProjectAmount {

    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "OverheadAmount")
    protected MoneyAmount overheadAmount;
    @XmlElement(name = "ProfitAmount")
    protected MoneyAmount profitAmount;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "AccruedRevenueAmount")
    protected MoneyAmount accruedRevenueAmount;
    @XmlElement(name = "RecognizedRevenueAmount")
    protected MoneyAmount recognizedRevenueAmount;
    @XmlElement(name = "BillingsInExcessOfEarningsAmount")
    protected MoneyAmount billingsInExcessOfEarningsAmount;
    @XmlElement(name = "EarningsInExcessOfBillingsAmount")
    protected MoneyAmount earningsInExcessOfBillingsAmount;
    @XmlElement(name = "BillingAmount")
    protected MoneyAmount billingAmount;
    @XmlElement(name = "DiscountAmount")
    protected MoneyAmount discountAmount;
    @XmlElement(name = "SalesTaxAmount")
    protected MoneyAmount salesTaxAmount;
    @XmlElement(name = "RetainageAmount")
    protected MoneyAmount retainageAmount;

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the overheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getOverheadAmount() {
        return overheadAmount;
    }

    /**
     * Sets the value of the overheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setOverheadAmount(MoneyAmount value) {
        this.overheadAmount = value;
    }

    /**
     * Gets the value of the profitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProfitAmount() {
        return profitAmount;
    }

    /**
     * Sets the value of the profitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProfitAmount(MoneyAmount value) {
        this.profitAmount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxAmount(MoneyAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the accruedRevenueAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAccruedRevenueAmount() {
        return accruedRevenueAmount;
    }

    /**
     * Sets the value of the accruedRevenueAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAccruedRevenueAmount(MoneyAmount value) {
        this.accruedRevenueAmount = value;
    }

    /**
     * Gets the value of the recognizedRevenueAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRecognizedRevenueAmount() {
        return recognizedRevenueAmount;
    }

    /**
     * Sets the value of the recognizedRevenueAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRecognizedRevenueAmount(MoneyAmount value) {
        this.recognizedRevenueAmount = value;
    }

    /**
     * Gets the value of the billingsInExcessOfEarningsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBillingsInExcessOfEarningsAmount() {
        return billingsInExcessOfEarningsAmount;
    }

    /**
     * Sets the value of the billingsInExcessOfEarningsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBillingsInExcessOfEarningsAmount(MoneyAmount value) {
        this.billingsInExcessOfEarningsAmount = value;
    }

    /**
     * Gets the value of the earningsInExcessOfBillingsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getEarningsInExcessOfBillingsAmount() {
        return earningsInExcessOfBillingsAmount;
    }

    /**
     * Sets the value of the earningsInExcessOfBillingsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setEarningsInExcessOfBillingsAmount(MoneyAmount value) {
        this.earningsInExcessOfBillingsAmount = value;
    }

    /**
     * Gets the value of the billingAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBillingAmount() {
        return billingAmount;
    }

    /**
     * Sets the value of the billingAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBillingAmount(MoneyAmount value) {
        this.billingAmount = value;
    }

    /**
     * Gets the value of the discountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets the value of the discountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountAmount(MoneyAmount value) {
        this.discountAmount = value;
    }

    /**
     * Gets the value of the salesTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getSalesTaxAmount() {
        return salesTaxAmount;
    }

    /**
     * Sets the value of the salesTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setSalesTaxAmount(MoneyAmount value) {
        this.salesTaxAmount = value;
    }

    /**
     * Gets the value of the retainageAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRetainageAmount() {
        return retainageAmount;
    }

    /**
     * Sets the value of the retainageAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRetainageAmount(MoneyAmount value) {
        this.retainageAmount = value;
    }

}
