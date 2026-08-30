
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectContractSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectContractSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectClassKey" minOccurs="0"/&gt;
 *         &lt;element name="BusinessManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="ForecastProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualCostOfEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ActualTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectContractSummary", propOrder = {
    "key",
    "status",
    "customerKey",
    "projectClassKey",
    "businessManagerKey",
    "forecastProfitAmount",
    "actualProfitAmount",
    "actualEarningsAmount",
    "actualCostOfEarningsAmount",
    "actualTotalCost"
})
public class ProjectContractSummary {

    @XmlElement(name = "Key")
    protected ProjectContractKey key;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectStatus status;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "ProjectClassKey")
    protected ProjectClassKey projectClassKey;
    @XmlElement(name = "BusinessManagerKey")
    protected EmployeeKey businessManagerKey;
    @XmlElement(name = "ForecastProfitAmount")
    protected MoneyAmount forecastProfitAmount;
    @XmlElement(name = "ActualProfitAmount")
    protected MoneyAmount actualProfitAmount;
    @XmlElement(name = "ActualEarningsAmount")
    protected MoneyAmount actualEarningsAmount;
    @XmlElement(name = "ActualCostOfEarningsAmount")
    protected MoneyAmount actualCostOfEarningsAmount;
    @XmlElement(name = "ActualTotalCost")
    protected MoneyAmount actualTotalCost;

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
     * Gets the value of the actualProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualProfitAmount() {
        return actualProfitAmount;
    }

    /**
     * Sets the value of the actualProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualProfitAmount(MoneyAmount value) {
        this.actualProfitAmount = value;
    }

    /**
     * Gets the value of the actualEarningsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualEarningsAmount() {
        return actualEarningsAmount;
    }

    /**
     * Sets the value of the actualEarningsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualEarningsAmount(MoneyAmount value) {
        this.actualEarningsAmount = value;
    }

    /**
     * Gets the value of the actualCostOfEarningsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualCostOfEarningsAmount() {
        return actualCostOfEarningsAmount;
    }

    /**
     * Sets the value of the actualCostOfEarningsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualCostOfEarningsAmount(MoneyAmount value) {
        this.actualCostOfEarningsAmount = value;
    }

    /**
     * Gets the value of the actualTotalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getActualTotalCost() {
        return actualTotalCost;
    }

    /**
     * Sets the value of the actualTotalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setActualTotalCost(MoneyAmount value) {
        this.actualTotalCost = value;
    }

}
