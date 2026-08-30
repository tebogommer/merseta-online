
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectChangeOrderFeeLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectChangeOrderFeeLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectChangeOrderFeeLineKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="FeeType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeType"/&gt;
 *         &lt;element name="PreviousFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="VarianceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousFeePercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="VarianceFeePercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="Percent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="FeeToUseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PreviousSalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PreviousContractEndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Frequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFrequency"/&gt;
 *         &lt;element name="PreviousFrequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFrequency"/&gt;
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DoesRenew" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RenewDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PreviousTotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RenewDay" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RenewMonth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="SequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PostedProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedRetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedRetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Schedules" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectChangeOrderFeeLineSchedule" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectChangeOrderFeeLine", propOrder = {
    "key",
    "lineSequenceNumber",
    "feeType",
    "previousFeeAmount",
    "varianceFeeAmount",
    "amount",
    "previousFeePercent",
    "varianceFeePercent",
    "percent",
    "feeToUseKey",
    "salesTaxBasis",
    "salesTaxScheduleKey",
    "previousSalesTaxScheduleKey",
    "contractBeginDate",
    "previousContractBeginDate",
    "contractEndDate",
    "previousContractEndDate",
    "frequency",
    "previousFrequency",
    "startDate",
    "endDate",
    "doesRenew",
    "renewDate",
    "totalAmount",
    "previousTotalAmount",
    "renewDay",
    "renewMonth",
    "sequenceNumber",
    "postedProjectFeeAmount",
    "postedRetainerFeeAmount",
    "postedRetentionFeeAmount",
    "postedServiceFeeAmount",
    "schedules"
})
public class ProjectChangeOrderFeeLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectChangeOrderFeeLineKey key;
    @XmlElement(name = "LineSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer lineSequenceNumber;
    @XmlElement(name = "FeeType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectFeeType feeType;
    @XmlElement(name = "PreviousFeeAmount")
    protected MoneyAmount previousFeeAmount;
    @XmlElement(name = "VarianceFeeAmount")
    protected MoneyAmount varianceFeeAmount;
    @XmlElement(name = "Amount")
    protected MoneyAmount amount;
    @XmlElement(name = "PreviousFeePercent")
    protected Percent previousFeePercent;
    @XmlElement(name = "VarianceFeePercent")
    protected Percent varianceFeePercent;
    @XmlElement(name = "Percent")
    protected Percent percent;
    @XmlElement(name = "FeeToUseKey")
    protected ProjectFeeKey feeToUseKey;
    @XmlElement(name = "SalesTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis salesTaxBasis;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "PreviousSalesTaxScheduleKey")
    protected TaxScheduleKey previousSalesTaxScheduleKey;
    @XmlElement(name = "ContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractBeginDate;
    @XmlElement(name = "PreviousContractBeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractBeginDate;
    @XmlElement(name = "ContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "PreviousContractEndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar previousContractEndDate;
    @XmlElement(name = "Frequency", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectFrequency frequency;
    @XmlElement(name = "PreviousFrequency", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectFrequency previousFrequency;
    @XmlElement(name = "StartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "DoesRenew", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesRenew;
    @XmlElement(name = "RenewDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar renewDate;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "PreviousTotalAmount")
    protected MoneyAmount previousTotalAmount;
    @XmlElement(name = "RenewDay", required = true, type = Integer.class, nillable = true)
    protected Integer renewDay;
    @XmlElement(name = "RenewMonth", required = true, type = Integer.class, nillable = true)
    protected Integer renewMonth;
    @XmlElement(name = "SequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer sequenceNumber;
    @XmlElement(name = "PostedProjectFeeAmount")
    protected MoneyAmount postedProjectFeeAmount;
    @XmlElement(name = "PostedRetainerFeeAmount")
    protected MoneyAmount postedRetainerFeeAmount;
    @XmlElement(name = "PostedRetentionFeeAmount")
    protected MoneyAmount postedRetentionFeeAmount;
    @XmlElement(name = "PostedServiceFeeAmount")
    protected MoneyAmount postedServiceFeeAmount;
    @XmlElement(name = "Schedules")
    protected ArrayOfProjectChangeOrderFeeLineSchedule schedules;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectChangeOrderFeeLineKey }
     *     
     */
    public ProjectChangeOrderFeeLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectChangeOrderFeeLineKey }
     *     
     */
    public void setKey(ProjectChangeOrderFeeLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the lineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLineSequenceNumber(Integer value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Gets the value of the feeType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFeeType }
     *     
     */
    public ProjectFeeType getFeeType() {
        return feeType;
    }

    /**
     * Sets the value of the feeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFeeType }
     *     
     */
    public void setFeeType(ProjectFeeType value) {
        this.feeType = value;
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
     * Gets the value of the varianceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getVarianceFeeAmount() {
        return varianceFeeAmount;
    }

    /**
     * Sets the value of the varianceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setVarianceFeeAmount(MoneyAmount value) {
        this.varianceFeeAmount = value;
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
     * Gets the value of the previousFeePercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPreviousFeePercent() {
        return previousFeePercent;
    }

    /**
     * Sets the value of the previousFeePercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPreviousFeePercent(Percent value) {
        this.previousFeePercent = value;
    }

    /**
     * Gets the value of the varianceFeePercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getVarianceFeePercent() {
        return varianceFeePercent;
    }

    /**
     * Sets the value of the varianceFeePercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setVarianceFeePercent(Percent value) {
        this.varianceFeePercent = value;
    }

    /**
     * Gets the value of the percent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPercent() {
        return percent;
    }

    /**
     * Sets the value of the percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPercent(Percent value) {
        this.percent = value;
    }

    /**
     * Gets the value of the feeToUseKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFeeKey }
     *     
     */
    public ProjectFeeKey getFeeToUseKey() {
        return feeToUseKey;
    }

    /**
     * Sets the value of the feeToUseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFeeKey }
     *     
     */
    public void setFeeToUseKey(ProjectFeeKey value) {
        this.feeToUseKey = value;
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
     * Gets the value of the previousSalesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPreviousSalesTaxScheduleKey() {
        return previousSalesTaxScheduleKey;
    }

    /**
     * Sets the value of the previousSalesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPreviousSalesTaxScheduleKey(TaxScheduleKey value) {
        this.previousSalesTaxScheduleKey = value;
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
     * Gets the value of the previousContractBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractBeginDate() {
        return previousContractBeginDate;
    }

    /**
     * Sets the value of the previousContractBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractBeginDate(XMLGregorianCalendar value) {
        this.previousContractBeginDate = value;
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
     * Gets the value of the previousContractEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPreviousContractEndDate() {
        return previousContractEndDate;
    }

    /**
     * Sets the value of the previousContractEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPreviousContractEndDate(XMLGregorianCalendar value) {
        this.previousContractEndDate = value;
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
     * Gets the value of the previousFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectFrequency }
     *     
     */
    public ProjectFrequency getPreviousFrequency() {
        return previousFrequency;
    }

    /**
     * Sets the value of the previousFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectFrequency }
     *     
     */
    public void setPreviousFrequency(ProjectFrequency value) {
        this.previousFrequency = value;
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
     * Gets the value of the doesRenew property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesRenew() {
        return doesRenew;
    }

    /**
     * Sets the value of the doesRenew property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesRenew(Boolean value) {
        this.doesRenew = value;
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
     * Gets the value of the previousTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreviousTotalAmount() {
        return previousTotalAmount;
    }

    /**
     * Sets the value of the previousTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreviousTotalAmount(MoneyAmount value) {
        this.previousTotalAmount = value;
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
     * Gets the value of the postedProjectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedProjectFeeAmount() {
        return postedProjectFeeAmount;
    }

    /**
     * Sets the value of the postedProjectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedProjectFeeAmount(MoneyAmount value) {
        this.postedProjectFeeAmount = value;
    }

    /**
     * Gets the value of the postedRetainerFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedRetainerFeeAmount() {
        return postedRetainerFeeAmount;
    }

    /**
     * Sets the value of the postedRetainerFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedRetainerFeeAmount(MoneyAmount value) {
        this.postedRetainerFeeAmount = value;
    }

    /**
     * Gets the value of the postedRetentionFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedRetentionFeeAmount() {
        return postedRetentionFeeAmount;
    }

    /**
     * Sets the value of the postedRetentionFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedRetentionFeeAmount(MoneyAmount value) {
        this.postedRetentionFeeAmount = value;
    }

    /**
     * Gets the value of the postedServiceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedServiceFeeAmount() {
        return postedServiceFeeAmount;
    }

    /**
     * Sets the value of the postedServiceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedServiceFeeAmount(MoneyAmount value) {
        this.postedServiceFeeAmount = value;
    }

    /**
     * Gets the value of the schedules property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectChangeOrderFeeLineSchedule }
     *     
     */
    public ArrayOfProjectChangeOrderFeeLineSchedule getSchedules() {
        return schedules;
    }

    /**
     * Sets the value of the schedules property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectChangeOrderFeeLineSchedule }
     *     
     */
    public void setSchedules(ArrayOfProjectChangeOrderFeeLineSchedule value) {
        this.schedules = value;
    }

}
