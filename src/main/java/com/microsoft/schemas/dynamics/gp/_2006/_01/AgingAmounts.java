
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for AgingAmounts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgingAmounts"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Period1Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period2Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period3Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period4Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period5Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period6Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Period7Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastAgedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BalanceAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgingAmounts", propOrder = {
    "period1Amount",
    "period2Amount",
    "period3Amount",
    "period4Amount",
    "period5Amount",
    "period6Amount",
    "period7Amount",
    "lastAgedDate",
    "balanceAmount"
})
public class AgingAmounts {

    @XmlElement(name = "Period1Amount")
    protected MoneyAmount period1Amount;
    @XmlElement(name = "Period2Amount")
    protected MoneyAmount period2Amount;
    @XmlElement(name = "Period3Amount")
    protected MoneyAmount period3Amount;
    @XmlElement(name = "Period4Amount")
    protected MoneyAmount period4Amount;
    @XmlElement(name = "Period5Amount")
    protected MoneyAmount period5Amount;
    @XmlElement(name = "Period6Amount")
    protected MoneyAmount period6Amount;
    @XmlElement(name = "Period7Amount")
    protected MoneyAmount period7Amount;
    @XmlElement(name = "LastAgedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastAgedDate;
    @XmlElement(name = "BalanceAmount")
    protected MoneyAmount balanceAmount;

    /**
     * Gets the value of the period1Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod1Amount() {
        return period1Amount;
    }

    /**
     * Sets the value of the period1Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod1Amount(MoneyAmount value) {
        this.period1Amount = value;
    }

    /**
     * Gets the value of the period2Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod2Amount() {
        return period2Amount;
    }

    /**
     * Sets the value of the period2Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod2Amount(MoneyAmount value) {
        this.period2Amount = value;
    }

    /**
     * Gets the value of the period3Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod3Amount() {
        return period3Amount;
    }

    /**
     * Sets the value of the period3Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod3Amount(MoneyAmount value) {
        this.period3Amount = value;
    }

    /**
     * Gets the value of the period4Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod4Amount() {
        return period4Amount;
    }

    /**
     * Sets the value of the period4Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod4Amount(MoneyAmount value) {
        this.period4Amount = value;
    }

    /**
     * Gets the value of the period5Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod5Amount() {
        return period5Amount;
    }

    /**
     * Sets the value of the period5Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod5Amount(MoneyAmount value) {
        this.period5Amount = value;
    }

    /**
     * Gets the value of the period6Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod6Amount() {
        return period6Amount;
    }

    /**
     * Sets the value of the period6Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod6Amount(MoneyAmount value) {
        this.period6Amount = value;
    }

    /**
     * Gets the value of the period7Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPeriod7Amount() {
        return period7Amount;
    }

    /**
     * Sets the value of the period7Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPeriod7Amount(MoneyAmount value) {
        this.period7Amount = value;
    }

    /**
     * Gets the value of the lastAgedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastAgedDate() {
        return lastAgedDate;
    }

    /**
     * Sets the value of the lastAgedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastAgedDate(XMLGregorianCalendar value) {
        this.lastAgedDate = value;
    }

    /**
     * Gets the value of the balanceAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBalanceAmount() {
        return balanceAmount;
    }

    /**
     * Sets the value of the balanceAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBalanceAmount(MoneyAmount value) {
        this.balanceAmount = value;
    }

}
