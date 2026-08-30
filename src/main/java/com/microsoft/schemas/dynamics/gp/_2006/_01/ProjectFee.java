
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectFee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectFee"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeItemKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeType"/&gt;
 *         &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CostPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="RevenuePercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="RetentionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Frequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFrequency"/&gt;
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Renew" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RenewDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RenewDay" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RenewMonth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Schedules" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectFeeSchedule" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectFee", propOrder = {
    "key",
    "name",
    "type",
    "amount",
    "costPercent",
    "revenuePercent",
    "retentionPercent",
    "salesTaxScheduleKey",
    "salesTaxBasis",
    "contractBeginDate",
    "contractEndDate",
    "frequency",
    "startDate",
    "endDate",
    "renew",
    "renewDate",
    "totalAmount",
    "renewDay",
    "renewMonth",
    "userDefined1",
    "userDefined2",
    "serviceFeeAmount",
    "schedules"
})
public class ProjectFee
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectFeeItemKey key;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectFeeType type;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;
    @XmlElement(name = "CostPercent")
    protected Percent costPercent;
    @XmlElement(name = "RevenuePercent")
    protected Percent revenuePercent;
    @XmlElement(name = "RetentionPercent")
    protected Percent retentionPercent;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "SalesTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis salesTaxBasis;
    @XmlElement(name = "ContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractBeginDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "Frequency", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectFrequency frequency;
    @XmlElement(name = "StartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "Renew", required = true, type = Boolean.class, nillable = true)
    protected Boolean renew;
    @XmlElement(name = "RenewDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar renewDate;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "RenewDay", required = true, type = Integer.class, nillable = true)
    protected Integer renewDay;
    @XmlElement(name = "RenewMonth", required = true, type = Integer.class, nillable = true)
    protected Integer renewMonth;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "ServiceFeeAmount")
    protected MoneyAmount serviceFeeAmount;
    @XmlElement(name = "Schedules")
    protected ArrayOfProjectFeeSchedule schedules;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFeeItemKey }
     *     
     */
    public ProjectFeeItemKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFeeItemKey }
     *     
     */
    public void setKey(ProjectFeeItemKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFeeType }
     *     
     */
    public ProjectFeeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFeeType }
     *     
     */
    public void setType(ProjectFeeType value) {
        this.type = value;
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
     * Gets the value of the costPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCostPercent() {
        return costPercent;
    }

    /**
     * Sets the value of the costPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCostPercent(Percent value) {
        this.costPercent = value;
    }

    /**
     * Gets the value of the revenuePercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getRevenuePercent() {
        return revenuePercent;
    }

    /**
     * Sets the value of the revenuePercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setRevenuePercent(Percent value) {
        this.revenuePercent = value;
    }

    /**
     * Gets the value of the retentionPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getRetentionPercent() {
        return retentionPercent;
    }

    /**
     * Sets the value of the retentionPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setRetentionPercent(Percent value) {
        this.retentionPercent = value;
    }

    /**
     * Gets the value of the salesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getSalesTaxScheduleKey() {
        return salesTaxScheduleKey;
    }

    /**
     * Sets the value of the salesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setSalesTaxScheduleKey(TaxScheduleKey value) {
        this.salesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the salesTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getSalesTaxBasis() {
        return salesTaxBasis;
    }

    /**
     * Sets the value of the salesTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setSalesTaxBasis(SalesTaxBasis value) {
        this.salesTaxBasis = value;
    }

    /**
     * Gets the value of the contractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractBeginDate() {
        return contractBeginDate;
    }

    /**
     * Sets the value of the contractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractBeginDate(XMLGregorianCalendar value) {
        this.contractBeginDate = value;
    }

    /**
     * Gets the value of the contractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    /**
     * Sets the value of the contractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFrequency }
     *     
     */
    public ProjectFrequency getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFrequency }
     *     
     */
    public void setFrequency(ProjectFrequency value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the renew property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRenew() {
        return renew;
    }

    /**
     * Sets the value of the renew property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRenew(Boolean value) {
        this.renew = value;
    }

    /**
     * Gets the value of the renewDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRenewDate() {
        return renewDate;
    }

    /**
     * Sets the value of the renewDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRenewDate(XMLGregorianCalendar value) {
        this.renewDate = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalAmount(MoneyAmount value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the renewDay property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRenewDay() {
        return renewDay;
    }

    /**
     * Sets the value of the renewDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRenewDay(Integer value) {
        this.renewDay = value;
    }

    /**
     * Gets the value of the renewMonth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRenewMonth() {
        return renewMonth;
    }

    /**
     * Sets the value of the renewMonth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRenewMonth(Integer value) {
        this.renewMonth = value;
    }

    /**
     * Gets the value of the userDefined1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined1() {
        return userDefined1;
    }

    /**
     * Sets the value of the userDefined1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined1(String value) {
        this.userDefined1 = value;
    }

    /**
     * Gets the value of the userDefined2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined2() {
        return userDefined2;
    }

    /**
     * Sets the value of the userDefined2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined2(String value) {
        this.userDefined2 = value;
    }

    /**
     * Gets the value of the serviceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    /**
     * Sets the value of the serviceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setServiceFeeAmount(MoneyAmount value) {
        this.serviceFeeAmount = value;
    }

    /**
     * Gets the value of the schedules property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectFeeSchedule }
     *     
     */
    public ArrayOfProjectFeeSchedule getSchedules() {
        return schedules;
    }

    /**
     * Sets the value of the schedules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectFeeSchedule }
     *     
     */
    public void setSchedules(ArrayOfProjectFeeSchedule value) {
        this.schedules = value;
    }

}
