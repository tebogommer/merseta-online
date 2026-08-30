
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for Company complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Company"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DefaultAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompanyAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ExtendedBusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxExempt1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxExempt2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxRegistration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="WithholdingVendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="WitholdingFileOrReconciliationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WitholdingTaxRate" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ReceivablesProcessesTaxDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="PayablesProcessesTaxDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="BusinessType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SICNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DUNSNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Vets100Number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AccountSegmentSeparator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsValueAddedTaxReturnEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Options" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompanyOptions"/&gt;
 *         &lt;element name="IsWorkflowEnabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Company", propOrder = {
    "key",
    "name",
    "defaultAddressKey",
    "defaultAddress",
    "userDefined1",
    "userDefined2",
    "taxExempt1",
    "taxExempt2",
    "taxRegistration",
    "modifiedDate",
    "modifiedBy",
    "createdDate",
    "salesTaxScheduleKey",
    "purchasesTaxScheduleKey",
    "withholdingVendorKey",
    "witholdingFileOrReconciliationNumber",
    "witholdingTaxRate",
    "receivablesProcessesTaxDetailKey",
    "payablesProcessesTaxDetailKey",
    "businessType",
    "sicNumber",
    "dunsNumber",
    "vets100Number",
    "accountSegmentSeparator",
    "isValueAddedTaxReturnEnabled",
    "options",
    "isWorkflowEnabled"
})
public class Company
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CompanyKey key;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "DefaultAddressKey")
    protected CompanyAddressKey defaultAddressKey;
    @XmlElement(name = "DefaultAddress")
    protected ExtendedBusinessAddress defaultAddress;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "TaxExempt1")
    protected String taxExempt1;
    @XmlElement(name = "TaxExempt2")
    protected String taxExempt2;
    @XmlElement(name = "TaxRegistration")
    protected String taxRegistration;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "PurchasesTaxScheduleKey")
    protected TaxScheduleKey purchasesTaxScheduleKey;
    @XmlElement(name = "WithholdingVendorKey")
    protected VendorKey withholdingVendorKey;
    @XmlElement(name = "WitholdingFileOrReconciliationNumber")
    protected String witholdingFileOrReconciliationNumber;
    @XmlElement(name = "WitholdingTaxRate", required = true, type = Integer.class, nillable = true)
    protected Integer witholdingTaxRate;
    @XmlElement(name = "ReceivablesProcessesTaxDetailKey")
    protected TaxDetailKey receivablesProcessesTaxDetailKey;
    @XmlElement(name = "PayablesProcessesTaxDetailKey")
    protected TaxDetailKey payablesProcessesTaxDetailKey;
    @XmlElement(name = "BusinessType")
    protected String businessType;
    @XmlElement(name = "SICNumber")
    protected String sicNumber;
    @XmlElement(name = "DUNSNumber")
    protected String dunsNumber;
    @XmlElement(name = "Vets100Number")
    protected String vets100Number;
    @XmlElement(name = "AccountSegmentSeparator")
    protected String accountSegmentSeparator;
    @XmlElement(name = "IsValueAddedTaxReturnEnabled", required = true, type = Boolean.class, nillable = true)
    protected Boolean isValueAddedTaxReturnEnabled;
    @XmlList
    @XmlElement(name = "Options", required = true, nillable = true)
    protected List<String> options;
    @XmlElement(name = "IsWorkflowEnabled", required = true, type = Boolean.class, nillable = true)
    protected Boolean isWorkflowEnabled;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setKey(CompanyKey value) {
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
     * Gets the value of the defaultAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyAddressKey }
     *     
     */
    public CompanyAddressKey getDefaultAddressKey() {
        return defaultAddressKey;
    }

    /**
     * Sets the value of the defaultAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyAddressKey }
     *     
     */
    public void setDefaultAddressKey(CompanyAddressKey value) {
        this.defaultAddressKey = value;
    }

    /**
     * Gets the value of the defaultAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedBusinessAddress }
     *     
     */
    public ExtendedBusinessAddress getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * Sets the value of the defaultAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedBusinessAddress }
     *     
     */
    public void setDefaultAddress(ExtendedBusinessAddress value) {
        this.defaultAddress = value;
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
     * Gets the value of the taxExempt1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExempt1() {
        return taxExempt1;
    }

    /**
     * Sets the value of the taxExempt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExempt1(String value) {
        this.taxExempt1 = value;
    }

    /**
     * Gets the value of the taxExempt2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExempt2() {
        return taxExempt2;
    }

    /**
     * Sets the value of the taxExempt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExempt2(String value) {
        this.taxExempt2 = value;
    }

    /**
     * Gets the value of the taxRegistration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxRegistration() {
        return taxRegistration;
    }

    /**
     * Sets the value of the taxRegistration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxRegistration(String value) {
        this.taxRegistration = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
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
     * Gets the value of the purchasesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPurchasesTaxScheduleKey() {
        return purchasesTaxScheduleKey;
    }

    /**
     * Sets the value of the purchasesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPurchasesTaxScheduleKey(TaxScheduleKey value) {
        this.purchasesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the withholdingVendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getWithholdingVendorKey() {
        return withholdingVendorKey;
    }

    /**
     * Sets the value of the withholdingVendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setWithholdingVendorKey(VendorKey value) {
        this.withholdingVendorKey = value;
    }

    /**
     * Gets the value of the witholdingFileOrReconciliationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWitholdingFileOrReconciliationNumber() {
        return witholdingFileOrReconciliationNumber;
    }

    /**
     * Sets the value of the witholdingFileOrReconciliationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWitholdingFileOrReconciliationNumber(String value) {
        this.witholdingFileOrReconciliationNumber = value;
    }

    /**
     * Gets the value of the witholdingTaxRate property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWitholdingTaxRate() {
        return witholdingTaxRate;
    }

    /**
     * Sets the value of the witholdingTaxRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWitholdingTaxRate(Integer value) {
        this.witholdingTaxRate = value;
    }

    /**
     * Gets the value of the receivablesProcessesTaxDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getReceivablesProcessesTaxDetailKey() {
        return receivablesProcessesTaxDetailKey;
    }

    /**
     * Sets the value of the receivablesProcessesTaxDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setReceivablesProcessesTaxDetailKey(TaxDetailKey value) {
        this.receivablesProcessesTaxDetailKey = value;
    }

    /**
     * Gets the value of the payablesProcessesTaxDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getPayablesProcessesTaxDetailKey() {
        return payablesProcessesTaxDetailKey;
    }

    /**
     * Sets the value of the payablesProcessesTaxDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setPayablesProcessesTaxDetailKey(TaxDetailKey value) {
        this.payablesProcessesTaxDetailKey = value;
    }

    /**
     * Gets the value of the businessType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * Sets the value of the businessType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessType(String value) {
        this.businessType = value;
    }

    /**
     * Gets the value of the sicNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSICNumber() {
        return sicNumber;
    }

    /**
     * Sets the value of the sicNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSICNumber(String value) {
        this.sicNumber = value;
    }

    /**
     * Gets the value of the dunsNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDUNSNumber() {
        return dunsNumber;
    }

    /**
     * Sets the value of the dunsNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDUNSNumber(String value) {
        this.dunsNumber = value;
    }

    /**
     * Gets the value of the vets100Number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVets100Number() {
        return vets100Number;
    }

    /**
     * Sets the value of the vets100Number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVets100Number(String value) {
        this.vets100Number = value;
    }

    /**
     * Gets the value of the accountSegmentSeparator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountSegmentSeparator() {
        return accountSegmentSeparator;
    }

    /**
     * Sets the value of the accountSegmentSeparator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountSegmentSeparator(String value) {
        this.accountSegmentSeparator = value;
    }

    /**
     * Gets the value of the isValueAddedTaxReturnEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsValueAddedTaxReturnEnabled() {
        return isValueAddedTaxReturnEnabled;
    }

    /**
     * Sets the value of the isValueAddedTaxReturnEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsValueAddedTaxReturnEnabled(Boolean value) {
        this.isValueAddedTaxReturnEnabled = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the options property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOptions() {
        if (options == null) {
            options = new ArrayList<String>();
        }
        return this.options;
    }

    /**
     * Gets the value of the isWorkflowEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWorkflowEnabled() {
        return isWorkflowEnabled;
    }

    /**
     * Sets the value of the isWorkflowEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWorkflowEnabled(Boolean value) {
        this.isWorkflowEnabled = value;
    }

}
