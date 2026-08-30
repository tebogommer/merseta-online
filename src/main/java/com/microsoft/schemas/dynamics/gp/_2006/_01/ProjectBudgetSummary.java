
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBudgetSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBudgetSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="ProfitType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitType"/&gt;
 *         &lt;element name="PostedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="PostedTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PostedBillableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ForecastQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ForecastTotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ForecastBillableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBudgetSummary", propOrder = {
    "key",
    "projectAmount",
    "status",
    "profitType",
    "postedQuantity",
    "postedTotalCost",
    "postedBillableAmount",
    "forecastQuantity",
    "forecastTotalCost",
    "forecastBillableAmount"
})
public class ProjectBudgetSummary {

    @XmlElement(name = "Key")
    protected ProjectBudgetKey key;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectStatus status;
    @XmlElement(name = "ProfitType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProfitType profitType;
    @XmlElement(name = "PostedQuantity")
    protected Quantity postedQuantity;
    @XmlElement(name = "PostedTotalCost")
    protected MoneyAmount postedTotalCost;
    @XmlElement(name = "PostedBillableAmount")
    protected MoneyAmount postedBillableAmount;
    @XmlElement(name = "ForecastQuantity")
    protected Quantity forecastQuantity;
    @XmlElement(name = "ForecastTotalCost")
    protected MoneyAmount forecastTotalCost;
    @XmlElement(name = "ForecastBillableAmount")
    protected MoneyAmount forecastBillableAmount;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetKey }
     *     
     */
    public ProjectBudgetKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetKey }
     *     
     */
    public void setKey(ProjectBudgetKey value) {
        this.key = value;
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
     * Gets the value of the profitType property.
     * 
     * @return
     *     possible object is
     *     {@link ProfitType }
     *     
     */
    public ProfitType getProfitType() {
        return profitType;
    }

    /**
     * Sets the value of the profitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfitType }
     *     
     */
    public void setProfitType(ProfitType value) {
        this.profitType = value;
    }

    /**
     * Gets the value of the postedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPostedQuantity() {
        return postedQuantity;
    }

    /**
     * Sets the value of the postedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPostedQuantity(Quantity value) {
        this.postedQuantity = value;
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
     * Gets the value of the postedBillableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPostedBillableAmount() {
        return postedBillableAmount;
    }

    /**
     * Sets the value of the postedBillableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPostedBillableAmount(MoneyAmount value) {
        this.postedBillableAmount = value;
    }

    /**
     * Gets the value of the forecastQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getForecastQuantity() {
        return forecastQuantity;
    }

    /**
     * Sets the value of the forecastQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setForecastQuantity(Quantity value) {
        this.forecastQuantity = value;
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
     * Gets the value of the forecastBillableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getForecastBillableAmount() {
        return forecastBillableAmount;
    }

    /**
     * Sets the value of the forecastBillableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setForecastBillableAmount(MoneyAmount value) {
        this.forecastBillableAmount = value;
    }

}
