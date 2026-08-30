
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectContract complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectContract"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectContractId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectClassKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectType"/&gt;
 *         &lt;element name="AccountingMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAccountingMethod"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CloseToProjectCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectCloseToProjectCosts"/&gt;
 *         &lt;element name="CloseToBillings" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectCloseToBillings"/&gt;
 *         &lt;element name="ContractManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="BusinessManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="CommissionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CommissionBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionBasedOn"/&gt;
 *         &lt;element name="DefaultBillingFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ContactPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BillToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DoesCombineForRevenueRecognition" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RestrictToCustomerList" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DiscountPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="WriteUpDownAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CostCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="ServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetainerAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCommittedCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCommittedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="TransactionalCurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Forecast" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractForecast" minOccurs="0"/&gt;
 *         &lt;element name="Baseline" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractBaseline" minOccurs="0"/&gt;
 *         &lt;element name="Billed" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractBilled" minOccurs="0"/&gt;
 *         &lt;element name="Unposted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractUnposted" minOccurs="0"/&gt;
 *         &lt;element name="Actual" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractActual" minOccurs="0"/&gt;
 *         &lt;element name="BillingCycles" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectContractBillingCycle" minOccurs="0"/&gt;
 *         &lt;element name="Accounts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectContractAccountType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectContract", propOrder = {
    "key",
    "customerKey",
    "projectContractId",
    "name",
    "classKey",
    "projectType",
    "accountingMethod",
    "status",
    "currencyKey",
    "customerPONumber",
    "closeToProjectCosts",
    "closeToBillings",
    "contractManagerKey",
    "businessManagerKey",
    "salespersonKey",
    "salesTerritoryKey",
    "commissionPercent",
    "commissionBasedOn",
    "defaultBillingFormat",
    "addressKey",
    "contactPerson",
    "billToAddressKey",
    "userDefined1",
    "userDefined2",
    "doesCombineForRevenueRecognition",
    "restrictToCustomerList",
    "discountPercent",
    "projectAmount",
    "writeUpDownAmount",
    "costCompletedPercent",
    "quantityCompletedPercent",
    "serviceFeeAmount",
    "retainerAmount",
    "projectFeeAmount",
    "retentionFeeAmount",
    "poCommittedCosts",
    "poCommittedQuantity",
    "transactionalCurrencyKey",
    "forecast",
    "baseline",
    "billed",
    "unposted",
    "actual",
    "billingCycles",
    "accounts"
})
public class ProjectContract
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectContractKey key;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ProjectContractId")
    protected String projectContractId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "ClassKey")
    protected ProjectClassKey classKey;
    @XmlElement(name = "ProjectType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectType projectType;
    @XmlElement(name = "AccountingMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectAccountingMethod accountingMethod;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectStatus status;
    @XmlElement(name = "CurrencyKey")
    protected CurrencyKey currencyKey;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "CloseToProjectCosts", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectCloseToProjectCosts closeToProjectCosts;
    @XmlElement(name = "CloseToBillings", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectCloseToBillings closeToBillings;
    @XmlElement(name = "ContractManagerKey")
    protected EmployeeKey contractManagerKey;
    @XmlElement(name = "BusinessManagerKey")
    protected EmployeeKey businessManagerKey;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "CommissionPercent")
    protected Percent commissionPercent;
    @XmlElement(name = "CommissionBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CommissionBasedOn commissionBasedOn;
    @XmlElement(name = "DefaultBillingFormat")
    protected String defaultBillingFormat;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;
    @XmlElement(name = "ContactPerson")
    protected String contactPerson;
    @XmlElement(name = "BillToAddressKey")
    protected AddressKey billToAddressKey;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "DoesCombineForRevenueRecognition", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesCombineForRevenueRecognition;
    @XmlElement(name = "RestrictToCustomerList", required = true, type = Boolean.class, nillable = true)
    protected Boolean restrictToCustomerList;
    @XmlElement(name = "DiscountPercent")
    protected Percent discountPercent;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "WriteUpDownAmount")
    protected MoneyAmount writeUpDownAmount;
    @XmlElement(name = "CostCompletedPercent")
    protected Percent costCompletedPercent;
    @XmlElement(name = "QuantityCompletedPercent")
    protected Percent quantityCompletedPercent;
    @XmlElement(name = "ServiceFeeAmount")
    protected MoneyAmount serviceFeeAmount;
    @XmlElement(name = "RetainerAmount")
    protected MoneyAmount retainerAmount;
    @XmlElement(name = "ProjectFeeAmount")
    protected MoneyAmount projectFeeAmount;
    @XmlElement(name = "RetentionFeeAmount")
    protected MoneyAmount retentionFeeAmount;
    @XmlElement(name = "POCommittedCosts")
    protected MoneyAmount poCommittedCosts;
    @XmlElement(name = "POCommittedQuantity")
    protected Quantity poCommittedQuantity;
    @XmlElement(name = "TransactionalCurrencyKey")
    protected CurrencyKey transactionalCurrencyKey;
    @XmlElement(name = "Forecast")
    protected ProjectContractForecast forecast;
    @XmlElement(name = "Baseline")
    protected ProjectContractBaseline baseline;
    @XmlElement(name = "Billed")
    protected ProjectContractBilled billed;
    @XmlElement(name = "Unposted")
    protected ProjectContractUnposted unposted;
    @XmlElement(name = "Actual")
    protected ProjectContractActual actual;
    @XmlElement(name = "BillingCycles")
    protected ArrayOfProjectContractBillingCycle billingCycles;
    @XmlElement(name = "Accounts")
    protected ArrayOfProjectContractAccountType accounts;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractKey }
     *     
     */
    public ProjectContractKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractKey }
     *     
     */
    public void setKey(ProjectContractKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
    }

    /**
     * Gets the value of the projectContractId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectContractId() {
        return projectContractId;
    }

    /**
     * Sets the value of the projectContractId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectContractId(String value) {
        this.projectContractId = value;
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
     * Gets the value of the classKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectClassKey }
     *     
     */
    public ProjectClassKey getClassKey() {
        return classKey;
    }

    /**
     * Sets the value of the classKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectClassKey }
     *     
     */
    public void setClassKey(ProjectClassKey value) {
        this.classKey = value;
    }

    /**
     * Gets the value of the projectType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectType }
     *     
     */
    public ProjectType getProjectType() {
        return projectType;
    }

    /**
     * Sets the value of the projectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectType }
     *     
     */
    public void setProjectType(ProjectType value) {
        this.projectType = value;
    }

    /**
     * Gets the value of the accountingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectAccountingMethod }
     *     
     */
    public ProjectAccountingMethod getAccountingMethod() {
        return accountingMethod;
    }

    /**
     * Sets the value of the accountingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectAccountingMethod }
     *     
     */
    public void setAccountingMethod(ProjectAccountingMethod value) {
        this.accountingMethod = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectStatus }
     *     
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectStatus }
     *     
     */
    public void setStatus(ProjectStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the currencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getCurrencyKey() {
        return currencyKey;
    }

    /**
     * Sets the value of the currencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setCurrencyKey(CurrencyKey value) {
        this.currencyKey = value;
    }

    /**
     * Gets the value of the customerPONumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPONumber() {
        return customerPONumber;
    }

    /**
     * Sets the value of the customerPONumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPONumber(String value) {
        this.customerPONumber = value;
    }

    /**
     * Gets the value of the closeToProjectCosts property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectCloseToProjectCosts }
     *     
     */
    public ProjectCloseToProjectCosts getCloseToProjectCosts() {
        return closeToProjectCosts;
    }

    /**
     * Sets the value of the closeToProjectCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectCloseToProjectCosts }
     *     
     */
    public void setCloseToProjectCosts(ProjectCloseToProjectCosts value) {
        this.closeToProjectCosts = value;
    }

    /**
     * Gets the value of the closeToBillings property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectCloseToBillings }
     *     
     */
    public ProjectCloseToBillings getCloseToBillings() {
        return closeToBillings;
    }

    /**
     * Sets the value of the closeToBillings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectCloseToBillings }
     *     
     */
    public void setCloseToBillings(ProjectCloseToBillings value) {
        this.closeToBillings = value;
    }

    /**
     * Gets the value of the contractManagerKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getContractManagerKey() {
        return contractManagerKey;
    }

    /**
     * Sets the value of the contractManagerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setContractManagerKey(EmployeeKey value) {
        this.contractManagerKey = value;
    }

    /**
     * Gets the value of the businessManagerKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getBusinessManagerKey() {
        return businessManagerKey;
    }

    /**
     * Sets the value of the businessManagerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setBusinessManagerKey(EmployeeKey value) {
        this.businessManagerKey = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
    }

    /**
     * Gets the value of the salesTerritoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getSalesTerritoryKey() {
        return salesTerritoryKey;
    }

    /**
     * Sets the value of the salesTerritoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setSalesTerritoryKey(SalesTerritoryKey value) {
        this.salesTerritoryKey = value;
    }

    /**
     * Gets the value of the commissionPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCommissionPercent() {
        return commissionPercent;
    }

    /**
     * Sets the value of the commissionPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCommissionPercent(Percent value) {
        this.commissionPercent = value;
    }

    /**
     * Gets the value of the commissionBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionBasedOn }
     *     
     */
    public CommissionBasedOn getCommissionBasedOn() {
        return commissionBasedOn;
    }

    /**
     * Sets the value of the commissionBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionBasedOn }
     *     
     */
    public void setCommissionBasedOn(CommissionBasedOn value) {
        this.commissionBasedOn = value;
    }

    /**
     * Gets the value of the defaultBillingFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultBillingFormat() {
        return defaultBillingFormat;
    }

    /**
     * Sets the value of the defaultBillingFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultBillingFormat(String value) {
        this.defaultBillingFormat = value;
    }

    /**
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setAddressKey(AddressKey value) {
        this.addressKey = value;
    }

    /**
     * Gets the value of the contactPerson property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the value of the contactPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPerson(String value) {
        this.contactPerson = value;
    }

    /**
     * Gets the value of the billToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getBillToAddressKey() {
        return billToAddressKey;
    }

    /**
     * Sets the value of the billToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setBillToAddressKey(AddressKey value) {
        this.billToAddressKey = value;
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
     * Gets the value of the doesCombineForRevenueRecognition property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesCombineForRevenueRecognition() {
        return doesCombineForRevenueRecognition;
    }

    /**
     * Sets the value of the doesCombineForRevenueRecognition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesCombineForRevenueRecognition(Boolean value) {
        this.doesCombineForRevenueRecognition = value;
    }

    /**
     * Gets the value of the restrictToCustomerList property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRestrictToCustomerList() {
        return restrictToCustomerList;
    }

    /**
     * Sets the value of the restrictToCustomerList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictToCustomerList(Boolean value) {
        this.restrictToCustomerList = value;
    }

    /**
     * Gets the value of the discountPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Sets the value of the discountPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setDiscountPercent(Percent value) {
        this.discountPercent = value;
    }

    /**
     * Gets the value of the projectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectAmount() {
        return projectAmount;
    }

    /**
     * Sets the value of the projectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectAmount(MoneyAmount value) {
        this.projectAmount = value;
    }

    /**
     * Gets the value of the writeUpDownAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getWriteUpDownAmount() {
        return writeUpDownAmount;
    }

    /**
     * Sets the value of the writeUpDownAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setWriteUpDownAmount(MoneyAmount value) {
        this.writeUpDownAmount = value;
    }

    /**
     * Gets the value of the costCompletedPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCostCompletedPercent() {
        return costCompletedPercent;
    }

    /**
     * Sets the value of the costCompletedPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCostCompletedPercent(Percent value) {
        this.costCompletedPercent = value;
    }

    /**
     * Gets the value of the quantityCompletedPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getQuantityCompletedPercent() {
        return quantityCompletedPercent;
    }

    /**
     * Sets the value of the quantityCompletedPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setQuantityCompletedPercent(Percent value) {
        this.quantityCompletedPercent = value;
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
     * Gets the value of the retainerAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRetainerAmount() {
        return retainerAmount;
    }

    /**
     * Sets the value of the retainerAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRetainerAmount(MoneyAmount value) {
        this.retainerAmount = value;
    }

    /**
     * Gets the value of the projectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectFeeAmount() {
        return projectFeeAmount;
    }

    /**
     * Sets the value of the projectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectFeeAmount(MoneyAmount value) {
        this.projectFeeAmount = value;
    }

    /**
     * Gets the value of the retentionFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRetentionFeeAmount() {
        return retentionFeeAmount;
    }

    /**
     * Sets the value of the retentionFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRetentionFeeAmount(MoneyAmount value) {
        this.retentionFeeAmount = value;
    }

    /**
     * Gets the value of the poCommittedCosts property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPOCommittedCosts() {
        return poCommittedCosts;
    }

    /**
     * Sets the value of the poCommittedCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPOCommittedCosts(MoneyAmount value) {
        this.poCommittedCosts = value;
    }

    /**
     * Gets the value of the poCommittedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPOCommittedQuantity() {
        return poCommittedQuantity;
    }

    /**
     * Sets the value of the poCommittedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPOCommittedQuantity(Quantity value) {
        this.poCommittedQuantity = value;
    }

    /**
     * Gets the value of the transactionalCurrencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getTransactionalCurrencyKey() {
        return transactionalCurrencyKey;
    }

    /**
     * Sets the value of the transactionalCurrencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setTransactionalCurrencyKey(CurrencyKey value) {
        this.transactionalCurrencyKey = value;
    }

    /**
     * Gets the value of the forecast property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractForecast }
     *     
     */
    public ProjectContractForecast getForecast() {
        return forecast;
    }

    /**
     * Sets the value of the forecast property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractForecast }
     *     
     */
    public void setForecast(ProjectContractForecast value) {
        this.forecast = value;
    }

    /**
     * Gets the value of the baseline property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractBaseline }
     *     
     */
    public ProjectContractBaseline getBaseline() {
        return baseline;
    }

    /**
     * Sets the value of the baseline property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractBaseline }
     *     
     */
    public void setBaseline(ProjectContractBaseline value) {
        this.baseline = value;
    }

    /**
     * Gets the value of the billed property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractBilled }
     *     
     */
    public ProjectContractBilled getBilled() {
        return billed;
    }

    /**
     * Sets the value of the billed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractBilled }
     *     
     */
    public void setBilled(ProjectContractBilled value) {
        this.billed = value;
    }

    /**
     * Gets the value of the unposted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractUnposted }
     *     
     */
    public ProjectContractUnposted getUnposted() {
        return unposted;
    }

    /**
     * Sets the value of the unposted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractUnposted }
     *     
     */
    public void setUnposted(ProjectContractUnposted value) {
        this.unposted = value;
    }

    /**
     * Gets the value of the actual property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractActual }
     *     
     */
    public ProjectContractActual getActual() {
        return actual;
    }

    /**
     * Sets the value of the actual property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractActual }
     *     
     */
    public void setActual(ProjectContractActual value) {
        this.actual = value;
    }

    /**
     * Gets the value of the billingCycles property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectContractBillingCycle }
     *     
     */
    public ArrayOfProjectContractBillingCycle getBillingCycles() {
        return billingCycles;
    }

    /**
     * Sets the value of the billingCycles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectContractBillingCycle }
     *     
     */
    public void setBillingCycles(ArrayOfProjectContractBillingCycle value) {
        this.billingCycles = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectContractAccountType }
     *     
     */
    public ArrayOfProjectContractAccountType getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectContractAccountType }
     *     
     */
    public void setAccounts(ArrayOfProjectContractAccountType value) {
        this.accounts = value;
    }

}
