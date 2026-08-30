
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceCall complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCall"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DispatchDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ArrivalDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CompletionDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Meter1" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter2" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter3" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter4" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter5" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallType"/&gt;
 *         &lt;element name="EscalationDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EscalationLevel" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="InvoicedAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="IsCallback" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="NotifyDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="WasNotified" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Parts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallPartLine" minOccurs="0"/&gt;
 *         &lt;element name="Labor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallLaborLine" minOccurs="0"/&gt;
 *         &lt;element name="AdditionalCharges" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallAdditionalChargeLine" minOccurs="0"/&gt;
 *         &lt;element name="Expenses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallExpenseLine" minOccurs="0"/&gt;
 *         &lt;element name="EquipmentCodes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallEquipmentCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCall", propOrder = {
    "dispatchDateTime",
    "arrivalDateTime",
    "completionDateTime",
    "meter1",
    "meter2",
    "meter3",
    "meter4",
    "meter5",
    "type",
    "escalationDateTime",
    "escalationLevel",
    "invoicedAmount",
    "isCallback",
    "notifyDateTime",
    "wasNotified",
    "parts",
    "labor",
    "additionalCharges",
    "expenses",
    "equipmentCodes"
})
public class ServiceCall
    extends ServiceCallDocument
{

    @XmlElement(name = "DispatchDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dispatchDateTime;
    @XmlElement(name = "ArrivalDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar arrivalDateTime;
    @XmlElement(name = "CompletionDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar completionDateTime;
    @XmlElement(name = "Meter1")
    protected ServiceCallEquipmentMeter meter1;
    @XmlElement(name = "Meter2")
    protected ServiceCallEquipmentMeter meter2;
    @XmlElement(name = "Meter3")
    protected ServiceCallEquipmentMeter meter3;
    @XmlElement(name = "Meter4")
    protected ServiceCallEquipmentMeter meter4;
    @XmlElement(name = "Meter5")
    protected ServiceCallEquipmentMeter meter5;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallType type;
    @XmlElement(name = "EscalationDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar escalationDateTime;
    @XmlElement(name = "EscalationLevel", required = true, type = Integer.class, nillable = true)
    protected Integer escalationLevel;
    @XmlElement(name = "InvoicedAmount")
    protected MoneyAmount invoicedAmount;
    @XmlElement(name = "IsCallback", required = true, type = Boolean.class, nillable = true)
    protected Boolean isCallback;
    @XmlElement(name = "NotifyDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notifyDateTime;
    @XmlElement(name = "WasNotified", required = true, type = Boolean.class, nillable = true)
    protected Boolean wasNotified;
    @XmlElement(name = "Parts")
    protected ArrayOfServiceCallPartLine parts;
    @XmlElement(name = "Labor")
    protected ArrayOfServiceCallLaborLine labor;
    @XmlElement(name = "AdditionalCharges")
    protected ArrayOfServiceCallAdditionalChargeLine additionalCharges;
    @XmlElement(name = "Expenses")
    protected ArrayOfServiceCallExpenseLine expenses;
    @XmlElement(name = "EquipmentCodes")
    protected ArrayOfServiceCallEquipmentCode equipmentCodes;

    /**
     * Gets the value of the dispatchDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDispatchDateTime() {
        return dispatchDateTime;
    }

    /**
     * Sets the value of the dispatchDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDispatchDateTime(XMLGregorianCalendar value) {
        this.dispatchDateTime = value;
    }

    /**
     * Gets the value of the arrivalDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the value of the arrivalDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setArrivalDateTime(XMLGregorianCalendar value) {
        this.arrivalDateTime = value;
    }

    /**
     * Gets the value of the completionDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCompletionDateTime() {
        return completionDateTime;
    }

    /**
     * Sets the value of the completionDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCompletionDateTime(XMLGregorianCalendar value) {
        this.completionDateTime = value;
    }

    /**
     * Gets the value of the meter1 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public ServiceCallEquipmentMeter getMeter1() {
        return meter1;
    }

    /**
     * Sets the value of the meter1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public void setMeter1(ServiceCallEquipmentMeter value) {
        this.meter1 = value;
    }

    /**
     * Gets the value of the meter2 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public ServiceCallEquipmentMeter getMeter2() {
        return meter2;
    }

    /**
     * Sets the value of the meter2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public void setMeter2(ServiceCallEquipmentMeter value) {
        this.meter2 = value;
    }

    /**
     * Gets the value of the meter3 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public ServiceCallEquipmentMeter getMeter3() {
        return meter3;
    }

    /**
     * Sets the value of the meter3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public void setMeter3(ServiceCallEquipmentMeter value) {
        this.meter3 = value;
    }

    /**
     * Gets the value of the meter4 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public ServiceCallEquipmentMeter getMeter4() {
        return meter4;
    }

    /**
     * Sets the value of the meter4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public void setMeter4(ServiceCallEquipmentMeter value) {
        this.meter4 = value;
    }

    /**
     * Gets the value of the meter5 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public ServiceCallEquipmentMeter getMeter5() {
        return meter5;
    }

    /**
     * Sets the value of the meter5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallEquipmentMeter }
     *     
     */
    public void setMeter5(ServiceCallEquipmentMeter value) {
        this.meter5 = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallType }
     *     
     */
    public ServiceCallType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallType }
     *     
     */
    public void setType(ServiceCallType value) {
        this.type = value;
    }

    /**
     * Gets the value of the escalationDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEscalationDateTime() {
        return escalationDateTime;
    }

    /**
     * Sets the value of the escalationDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEscalationDateTime(XMLGregorianCalendar value) {
        this.escalationDateTime = value;
    }

    /**
     * Gets the value of the escalationLevel property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEscalationLevel() {
        return escalationLevel;
    }

    /**
     * Sets the value of the escalationLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEscalationLevel(Integer value) {
        this.escalationLevel = value;
    }

    /**
     * Gets the value of the invoicedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getInvoicedAmount() {
        return invoicedAmount;
    }

    /**
     * Sets the value of the invoicedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setInvoicedAmount(MoneyAmount value) {
        this.invoicedAmount = value;
    }

    /**
     * Gets the value of the isCallback property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCallback() {
        return isCallback;
    }

    /**
     * Sets the value of the isCallback property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCallback(Boolean value) {
        this.isCallback = value;
    }

    /**
     * Gets the value of the notifyDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotifyDateTime() {
        return notifyDateTime;
    }

    /**
     * Sets the value of the notifyDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotifyDateTime(XMLGregorianCalendar value) {
        this.notifyDateTime = value;
    }

    /**
     * Gets the value of the wasNotified property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWasNotified() {
        return wasNotified;
    }

    /**
     * Sets the value of the wasNotified property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWasNotified(Boolean value) {
        this.wasNotified = value;
    }

    /**
     * Gets the value of the parts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallPartLine }
     *     
     */
    public ArrayOfServiceCallPartLine getParts() {
        return parts;
    }

    /**
     * Sets the value of the parts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallPartLine }
     *     
     */
    public void setParts(ArrayOfServiceCallPartLine value) {
        this.parts = value;
    }

    /**
     * Gets the value of the labor property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallLaborLine }
     *     
     */
    public ArrayOfServiceCallLaborLine getLabor() {
        return labor;
    }

    /**
     * Sets the value of the labor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallLaborLine }
     *     
     */
    public void setLabor(ArrayOfServiceCallLaborLine value) {
        this.labor = value;
    }

    /**
     * Gets the value of the additionalCharges property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallAdditionalChargeLine }
     *     
     */
    public ArrayOfServiceCallAdditionalChargeLine getAdditionalCharges() {
        return additionalCharges;
    }

    /**
     * Sets the value of the additionalCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallAdditionalChargeLine }
     *     
     */
    public void setAdditionalCharges(ArrayOfServiceCallAdditionalChargeLine value) {
        this.additionalCharges = value;
    }

    /**
     * Gets the value of the expenses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallExpenseLine }
     *     
     */
    public ArrayOfServiceCallExpenseLine getExpenses() {
        return expenses;
    }

    /**
     * Sets the value of the expenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallExpenseLine }
     *     
     */
    public void setExpenses(ArrayOfServiceCallExpenseLine value) {
        this.expenses = value;
    }

    /**
     * Gets the value of the equipmentCodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallEquipmentCode }
     *     
     */
    public ArrayOfServiceCallEquipmentCode getEquipmentCodes() {
        return equipmentCodes;
    }

    /**
     * Sets the value of the equipmentCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallEquipmentCode }
     *     
     */
    public void setEquipmentCodes(ArrayOfServiceCallEquipmentCode value) {
        this.equipmentCodes = value;
    }

}
