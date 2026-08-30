
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CustomerReceivablesSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerReceivablesSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="Aging" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AgingAmounts" minOccurs="0"/&gt;
 *         &lt;element name="LastYear" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesAmounts" minOccurs="0"/&gt;
 *         &lt;element name="LifeToDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesAmounts" minOccurs="0"/&gt;
 *         &lt;element name="YearToDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesAmounts" minOccurs="0"/&gt;
 *         &lt;element name="OnOrderAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastPaymentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastPaymentDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastStatementAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastStatementDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastTransactionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastTransactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerReceivablesSummary", propOrder = {
    "key",
    "aging",
    "lastYear",
    "lifeToDate",
    "yearToDate",
    "onOrderAmount",
    "lastPaymentAmount",
    "lastPaymentDate",
    "lastStatementAmount",
    "lastStatementDate",
    "lastTransactionAmount",
    "lastTransactionDate"
})
public class CustomerReceivablesSummary
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CustomerKey key;
    @XmlElement(name = "Aging")
    protected AgingAmounts aging;
    @XmlElement(name = "LastYear")
    protected SalesAmounts lastYear;
    @XmlElement(name = "LifeToDate")
    protected SalesAmounts lifeToDate;
    @XmlElement(name = "YearToDate")
    protected SalesAmounts yearToDate;
    @XmlElement(name = "OnOrderAmount")
    protected MoneyAmount onOrderAmount;
    @XmlElement(name = "LastPaymentAmount")
    protected MoneyAmount lastPaymentAmount;
    @XmlElement(name = "LastPaymentDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPaymentDate;
    @XmlElement(name = "LastStatementAmount")
    protected MoneyAmount lastStatementAmount;
    @XmlElement(name = "LastStatementDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastStatementDate;
    @XmlElement(name = "LastTransactionAmount")
    protected MoneyAmount lastTransactionAmount;
    @XmlElement(name = "LastTransactionDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastTransactionDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setKey(CustomerKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the aging property.
     * 
     * @return
     *     possible object is
     *     {@link AgingAmounts }
     *     
     */
    public AgingAmounts getAging() {
        return aging;
    }

    /**
     * Sets the value of the aging property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgingAmounts }
     *     
     */
    public void setAging(AgingAmounts value) {
        this.aging = value;
    }

    /**
     * Gets the value of the lastYear property.
     * 
     * @return
     *     possible object is
     *     {@link SalesAmounts }
     *     
     */
    public SalesAmounts getLastYear() {
        return lastYear;
    }

    /**
     * Sets the value of the lastYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesAmounts }
     *     
     */
    public void setLastYear(SalesAmounts value) {
        this.lastYear = value;
    }

    /**
     * Gets the value of the lifeToDate property.
     * 
     * @return
     *     possible object is
     *     {@link SalesAmounts }
     *     
     */
    public SalesAmounts getLifeToDate() {
        return lifeToDate;
    }

    /**
     * Sets the value of the lifeToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesAmounts }
     *     
     */
    public void setLifeToDate(SalesAmounts value) {
        this.lifeToDate = value;
    }

    /**
     * Gets the value of the yearToDate property.
     * 
     * @return
     *     possible object is
     *     {@link SalesAmounts }
     *     
     */
    public SalesAmounts getYearToDate() {
        return yearToDate;
    }

    /**
     * Sets the value of the yearToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesAmounts }
     *     
     */
    public void setYearToDate(SalesAmounts value) {
        this.yearToDate = value;
    }

    /**
     * Gets the value of the onOrderAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getOnOrderAmount() {
        return onOrderAmount;
    }

    /**
     * Sets the value of the onOrderAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setOnOrderAmount(MoneyAmount value) {
        this.onOrderAmount = value;
    }

    /**
     * Gets the value of the lastPaymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLastPaymentAmount() {
        return lastPaymentAmount;
    }

    /**
     * Sets the value of the lastPaymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLastPaymentAmount(MoneyAmount value) {
        this.lastPaymentAmount = value;
    }

    /**
     * Gets the value of the lastPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPaymentDate() {
        return lastPaymentDate;
    }

    /**
     * Sets the value of the lastPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPaymentDate(XMLGregorianCalendar value) {
        this.lastPaymentDate = value;
    }

    /**
     * Gets the value of the lastStatementAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLastStatementAmount() {
        return lastStatementAmount;
    }

    /**
     * Sets the value of the lastStatementAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLastStatementAmount(MoneyAmount value) {
        this.lastStatementAmount = value;
    }

    /**
     * Gets the value of the lastStatementDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastStatementDate() {
        return lastStatementDate;
    }

    /**
     * Sets the value of the lastStatementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastStatementDate(XMLGregorianCalendar value) {
        this.lastStatementDate = value;
    }

    /**
     * Gets the value of the lastTransactionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLastTransactionAmount() {
        return lastTransactionAmount;
    }

    /**
     * Sets the value of the lastTransactionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLastTransactionAmount(MoneyAmount value) {
        this.lastTransactionAmount = value;
    }

    /**
     * Gets the value of the lastTransactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastTransactionDate() {
        return lastTransactionDate;
    }

    /**
     * Sets the value of the lastTransactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastTransactionDate(XMLGregorianCalendar value) {
        this.lastTransactionDate = value;
    }

}
