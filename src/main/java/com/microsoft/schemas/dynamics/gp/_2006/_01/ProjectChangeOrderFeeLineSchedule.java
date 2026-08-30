
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrderFeeLineSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderFeeLineSchedule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderFeeLineScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="AmountBilled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrderFeeLineSchedule", propOrder = {
    "key",
    "sequenceNumber",
    "amount",
    "previousDate",
    "previousFeeAmount",
    "amountBilled"
})
public class ProjectChangeOrderFeeLineSchedule
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectChangeOrderFeeLineScheduleKey key;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;
    @XmlElement(name = "PreviousDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousDate;
    @XmlElement(name = "PreviousFeeAmount")
    protected MoneyAmount previousFeeAmount;
    @XmlElement(name = "AmountBilled")
    protected MoneyAmount amountBilled;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderFeeLineScheduleKey }
     *     
     */
    public ProjectChangeOrderFeeLineScheduleKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderFeeLineScheduleKey }
     *     
     */
    public void setKey(ProjectChangeOrderFeeLineScheduleKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSequenceNumber(Integer value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmount(MoneyAmount value) {
        this.amount = value;
    }

    /**
     * Gets the value of the previousDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousDate() {
        return previousDate;
    }

    /**
     * Sets the value of the previousDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousDate(XMLGregorianCalendar value) {
        this.previousDate = value;
    }

    /**
     * Gets the value of the previousFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousFeeAmount() {
        return previousFeeAmount;
    }

    /**
     * Sets the value of the previousFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousFeeAmount(MoneyAmount value) {
        this.previousFeeAmount = value;
    }

    /**
     * Gets the value of the amountBilled property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmountBilled() {
        return amountBilled;
    }

    /**
     * Sets the value of the amountBilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmountBilled(MoneyAmount value) {
        this.amountBilled = value;
    }

}
