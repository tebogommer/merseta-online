
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceCallDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CustomerReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsOnHold" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="PriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ResponseDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ServiceAreaKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceAreaKey" minOccurs="0"/&gt;
 *         &lt;element name="ServiceContractKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="ServiceTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallShipToAddress" minOccurs="0"/&gt;
 *         &lt;element name="StatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="TechnicianKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTechnicianKey" minOccurs="0"/&gt;
 *         &lt;element name="TimeZoneKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTimeZoneKey" minOccurs="0"/&gt;
 *         &lt;element name="DocumentTotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="EstimatedTimeToRepair" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LaborCharges" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCharge" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousCharges" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCharge" minOccurs="0"/&gt;
 *         &lt;element name="PartsCharges" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCharge" minOccurs="0"/&gt;
 *         &lt;element name="PreTaxDocumentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SalesPersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="ServiceContractLineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxExemptNumber1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxExemptNumber2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceDistribution" minOccurs="0"/&gt;
 *         &lt;element name="Audits" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceCallDocumentAudit" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallDocument", propOrder = {
    "customerReference",
    "description",
    "isOnHold",
    "paymentTermsKey",
    "priceLevelKey",
    "priority",
    "responseDateTime",
    "serviceAreaKey",
    "serviceContractKey",
    "serviceTypeKey",
    "shipToAddress",
    "statusCodeKey",
    "technicianKey",
    "timeZoneKey",
    "documentTotal",
    "estimatedTimeToRepair",
    "laborCharges",
    "miscellaneousCharges",
    "partsCharges",
    "preTaxDocumentAmount",
    "salesPersonKey",
    "serviceContractLineSequenceNumber",
    "taxAmount",
    "taxExemptNumber1",
    "taxExemptNumber2",
    "taxScheduleKey",
    "distributions",
    "audits"
})
@XmlSeeAlso({
    ServiceQuote.class,
    ServiceCall.class
})
public abstract class ServiceCallDocument
    extends ServiceDocument
{

    @XmlElement(name = "CustomerReference")
    protected String customerReference;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "IsOnHold", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnHold;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "PriceLevelKey")
    protected PriceLevelKey priceLevelKey;
    @XmlElement(name = "Priority", required = true, type = Integer.class, nillable = true)
    protected Integer priority;
    @XmlElement(name = "ResponseDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar responseDateTime;
    @XmlElement(name = "ServiceAreaKey")
    protected ServiceAreaKey serviceAreaKey;
    @XmlElement(name = "ServiceContractKey")
    protected ServiceDocumentKey serviceContractKey;
    @XmlElement(name = "ServiceTypeKey")
    protected ServiceTypeKey serviceTypeKey;
    @XmlElement(name = "ShipToAddress")
    protected ServiceCallShipToAddress shipToAddress;
    @XmlElement(name = "StatusCodeKey")
    protected ServiceCallStatusCodeKey statusCodeKey;
    @XmlElement(name = "TechnicianKey")
    protected ServiceTechnicianKey technicianKey;
    @XmlElement(name = "TimeZoneKey")
    protected ServiceTimeZoneKey timeZoneKey;
    @XmlElement(name = "DocumentTotal")
    protected MoneyAmount documentTotal;
    @XmlElement(name = "EstimatedTimeToRepair", required = true)
    protected BigDecimal estimatedTimeToRepair;
    @XmlElement(name = "LaborCharges")
    protected ServiceCharge laborCharges;
    @XmlElement(name = "MiscellaneousCharges")
    protected ServiceCharge miscellaneousCharges;
    @XmlElement(name = "PartsCharges")
    protected ServiceCharge partsCharges;
    @XmlElement(name = "PreTaxDocumentAmount")
    protected MoneyAmount preTaxDocumentAmount;
    @XmlElement(name = "SalesPersonKey")
    protected SalespersonKey salesPersonKey;
    @XmlElement(name = "ServiceContractLineSequenceNumber", required = true)
    protected BigDecimal serviceContractLineSequenceNumber;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "TaxExemptNumber1")
    protected String taxExemptNumber1;
    @XmlElement(name = "TaxExemptNumber2")
    protected String taxExemptNumber2;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "Distributions")
    protected ArrayOfServiceDistribution distributions;
    @XmlElement(name = "Audits")
    protected ArrayOfServiceCallDocumentAudit audits;

    /**
     * Gets the value of the customerReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerReference() {
        return customerReference;
    }

    /**
     * Sets the value of the customerReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerReference(String value) {
        this.customerReference = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the isOnHold property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOnHold() {
        return isOnHold;
    }

    /**
     * Sets the value of the isOnHold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOnHold(Boolean value) {
        this.isOnHold = value;
    }

    /**
     * Gets the value of the paymentTermsKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsKey }
     *     
     */
    public PaymentTermsKey getPaymentTermsKey() {
        return paymentTermsKey;
    }

    /**
     * Sets the value of the paymentTermsKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsKey }
     *     
     */
    public void setPaymentTermsKey(PaymentTermsKey value) {
        this.paymentTermsKey = value;
    }

    /**
     * Gets the value of the priceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getPriceLevelKey() {
        return priceLevelKey;
    }

    /**
     * Sets the value of the priceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setPriceLevelKey(PriceLevelKey value) {
        this.priceLevelKey = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPriority(Integer value) {
        this.priority = value;
    }

    /**
     * Gets the value of the responseDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getResponseDateTime() {
        return responseDateTime;
    }

    /**
     * Sets the value of the responseDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setResponseDateTime(XMLGregorianCalendar value) {
        this.responseDateTime = value;
    }

    /**
     * Gets the value of the serviceAreaKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceAreaKey }
     *     
     */
    public ServiceAreaKey getServiceAreaKey() {
        return serviceAreaKey;
    }

    /**
     * Sets the value of the serviceAreaKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceAreaKey }
     *     
     */
    public void setServiceAreaKey(ServiceAreaKey value) {
        this.serviceAreaKey = value;
    }

    /**
     * Gets the value of the serviceContractKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public ServiceDocumentKey getServiceContractKey() {
        return serviceContractKey;
    }

    /**
     * Sets the value of the serviceContractKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public void setServiceContractKey(ServiceDocumentKey value) {
        this.serviceContractKey = value;
    }

    /**
     * Gets the value of the serviceTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTypeKey }
     *     
     */
    public ServiceTypeKey getServiceTypeKey() {
        return serviceTypeKey;
    }

    /**
     * Sets the value of the serviceTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTypeKey }
     *     
     */
    public void setServiceTypeKey(ServiceTypeKey value) {
        this.serviceTypeKey = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallShipToAddress }
     *     
     */
    public ServiceCallShipToAddress getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallShipToAddress }
     *     
     */
    public void setShipToAddress(ServiceCallShipToAddress value) {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the statusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public ServiceCallStatusCodeKey getStatusCodeKey() {
        return statusCodeKey;
    }

    /**
     * Sets the value of the statusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public void setStatusCodeKey(ServiceCallStatusCodeKey value) {
        this.statusCodeKey = value;
    }

    /**
     * Gets the value of the technicianKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public ServiceTechnicianKey getTechnicianKey() {
        return technicianKey;
    }

    /**
     * Sets the value of the technicianKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public void setTechnicianKey(ServiceTechnicianKey value) {
        this.technicianKey = value;
    }

    /**
     * Gets the value of the timeZoneKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTimeZoneKey }
     *     
     */
    public ServiceTimeZoneKey getTimeZoneKey() {
        return timeZoneKey;
    }

    /**
     * Sets the value of the timeZoneKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTimeZoneKey }
     *     
     */
    public void setTimeZoneKey(ServiceTimeZoneKey value) {
        this.timeZoneKey = value;
    }

    /**
     * Gets the value of the documentTotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDocumentTotal() {
        return documentTotal;
    }

    /**
     * Sets the value of the documentTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDocumentTotal(MoneyAmount value) {
        this.documentTotal = value;
    }

    /**
     * Gets the value of the estimatedTimeToRepair property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getEstimatedTimeToRepair() {
        return estimatedTimeToRepair;
    }

    /**
     * Sets the value of the estimatedTimeToRepair property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setEstimatedTimeToRepair(BigDecimal value) {
        this.estimatedTimeToRepair = value;
    }

    /**
     * Gets the value of the laborCharges property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCharge }
     *     
     */
    public ServiceCharge getLaborCharges() {
        return laborCharges;
    }

    /**
     * Sets the value of the laborCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCharge }
     *     
     */
    public void setLaborCharges(ServiceCharge value) {
        this.laborCharges = value;
    }

    /**
     * Gets the value of the miscellaneousCharges property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCharge }
     *     
     */
    public ServiceCharge getMiscellaneousCharges() {
        return miscellaneousCharges;
    }

    /**
     * Sets the value of the miscellaneousCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCharge }
     *     
     */
    public void setMiscellaneousCharges(ServiceCharge value) {
        this.miscellaneousCharges = value;
    }

    /**
     * Gets the value of the partsCharges property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCharge }
     *     
     */
    public ServiceCharge getPartsCharges() {
        return partsCharges;
    }

    /**
     * Sets the value of the partsCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCharge }
     *     
     */
    public void setPartsCharges(ServiceCharge value) {
        this.partsCharges = value;
    }

    /**
     * Gets the value of the preTaxDocumentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPreTaxDocumentAmount() {
        return preTaxDocumentAmount;
    }

    /**
     * Sets the value of the preTaxDocumentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPreTaxDocumentAmount(MoneyAmount value) {
        this.preTaxDocumentAmount = value;
    }

    /**
     * Gets the value of the salesPersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalesPersonKey() {
        return salesPersonKey;
    }

    /**
     * Sets the value of the salesPersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalesPersonKey(SalespersonKey value) {
        this.salesPersonKey = value;
    }

    /**
     * Gets the value of the serviceContractLineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getServiceContractLineSequenceNumber() {
        return serviceContractLineSequenceNumber;
    }

    /**
     * Sets the value of the serviceContractLineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setServiceContractLineSequenceNumber(BigDecimal value) {
        this.serviceContractLineSequenceNumber = value;
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
     * Gets the value of the taxExemptNumber1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExemptNumber1() {
        return taxExemptNumber1;
    }

    /**
     * Sets the value of the taxExemptNumber1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExemptNumber1(String value) {
        this.taxExemptNumber1 = value;
    }

    /**
     * Gets the value of the taxExemptNumber2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExemptNumber2() {
        return taxExemptNumber2;
    }

    /**
     * Sets the value of the taxExemptNumber2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExemptNumber2(String value) {
        this.taxExemptNumber2 = value;
    }

    /**
     * Gets the value of the taxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getTaxScheduleKey() {
        return taxScheduleKey;
    }

    /**
     * Sets the value of the taxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setTaxScheduleKey(TaxScheduleKey value) {
        this.taxScheduleKey = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceDistribution }
     *     
     */
    public ArrayOfServiceDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceDistribution }
     *     
     */
    public void setDistributions(ArrayOfServiceDistribution value) {
        this.distributions = value;
    }

    /**
     * Gets the value of the audits property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceCallDocumentAudit }
     *     
     */
    public ArrayOfServiceCallDocumentAudit getAudits() {
        return audits;
    }

    /**
     * Sets the value of the audits property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceCallDocumentAudit }
     *     
     */
    public void setAudits(ArrayOfServiceCallDocumentAudit value) {
        this.audits = value;
    }

}
