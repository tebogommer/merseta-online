
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceLaborLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLaborLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillableLaborPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="BillableTime" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DistanceTraveled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDistanceTraveled" minOccurs="0"/&gt;
 *         &lt;element name="EndDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantitySold" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="StartDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TransactionTime" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="WorkTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceWorkTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UseType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLaborUseType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLaborLine", propOrder = {
    "billableLaborPercent",
    "billableTime",
    "distanceTraveled",
    "endDateTime",
    "quantity",
    "quantitySold",
    "startDateTime",
    "transactionTime",
    "workTypeKey",
    "totalCost",
    "useType"
})
@XmlSeeAlso({
    ServiceQuoteLaborLine.class,
    ServiceCallLaborLine.class
})
public abstract class ServiceLaborLine
    extends ServiceLine
{

    @XmlElement(name = "BillableLaborPercent")
    protected Percent billableLaborPercent;
    @XmlElement(name = "BillableTime", required = true, type = Integer.class, nillable = true)
    protected Integer billableTime;
    @XmlElement(name = "DistanceTraveled")
    protected ServiceDistanceTraveled distanceTraveled;
    @XmlElement(name = "EndDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDateTime;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "QuantitySold")
    protected Quantity quantitySold;
    @XmlElement(name = "StartDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDateTime;
    @XmlElement(name = "TransactionTime", required = true, type = Integer.class, nillable = true)
    protected Integer transactionTime;
    @XmlElement(name = "WorkTypeKey")
    protected ServiceWorkTypeKey workTypeKey;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "UseType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceLaborUseType useType;

    /**
     * Gets the value of the billableLaborPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getBillableLaborPercent() {
        return billableLaborPercent;
    }

    /**
     * Sets the value of the billableLaborPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setBillableLaborPercent(Percent value) {
        this.billableLaborPercent = value;
    }

    /**
     * Gets the value of the billableTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBillableTime() {
        return billableTime;
    }

    /**
     * Sets the value of the billableTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBillableTime(Integer value) {
        this.billableTime = value;
    }

    /**
     * Gets the value of the distanceTraveled property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDistanceTraveled }
     *     
     */
    public ServiceDistanceTraveled getDistanceTraveled() {
        return distanceTraveled;
    }

    /**
     * Sets the value of the distanceTraveled property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDistanceTraveled }
     *     
     */
    public void setDistanceTraveled(ServiceDistanceTraveled value) {
        this.distanceTraveled = value;
    }

    /**
     * Gets the value of the endDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDateTime() {
        return endDateTime;
    }

    /**
     * Sets the value of the endDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDateTime(XMLGregorianCalendar value) {
        this.endDateTime = value;
    }

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
     * Gets the value of the quantitySold property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantitySold() {
        return quantitySold;
    }

    /**
     * Sets the value of the quantitySold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantitySold(Quantity value) {
        this.quantitySold = value;
    }

    /**
     * Gets the value of the startDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateTime() {
        return startDateTime;
    }

    /**
     * Sets the value of the startDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateTime(XMLGregorianCalendar value) {
        this.startDateTime = value;
    }

    /**
     * Gets the value of the transactionTime property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTransactionTime() {
        return transactionTime;
    }

    /**
     * Sets the value of the transactionTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTransactionTime(Integer value) {
        this.transactionTime = value;
    }

    /**
     * Gets the value of the workTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceWorkTypeKey }
     *     
     */
    public ServiceWorkTypeKey getWorkTypeKey() {
        return workTypeKey;
    }

    /**
     * Sets the value of the workTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceWorkTypeKey }
     *     
     */
    public void setWorkTypeKey(ServiceWorkTypeKey value) {
        this.workTypeKey = value;
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
     * Gets the value of the useType property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLaborUseType }
     *     
     */
    public ServiceLaborUseType getUseType() {
        return useType;
    }

    /**
     * Sets the value of the useType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLaborUseType }
     *     
     */
    public void setUseType(ServiceLaborUseType value) {
        this.useType = value;
    }

}
