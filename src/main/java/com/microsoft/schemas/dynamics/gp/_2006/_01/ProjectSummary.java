
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProjectContractKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectClassKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectType"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="AccountingMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAccountingMethod"/&gt;
 *         &lt;element name="ForecastTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ForecastProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectSummary", propOrder = {
    "key",
    "projectId",
    "name",
    "projectContractKey",
    "customerKey",
    "projectClassKey",
    "projectManagerKey",
    "type",
    "status",
    "accountingMethod",
    "forecastTotalCost",
    "forecastProfitAmount",
    "postedTotalCost",
    "postedProfitAmount"
})
public class ProjectSummary {

    @XmlElement(name = "Key")
    protected ProjectKey key;
    @XmlElement(name = "ProjectId")
    protected String projectId;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "ProjectContractKey")
    protected ProjectContractKey projectContractKey;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ProjectClassKey")
    protected ProjectClassKey projectClassKey;
    @XmlElement(name = "ProjectManagerKey")
    protected EmployeeKey projectManagerKey;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectType type;
    @XmlElement(name = "Status", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectStatus status;
    @XmlElement(name = "AccountingMethod", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectAccountingMethod accountingMethod;
    @XmlElement(name = "ForecastTotalCost")
    protected MoneyAmount forecastTotalCost;
    @XmlElement(name = "ForecastProfitAmount")
    protected MoneyAmount forecastProfitAmount;
    @XmlElement(name = "PostedTotalCost")
    protected MoneyAmount postedTotalCost;
    @XmlElement(name = "PostedProfitAmount")
    protected MoneyAmount postedProfitAmount;

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
     * Gets the value of the forecastTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getForecastTotalCost() {
        return forecastTotalCost;
    }

    /**
     * Sets the value of the forecastTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setForecastTotalCost(MoneyAmount value) {
        this.forecastTotalCost = value;
    }

    /**
     * Gets the value of the forecastProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getForecastProfitAmount() {
        return forecastProfitAmount;
    }

    /**
     * Sets the value of the forecastProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setForecastProfitAmount(MoneyAmount value) {
        this.forecastProfitAmount = value;
    }

    /**
     * Gets the value of the postedTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedTotalCost() {
        return postedTotalCost;
    }

    /**
     * Sets the value of the postedTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedTotalCost(MoneyAmount value) {
        this.postedTotalCost = value;
    }

    /**
     * Gets the value of the postedProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedProfitAmount() {
        return postedProfitAmount;
    }

    /**
     * Sets the value of the postedProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedProfitAmount(MoneyAmount value) {
        this.postedProfitAmount = value;
    }

}
