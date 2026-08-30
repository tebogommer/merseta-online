
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesAmounts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesAmounts"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AverageDaysToPay" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="HighBalance" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="NumberOfInvoices" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TermsDiscountsTaken" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalCashReceived" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalFinanceCharge" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalReturns" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalSales" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalWaivedFinanceCharge" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalWriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesAmounts", propOrder = {
    "averageDaysToPay",
    "highBalance",
    "numberOfInvoices",
    "termsDiscountsTaken",
    "totalCashReceived",
    "totalCost",
    "totalFinanceCharge",
    "totalReturns",
    "totalSales",
    "totalWaivedFinanceCharge",
    "totalWriteoffAmount"
})
public class SalesAmounts {

    @XmlElement(name = "AverageDaysToPay")
    protected int averageDaysToPay;
    @XmlElement(name = "HighBalance")
    protected MoneyAmount highBalance;
    @XmlElement(name = "NumberOfInvoices")
    protected int numberOfInvoices;
    @XmlElement(name = "TermsDiscountsTaken")
    protected MoneyAmount termsDiscountsTaken;
    @XmlElement(name = "TotalCashReceived")
    protected MoneyAmount totalCashReceived;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "TotalFinanceCharge")
    protected MoneyAmount totalFinanceCharge;
    @XmlElement(name = "TotalReturns")
    protected MoneyAmount totalReturns;
    @XmlElement(name = "TotalSales")
    protected MoneyAmount totalSales;
    @XmlElement(name = "TotalWaivedFinanceCharge")
    protected MoneyAmount totalWaivedFinanceCharge;
    @XmlElement(name = "TotalWriteoffAmount")
    protected MoneyAmount totalWriteoffAmount;

    /**
     * Gets the value of the averageDaysToPay property.
     * 
     */
    public int getAverageDaysToPay() {
        return averageDaysToPay;
    }

    /**
     * Sets the value of the averageDaysToPay property.
     * 
     */
    public void setAverageDaysToPay(int value) {
        this.averageDaysToPay = value;
    }

    /**
     * Gets the value of the highBalance property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getHighBalance() {
        return highBalance;
    }

    /**
     * Sets the value of the highBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setHighBalance(MoneyAmount value) {
        this.highBalance = value;
    }

    /**
     * Gets the value of the numberOfInvoices property.
     * 
     */
    public int getNumberOfInvoices() {
        return numberOfInvoices;
    }

    /**
     * Sets the value of the numberOfInvoices property.
     * 
     */
    public void setNumberOfInvoices(int value) {
        this.numberOfInvoices = value;
    }

    /**
     * Gets the value of the termsDiscountsTaken property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTermsDiscountsTaken() {
        return termsDiscountsTaken;
    }

    /**
     * Sets the value of the termsDiscountsTaken property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTermsDiscountsTaken(MoneyAmount value) {
        this.termsDiscountsTaken = value;
    }

    /**
     * Gets the value of the totalCashReceived property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCashReceived() {
        return totalCashReceived;
    }

    /**
     * Sets the value of the totalCashReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCashReceived(MoneyAmount value) {
        this.totalCashReceived = value;
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
     * Gets the value of the totalFinanceCharge property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalFinanceCharge() {
        return totalFinanceCharge;
    }

    /**
     * Sets the value of the totalFinanceCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalFinanceCharge(MoneyAmount value) {
        this.totalFinanceCharge = value;
    }

    /**
     * Gets the value of the totalReturns property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalReturns() {
        return totalReturns;
    }

    /**
     * Sets the value of the totalReturns property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalReturns(MoneyAmount value) {
        this.totalReturns = value;
    }

    /**
     * Gets the value of the totalSales property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalSales() {
        return totalSales;
    }

    /**
     * Sets the value of the totalSales property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalSales(MoneyAmount value) {
        this.totalSales = value;
    }

    /**
     * Gets the value of the totalWaivedFinanceCharge property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalWaivedFinanceCharge() {
        return totalWaivedFinanceCharge;
    }

    /**
     * Sets the value of the totalWaivedFinanceCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalWaivedFinanceCharge(MoneyAmount value) {
        this.totalWaivedFinanceCharge = value;
    }

    /**
     * Gets the value of the totalWriteoffAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalWriteoffAmount() {
        return totalWriteoffAmount;
    }

    /**
     * Sets the value of the totalWriteoffAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalWriteoffAmount(MoneyAmount value) {
        this.totalWriteoffAmount = value;
    }

}
