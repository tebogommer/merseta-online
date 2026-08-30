
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Project complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Project"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectContractKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectContractId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProjectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectClassKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectType"/&gt;
 *         &lt;element name="AccountingMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAccountingMethod"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="CurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CloseToProjectCosts" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CloseToBillings" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectDepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="EstimatorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="BusinessManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="CommissionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CommissionBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionBasedOn"/&gt;
 *         &lt;element name="ContactPerson" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BillToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="LaborRateTableKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLaborRateTableKey" minOccurs="0"/&gt;
 *         &lt;element name="LaborRateTableType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLaborRateTableType"/&gt;
 *         &lt;element name="DoesAcceptLaborRateTableReplacement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="EquipmentRateTableKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEquipmentRateTableKey" minOccurs="0"/&gt;
 *         &lt;element name="DoesAcceptEquipmentRateTableReplacement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BillingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BillingType"/&gt;
 *         &lt;element name="ServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetainerAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetentionFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="AccountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DefaultBillingFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DiscountPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="RetentionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="UserDefinedText1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefinedText2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DoesCombineForRevenueRecognition" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="WriteUpDownAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CostCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="POCommittedCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCommittedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="WorkersCompensationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkersCompensationKey" minOccurs="0"/&gt;
 *         &lt;element name="SUTAState" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BillingNotReceivable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RestrictToCustomerList" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TransactionalCurrencyCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Forecast" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectForecast" minOccurs="0"/&gt;
 *         &lt;element name="Baseline" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBaseline" minOccurs="0"/&gt;
 *         &lt;element name="Billed" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBilled" minOccurs="0"/&gt;
 *         &lt;element name="Posted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectPosted" minOccurs="0"/&gt;
 *         &lt;element name="Unposted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectUnposted" minOccurs="0"/&gt;
 *         &lt;element name="Actual" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectActual" minOccurs="0"/&gt;
 *         &lt;element name="BillingCycles" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectBillingCycle" minOccurs="0"/&gt;
 *         &lt;element name="Accounts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectAccountType" minOccurs="0"/&gt;
 *         &lt;element name="EquipmentList" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectEquipmentItem" minOccurs="0"/&gt;
 *         &lt;element name="Fees" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectFee" minOccurs="0"/&gt;
 *         &lt;element name="Budgets" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectBudget" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Project", propOrder = {
    "customerKey",
    "projectContractKey",
    "projectContractId",
    "projectId",
    "name",
    "key",
    "projectClassKey",
    "type",
    "accountingMethod",
    "status",
    "currencyKey",
    "customerPONumber",
    "closeToProjectCosts",
    "closeToBillings",
    "departmentKey",
    "estimatorKey",
    "projectManagerKey",
    "businessManagerKey",
    "salespersonKey",
    "salesTerritoryKey",
    "commissionPercent",
    "commissionBasedOn",
    "contactPerson",
    "billToAddressKey",
    "laborRateTableKey",
    "laborRateTableType",
    "doesAcceptLaborRateTableReplacement",
    "equipmentRateTableKey",
    "doesAcceptEquipmentRateTableReplacement",
    "billingType",
    "serviceFeeAmount",
    "projectFeeAmount",
    "retainerAmount",
    "retentionFeeAmount",
    "projectAmount",
    "accountAmount",
    "defaultBillingFormat",
    "discountPercent",
    "retentionPercent",
    "userDefinedText1",
    "userDefinedText2",
    "doesCombineForRevenueRecognition",
    "writeUpDownAmount",
    "costCompletedPercent",
    "quantityCompletedPercent",
    "poCommittedCosts",
    "poCommittedQuantity",
    "workersCompensationKey",
    "sutaState",
    "billingNotReceivable",
    "restrictToCustomerList",
    "transactionalCurrencyCodeKey",
    "forecast",
    "baseline",
    "billed",
    "posted",
    "unposted",
    "actual",
    "billingCycles",
    "accounts",
    "equipmentList",
    "fees",
    "budgets"
})
public class Project
    extends BusinessObject
{

    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ProjectContractKey")
    protected ProjectContractKey projectContractKey;
    @XmlElement(name = "ProjectContractId")
    protected String projectContractId;
    @XmlElement(name = "ProjectId")
    protected String projectId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Key")
    protected ProjectKey key;
    @XmlElement(name = "ProjectClassKey")
    protected ProjectClassKey projectClassKey;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectType type;
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
    @XmlElement(name = "CloseToProjectCosts", required = true, type = Boolean.class, nillable = true)
    protected Boolean closeToProjectCosts;
    @XmlElement(name = "CloseToBillings", required = true, type = Boolean.class, nillable = true)
    protected Boolean closeToBillings;
    @XmlElement(name = "DepartmentKey")
    protected ProjectDepartmentKey departmentKey;
    @XmlElement(name = "EstimatorKey")
    protected EmployeeKey estimatorKey;
    @XmlElement(name = "ProjectManagerKey")
    protected EmployeeKey projectManagerKey;
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
    @XmlElement(name = "ContactPerson")
    protected String contactPerson;
    @XmlElement(name = "BillToAddressKey")
    protected AddressKey billToAddressKey;
    @XmlElement(name = "LaborRateTableKey")
    protected ProjectLaborRateTableKey laborRateTableKey;
    @XmlElement(name = "LaborRateTableType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectLaborRateTableType laborRateTableType;
    @XmlElement(name = "DoesAcceptLaborRateTableReplacement", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAcceptLaborRateTableReplacement;
    @XmlElement(name = "EquipmentRateTableKey")
    protected ProjectEquipmentRateTableKey equipmentRateTableKey;
    @XmlElement(name = "DoesAcceptEquipmentRateTableReplacement", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAcceptEquipmentRateTableReplacement;
    @XmlElement(name = "BillingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BillingType billingType;
    @XmlElement(name = "ServiceFeeAmount")
    protected MoneyAmount serviceFeeAmount;
    @XmlElement(name = "ProjectFeeAmount")
    protected MoneyAmount projectFeeAmount;
    @XmlElement(name = "RetainerAmount")
    protected MoneyAmount retainerAmount;
    @XmlElement(name = "RetentionFeeAmount")
    protected MoneyAmount retentionFeeAmount;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "AccountAmount")
    protected MoneyAmount accountAmount;
    @XmlElement(name = "DefaultBillingFormat")
    protected String defaultBillingFormat;
    @XmlElement(name = "DiscountPercent")
    protected Percent discountPercent;
    @XmlElement(name = "RetentionPercent")
    protected Percent retentionPercent;
    @XmlElement(name = "UserDefinedText1")
    protected String userDefinedText1;
    @XmlElement(name = "UserDefinedText2")
    protected String userDefinedText2;
    @XmlElement(name = "DoesCombineForRevenueRecognition", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesCombineForRevenueRecognition;
    @XmlElement(name = "WriteUpDownAmount")
    protected MoneyAmount writeUpDownAmount;
    @XmlElement(name = "CostCompletedPercent")
    protected Percent costCompletedPercent;
    @XmlElement(name = "QuantityCompletedPercent")
    protected Percent quantityCompletedPercent;
    @XmlElement(name = "POCommittedCosts")
    protected MoneyAmount poCommittedCosts;
    @XmlElement(name = "POCommittedQuantity")
    protected Quantity poCommittedQuantity;
    @XmlElement(name = "WorkersCompensationKey")
    protected WorkersCompensationKey workersCompensationKey;
    @XmlElement(name = "SUTAState")
    protected String sutaState;
    @XmlElement(name = "BillingNotReceivable")
    protected MoneyAmount billingNotReceivable;
    @XmlElement(name = "RestrictToCustomerList", required = true, type = Boolean.class, nillable = true)
    protected Boolean restrictToCustomerList;
    @XmlElement(name = "TransactionalCurrencyCodeKey")
    protected CurrencyKey transactionalCurrencyCodeKey;
    @XmlElement(name = "Forecast")
    protected ProjectForecast forecast;
    @XmlElement(name = "Baseline")
    protected ProjectBaseline baseline;
    @XmlElement(name = "Billed")
    protected ProjectBilled billed;
    @XmlElement(name = "Posted")
    protected ProjectPosted posted;
    @XmlElement(name = "Unposted")
    protected ProjectUnposted unposted;
    @XmlElement(name = "Actual")
    protected ProjectActual actual;
    @XmlElement(name = "BillingCycles")
    protected ArrayOfProjectBillingCycle billingCycles;
    @XmlElement(name = "Accounts")
    protected ArrayOfProjectAccountType accounts;
    @XmlElement(name = "EquipmentList")
    protected ArrayOfProjectEquipmentItem equipmentList;
    @XmlElement(name = "Fees")
    protected ArrayOfProjectFee fees;
    @XmlElement(name = "Budgets")
    protected ArrayOfProjectBudget budgets;

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
     * Gets the value of the projectContractKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractKey }
     *     
     */
    public ProjectContractKey getProjectContractKey() {
        return projectContractKey;
    }

    /**
     * Sets the value of the projectContractKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractKey }
     *     
     */
    public void setProjectContractKey(ProjectContractKey value) {
        this.projectContractKey = value;
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
     * Gets the value of the projectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Sets the value of the projectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectId(String value) {
        this.projectId = value;
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
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setKey(ProjectKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the projectClassKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectClassKey }
     *     
     */
    public ProjectClassKey getProjectClassKey() {
        return projectClassKey;
    }

    /**
     * Sets the value of the projectClassKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectClassKey }
     *     
     */
    public void setProjectClassKey(ProjectClassKey value) {
        this.projectClassKey = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectType }
     *     
     */
    public ProjectType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectType }
     *     
     */
    public void setType(ProjectType value) {
        this.type = value;
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
     *     {@link Boolean }
     *     
     */
    public Boolean isCloseToProjectCosts() {
        return closeToProjectCosts;
    }

    /**
     * Sets the value of the closeToProjectCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCloseToProjectCosts(Boolean value) {
        this.closeToProjectCosts = value;
    }

    /**
     * Gets the value of the closeToBillings property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCloseToBillings() {
        return closeToBillings;
    }

    /**
     * Sets the value of the closeToBillings property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCloseToBillings(Boolean value) {
        this.closeToBillings = value;
    }

    /**
     * Gets the value of the departmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectDepartmentKey }
     *     
     */
    public ProjectDepartmentKey getDepartmentKey() {
        return departmentKey;
    }

    /**
     * Sets the value of the departmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectDepartmentKey }
     *     
     */
    public void setDepartmentKey(ProjectDepartmentKey value) {
        this.departmentKey = value;
    }

    /**
     * Gets the value of the estimatorKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getEstimatorKey() {
        return estimatorKey;
    }

    /**
     * Sets the value of the estimatorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setEstimatorKey(EmployeeKey value) {
        this.estimatorKey = value;
    }

    /**
     * Gets the value of the projectManagerKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getProjectManagerKey() {
        return projectManagerKey;
    }

    /**
     * Sets the value of the projectManagerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setProjectManagerKey(EmployeeKey value) {
        this.projectManagerKey = value;
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
     * Gets the value of the laborRateTableKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectLaborRateTableKey }
     *     
     */
    public ProjectLaborRateTableKey getLaborRateTableKey() {
        return laborRateTableKey;
    }

    /**
     * Sets the value of the laborRateTableKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectLaborRateTableKey }
     *     
     */
    public void setLaborRateTableKey(ProjectLaborRateTableKey value) {
        this.laborRateTableKey = value;
    }

    /**
     * Gets the value of the laborRateTableType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectLaborRateTableType }
     *     
     */
    public ProjectLaborRateTableType getLaborRateTableType() {
        return laborRateTableType;
    }

    /**
     * Sets the value of the laborRateTableType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectLaborRateTableType }
     *     
     */
    public void setLaborRateTableType(ProjectLaborRateTableType value) {
        this.laborRateTableType = value;
    }

    /**
     * Gets the value of the doesAcceptLaborRateTableReplacement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAcceptLaborRateTableReplacement() {
        return doesAcceptLaborRateTableReplacement;
    }

    /**
     * Sets the value of the doesAcceptLaborRateTableReplacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAcceptLaborRateTableReplacement(Boolean value) {
        this.doesAcceptLaborRateTableReplacement = value;
    }

    /**
     * Gets the value of the equipmentRateTableKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEquipmentRateTableKey }
     *     
     */
    public ProjectEquipmentRateTableKey getEquipmentRateTableKey() {
        return equipmentRateTableKey;
    }

    /**
     * Sets the value of the equipmentRateTableKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEquipmentRateTableKey }
     *     
     */
    public void setEquipmentRateTableKey(ProjectEquipmentRateTableKey value) {
        this.equipmentRateTableKey = value;
    }

    /**
     * Gets the value of the doesAcceptEquipmentRateTableReplacement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAcceptEquipmentRateTableReplacement() {
        return doesAcceptEquipmentRateTableReplacement;
    }

    /**
     * Sets the value of the doesAcceptEquipmentRateTableReplacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAcceptEquipmentRateTableReplacement(Boolean value) {
        this.doesAcceptEquipmentRateTableReplacement = value;
    }

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link BillingType }
     *     
     */
    public BillingType getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingType }
     *     
     */
    public void setBillingType(BillingType value) {
        this.billingType = value;
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
     * Gets the value of the accountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAccountAmount() {
        return accountAmount;
    }

    /**
     * Sets the value of the accountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAccountAmount(MoneyAmount value) {
        this.accountAmount = value;
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
     * Gets the value of the userDefinedText1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefinedText1() {
        return userDefinedText1;
    }

    /**
     * Sets the value of the userDefinedText1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefinedText1(String value) {
        this.userDefinedText1 = value;
    }

    /**
     * Gets the value of the userDefinedText2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefinedText2() {
        return userDefinedText2;
    }

    /**
     * Sets the value of the userDefinedText2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefinedText2(String value) {
        this.userDefinedText2 = value;
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
     * Gets the value of the workersCompensationKey property.
     * 
     * @return
     *     possible object is
     *     {@link WorkersCompensationKey }
     *     
     */
    public WorkersCompensationKey getWorkersCompensationKey() {
        return workersCompensationKey;
    }

    /**
     * Sets the value of the workersCompensationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkersCompensationKey }
     *     
     */
    public void setWorkersCompensationKey(WorkersCompensationKey value) {
        this.workersCompensationKey = value;
    }

    /**
     * Gets the value of the sutaState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSUTAState() {
        return sutaState;
    }

    /**
     * Sets the value of the sutaState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSUTAState(String value) {
        this.sutaState = value;
    }

    /**
     * Gets the value of the billingNotReceivable property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBillingNotReceivable() {
        return billingNotReceivable;
    }

    /**
     * Sets the value of the billingNotReceivable property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBillingNotReceivable(MoneyAmount value) {
        this.billingNotReceivable = value;
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
     * Gets the value of the transactionalCurrencyCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getTransactionalCurrencyCodeKey() {
        return transactionalCurrencyCodeKey;
    }

    /**
     * Sets the value of the transactionalCurrencyCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setTransactionalCurrencyCodeKey(CurrencyKey value) {
        this.transactionalCurrencyCodeKey = value;
    }

    /**
     * Gets the value of the forecast property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectForecast }
     *     
     */
    public ProjectForecast getForecast() {
        return forecast;
    }

    /**
     * Sets the value of the forecast property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectForecast }
     *     
     */
    public void setForecast(ProjectForecast value) {
        this.forecast = value;
    }

    /**
     * Gets the value of the baseline property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBaseline }
     *     
     */
    public ProjectBaseline getBaseline() {
        return baseline;
    }

    /**
     * Sets the value of the baseline property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBaseline }
     *     
     */
    public void setBaseline(ProjectBaseline value) {
        this.baseline = value;
    }

    /**
     * Gets the value of the billed property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBilled }
     *     
     */
    public ProjectBilled getBilled() {
        return billed;
    }

    /**
     * Sets the value of the billed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBilled }
     *     
     */
    public void setBilled(ProjectBilled value) {
        this.billed = value;
    }

    /**
     * Gets the value of the posted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectPosted }
     *     
     */
    public ProjectPosted getPosted() {
        return posted;
    }

    /**
     * Sets the value of the posted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectPosted }
     *     
     */
    public void setPosted(ProjectPosted value) {
        this.posted = value;
    }

    /**
     * Gets the value of the unposted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectUnposted }
     *     
     */
    public ProjectUnposted getUnposted() {
        return unposted;
    }

    /**
     * Sets the value of the unposted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectUnposted }
     *     
     */
    public void setUnposted(ProjectUnposted value) {
        this.unposted = value;
    }

    /**
     * Gets the value of the actual property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectActual }
     *     
     */
    public ProjectActual getActual() {
        return actual;
    }

    /**
     * Sets the value of the actual property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectActual }
     *     
     */
    public void setActual(ProjectActual value) {
        this.actual = value;
    }

    /**
     * Gets the value of the billingCycles property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectBillingCycle }
     *     
     */
    public ArrayOfProjectBillingCycle getBillingCycles() {
        return billingCycles;
    }

    /**
     * Sets the value of the billingCycles property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectBillingCycle }
     *     
     */
    public void setBillingCycles(ArrayOfProjectBillingCycle value) {
        this.billingCycles = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectAccountType }
     *     
     */
    public ArrayOfProjectAccountType getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectAccountType }
     *     
     */
    public void setAccounts(ArrayOfProjectAccountType value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the equipmentList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectEquipmentItem }
     *     
     */
    public ArrayOfProjectEquipmentItem getEquipmentList() {
        return equipmentList;
    }

    /**
     * Sets the value of the equipmentList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectEquipmentItem }
     *     
     */
    public void setEquipmentList(ArrayOfProjectEquipmentItem value) {
        this.equipmentList = value;
    }

    /**
     * Gets the value of the fees property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectFee }
     *     
     */
    public ArrayOfProjectFee getFees() {
        return fees;
    }

    /**
     * Sets the value of the fees property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectFee }
     *     
     */
    public void setFees(ArrayOfProjectFee value) {
        this.fees = value;
    }

    /**
     * Gets the value of the budgets property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectBudget }
     *     
     */
    public ArrayOfProjectBudget getBudgets() {
        return budgets;
    }

    /**
     * Sets the value of the budgets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectBudget }
     *     
     */
    public void setBudgets(ArrayOfProjectBudget value) {
        this.budgets = value;
    }

}
